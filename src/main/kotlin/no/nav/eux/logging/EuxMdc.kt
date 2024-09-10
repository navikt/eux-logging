package no.nav.eux.logging

import org.slf4j.MDC
import java.util.*

fun <T> T.mdc(
    prosess: String? = null,
    arkivarprosess: String? = null,
    sletteprosess: String? = null,
    rinasakId: Int? = null,
    dokumentInfoId: String? = null,
    sedId: UUID? = null,
    sedVersjon: Int? = null,
    sedType: String? = null,
    journalpostId: String? = null,
    bucType: String? = null,
    erSakseier: Boolean? = null
): T {
    "prosess" leggTil prosess
    "arkivarprosess" leggTil arkivarprosess
    "sletteprosess" leggTil sletteprosess
    "rinasakId" leggTil rinasakId
    "dokumentInfoId" leggTil dokumentInfoId
    "sedId" leggTil sedId
    "sedVersjon" leggTil sedVersjon
    "sedType" leggTil sedType
    "journalpostId" leggTil journalpostId
    "bucType" leggTil bucType
    "erSakseier" leggTil erSakseier
    return this
}

fun clearLocalMdc() {
    MDC.remove("rinasakId")
    MDC.remove("dokumentInfoId")
    MDC.remove("sedId")
    MDC.remove("sedVersjon")
    MDC.remove("sedType")
    MDC.remove("journalpostId")
    MDC.remove("bucType")
    MDC.remove("prosess")
    MDC.remove("arkivarprosess")
    MDC.remove("sletteprosess")
    MDC.remove("erSakseier")
}

fun <T> T.setAndClearLocalMdc(
    prosess: String? = null,
    arkivarprosess: String? = null,
    sletteprosess: String? = null,
    rinasakId: Int? = null,
    dokumentInfoId: String? = null,
    sedId: UUID? = null,
    sedVersjon: Int? = null,
    sedType: String? = null,
    journalpostId: String? = null,
    bucType: String? = null,
    erSakseier: Boolean? = null
) {
    clearLocalMdc()
    mdc(
        prosess = prosess,
        arkivarprosess = arkivarprosess,
        sletteprosess = sletteprosess,
        rinasakId = rinasakId,
        dokumentInfoId = dokumentInfoId,
        sedId = sedId,
        sedVersjon = sedVersjon,
        sedType = sedType,
        journalpostId = journalpostId,
        bucType = bucType,
        erSakseier = erSakseier
    )
}

private infix fun String.leggTil(value: Any?) {
    if (value != null) MDC.put(this, "$value")
}
