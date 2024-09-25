package de.rwth.swc.piggybank.transfers.repository.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id

/**
 * Entity class representing an account in the persistence layer.
 *
 * @property amount The actual amount.
 * @property currency The currency of the amount.
 */
@Entity
data class AmountEntity(
    @Id
    val amount: String,
    val currency: String
)