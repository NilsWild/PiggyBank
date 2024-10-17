package de.rwth.swc.piggybank.transfers.rest.`in`.dto

import de.rwth.swc.piggybank.domain.shared.valueobject.AccountReference
import de.rwth.swc.piggybank.domain.shared.valueobject.CurrencyISOCode
import de.rwth.swc.piggybank.domain.shared.valueobject.MoneyAmount
import de.rwth.swc.piggybank.domain.transfers.valueobject.MoneyTransferItemId
import de.rwth.swc.piggybank.domain.transfers.valueobject.Purpose
import de.rwth.swc.piggybank.domain.transfers.valueobject.ValueDate

/**
 * Data transfer object for money transfers via REST.
 *
 * @property id The unique identifier of the money transfer.
 * @property amount The amount of money to be transferred.
 * @property currencyIsoCode The ISO code of the currency of the money to be transferred.
 * @property valueDate The date when the money transfer should be executed.
 * @property purpose The purpose of the money transfer.
 * @property source The account from which the money is transferred.
 * @property target The account to which the money is transferred.
 */
data class MoneyTransferDto(
    val id: MoneyTransferItemId,
    val amount: MoneyAmount,
    val currencyIsoCode: CurrencyISOCode,
    val valueDate: ValueDate,
    val purpose: Purpose,
    val source: AccountReference,
    val target: AccountReference
)