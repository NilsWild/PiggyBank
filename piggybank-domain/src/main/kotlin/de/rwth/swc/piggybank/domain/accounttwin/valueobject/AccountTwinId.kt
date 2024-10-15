package de.rwth.swc.piggybank.domain.accounttwin.valueobject

import java.util.UUID


/**
 * Represents the id of an account twin.
 */
@JvmInline
value class AccountTwinId(val value: UUID) {
    override fun toString(): String {
        return value.toString()
    }
}