package de.rwth.swc.piggybank.transfers.adapter

import de.rwth.swc.piggybank.domain.transfers.spi.MoneyTransferItemChangeListener
import de.rwth.swc.piggybank.domain.transfers.spi.event.NewMoneyTransferItemEvent
import de.rwth.swc.piggybank.transfers.rest.out.AccountTwinService
import org.springframework.stereotype.Component

/**
 * Adapter class that listens for new money transfer item events and interacts with the AccountTwinService.
 *
 * @property accountTwinService The service used to add money to an account.
 */
@Component
class AccountTwinAdapter(private val accountTwinService: AccountTwinService) :
    MoneyTransferItemChangeListener {

    /**
     * Handles the event of a new money transfer item by calling the AccountTwinService to add money to the target account.
     *
     * @param event The event containing the details of the new money transfer item.
     */
    override fun onNewMoneyTransferItem(event: NewMoneyTransferItemEvent) {
        accountTwinService.addMoney(
            event.item.target.type,
            event.item.target.identifier,
            event.item.amount
        ).subscribe()
    }
}