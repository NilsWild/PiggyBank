package de.rwth.swc.piggybank.domain.shared.valueobject

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

/**
 * Inline value class for the account identifier. Could be an IBAN, for example.
 *
 * @property value The identifier of the account.
 */
@JvmInline
value class AccountIdentifier(val value: String) {
    init {
        require(value.isNotBlank()) { "Account identifier must not be blank" }
    }

    override fun toString(): String {
        return value
    }
}

/**
 * Inline value class for the account type. Could be a BankAccount, for example.
 * The identifier for a BankAccount could be an IBAN.
 *
 * @property value The type of the account.
 */
@JvmInline
value class AccountType(val value: String) {
    init {
        require(value.isNotBlank()) { "Account type must not be blank" }
    }

    override fun toString(): String {
        return value
    }
}