package de.rwth.swc.piggybank.transfers.repository.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.util.*

/**
 * Entity class representing an account in the persistence layer.
 *
 * @property id The unique identifier of the account.
 * @property type The type of the account (e.g., BANK_ACCOUNT, PAYPAL).
 * @property identifier The account identifier.
 */
@Entity
data class AccountEntity(
    @Id
    val id: UUID,
    val type: String,
    val identifier: String
)