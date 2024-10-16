package de.rwth.swc.piggybank.transfers.repository.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.OneToOne
import java.util.*

/**
 * Entity class representing an account watch in the persistence layer.
 *
 * @property id The unique identifier of the account watch.
 * @property accountReference The account reference of the account being watched.
 */
@Entity
data class AccountWatchEntity(
    @Id
    val id: UUID,
    @OneToOne(cascade = [CascadeType.ALL])
    val accountReference: AccountEntity
)