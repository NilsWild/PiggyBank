package de.rwth.swc.piggybank.domain.transfers.spi

import de.rwth.swc.piggybank.domain.transfers.spi.event.NewMoneyTransferItemEvent

/**
 * Listener interface for handling changes to MoneyTransferItem entities.
 */
interface MoneyTransferItemChangeListener {
    /**
     * Handles the event when a new MoneyTransferItem is added.
     *
     * @param event The event representing the addition of a new MoneyTransferItem.
     */
    fun onNewMoneyTransferItem(event: NewMoneyTransferItemEvent)
}