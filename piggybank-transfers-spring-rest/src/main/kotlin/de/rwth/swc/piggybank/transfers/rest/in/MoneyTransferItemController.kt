package de.rwth.swc.piggybank.transfers.rest.`in`

import de.rwth.swc.piggybank.domain.transfers.api.MoneyTransferItemService
import de.rwth.swc.piggybank.transfers.rest.`in`.datatransferobject.MoneyTransferDTO
import de.rwth.swc.piggybank.transfers.rest.`in`.mapping.MoneyTransferDTOMapper
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
class MoneyTransferItemController(
    private val moneyTransferItemService: MoneyTransferItemService,
    private val mapper: MoneyTransferDTOMapper
) {

    /**
     * Endpoint to create a new money transfer item.
     *
     * @param moneyTransferItem The money transfer item to be created.
     */
    @PostMapping
    fun createMoneyTransferItem(@RequestBody moneyTransferItem: MoneyTransferDTO) {
        moneyTransferItemService.add(mapper.toDomain(moneyTransferItem))
    }

}