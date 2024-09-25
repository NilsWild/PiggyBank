package de.rwth.swc.piggybank.transfers.repository.entity

import de.rwth.swc.piggybank.domain.transfers.valueobject.ValueDate
import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import java.time.LocalDate
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
    @ManyToOne(cascade = [CascadeType.ALL])
    val source: AccountEntity,
    @ManyToOne(cascade = [CascadeType.ALL])
    val target: AccountEntity,
    val amount: Long,
    val currency: String,
    val valueDate: LocalDate,
    val purpose: String,
    )