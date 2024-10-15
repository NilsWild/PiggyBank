package de.rwth.swc.piggybank.domain.transferclassification.api.dto

import java.time.LocalDate

/**
 * Inline value class for the value date.
 *
 * @property value The date of the transfer.
 */
@JvmInline
value class ValueDate(val value: LocalDate)