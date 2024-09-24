package de.rwth.swc.piggybank.domain.transfers.entity

import de.rwth.swc.piggybank.domain.shared.valueobject.Account
import de.rwth.swc.piggybank.domain.shared.valueobject.Money
import de.rwth.swc.piggybank.domain.transfers.valueobject.MoneyTransferItemId
import de.rwth.swc.piggybank.domain.transfers.valueobject.Purpose
import de.rwth.swc.piggybank.domain.transfers.valueobject.ValueDate


/**
 * Represents a money transfer item.
 *
 * @property id The unique identifier of the money transfer item.
 * @property amount The amount of money being transferred.
 * @property valueDate The date when the transfer happened.
 * @property purpose The purpose of the transfer.
 * @property source The source account of the transfer.
 * @property target The target account of the transfer.
 */
data class MoneyTransferItem(
    val id: MoneyTransferItemId,
    val amount: Money,
    val valueDate: ValueDate,
    val purpose: Purpose,
    val source: Account,
    val target: Account
)