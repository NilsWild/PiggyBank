package de.rwth.swc.piggybank.transfers.repository.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import java.util.*

/**
 * Entity class representing a money transfer item in the persistence layer.
 *
 * @property id The unique identifier of the money transfer item.
 * @property source The source account of the money transfer.
 * @property target The target account of the money transfer.
 * @property amount The amount of money being transferred.
 */
@Entity
data class MoneyTransferItemEntity(
    @Id
    val id: UUID,
    @ManyToOne
    val source: AccountEntity,
    @ManyToOne
    val target: AccountEntity,
    val amount: String
)