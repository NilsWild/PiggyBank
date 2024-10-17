package de.rwth.swc.piggybank.transfers.rest.`in`

import de.rwth.swc.piggybank.domain.shared.valueobject.AccountReference
import de.rwth.swc.piggybank.domain.transfers.api.AccountWatchService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * REST controller for handling watched accounts.
 *
 * @property accountWatchService The service for managing watched accounts.
 */
@RestController
@RequestMapping("/api/watches")
class AccountWatchController(
    private val accountWatchService: AccountWatchService
) {

    /**
     * Endpoint to add a new watched account.
     * 
     * @param accountReference The account to watch.
     */
    @PostMapping
    fun addWatchedAccount(@RequestBody accountReference: AccountReference) {
        accountWatchService.addWatchedAccount(accountReference)
    }
}