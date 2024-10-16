package de.rwth.swc.piggybank.domain.transfers.valueobject

import java.util.UUID

/**
 * Represents the id of an account watch.
 */
@JvmInline
value class AccountWatchId(val value: UUID) {
    override fun toString(): String {
        return value.toString()
    }
}