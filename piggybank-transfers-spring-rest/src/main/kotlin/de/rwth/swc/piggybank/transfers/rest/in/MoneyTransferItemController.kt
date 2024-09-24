package de.rwth.swc.piggybank.transfers.rest.`in`

import de.rwth.swc.piggybank.domain.transfers.api.MoneyTransferItemService
import de.rwth.swc.piggybank.domain.transfers.entity.MoneyTransferItem
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * REST controller for handling money transfer item operations.
 *
 * @property moneyTransferItemService The service for managing money transfer items.
 */
@RestController
@RequestMapping("/api/transfers")
class MoneyTransferItemController(private val moneyTransferItemService: MoneyTransferItemService) {

    /**
     * Endpoint to create a new money transfer item.
     *
     * @param moneyTransferItem The money transfer item to be created.
     */
    @PostMapping
    fun createMoneyTransferItem(@RequestBody moneyTransferItem: MoneyTransferItem) {
        moneyTransferItemService.add(moneyTransferItem)
    }

}