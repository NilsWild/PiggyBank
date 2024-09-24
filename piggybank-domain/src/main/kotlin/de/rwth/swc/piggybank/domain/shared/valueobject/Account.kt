package de.rwth.swc.piggybank.domain.transfers.entity

import de.rwth.swc.piggybank.domain.transfers.valueobject.AccountIdentifier
import de.rwth.swc.piggybank.domain.transfers.valueobject.AccountType

/**
 * Represents an account with a type and an identifier.
 *
 * @property type The type of the account.
 * @property identifier The identifier of the account.
 */
data class Account(
    val type: AccountType,
    val identifier: AccountIdentifier
)