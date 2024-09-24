package de.rwth.swc.piggybank.domain.transfers.spi.event

import de.rwth.swc.piggybank.domain.transfers.entity.MoneyTransferItem

/**
 * Event representing the addition of a new MoneyTransferItem.
 *
 * @property item The money transfer item that was added.
 */
data class NewMoneyTransferItemEvent(val item: MoneyTransferItem)