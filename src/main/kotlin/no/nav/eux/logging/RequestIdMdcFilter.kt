package no.nav.eux.logging

import jakarta.servlet.Filter
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.MDC
import java.util.UUID.randomUUID

const val REQUEST_ID_MDC_KEY = "x_request_id"

val REQUEST_ID_HEADER_CANDIDATES = listOf(
    "x_request_id",
    "x-request-id",
    "nav-call-id",
    "x-correlation-id",
    "nav-callid",
    "callid",
    "nav-correlation-id"
)

open class RequestIdMdcFilter : Filter {

    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        val requestId = (request as HttpServletRequest).toNavRequestId()
        MDC.put(REQUEST_ID_MDC_KEY, requestId)
        try {
            chain.doFilter(request, response)
        } finally {
            MDC.remove(REQUEST_ID_MDC_KEY)
        }
    }

    private fun HttpServletRequest.toNavRequestId() =
        headerNames
            .toList()
            .map { it.lowercase() to it }
            .firstOrNull { it.first.keyExists() }
            ?.let { getHeader(it.second) }
            ?: randomUUID().toString()

    private fun String.keyExists() =
        REQUEST_ID_HEADER_CANDIDATES.any { it == this }
}