package de.rwth.swc.piggybank.transfers.repository.entity

import jakarta.persistence.*
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
    val identifier: String,
    @OneToMany
    val transfers: Set<MoneyTransferItemEntity> = emptySet(),
    @OneToOne
    val watch: AccountWatchEntity? = null
)