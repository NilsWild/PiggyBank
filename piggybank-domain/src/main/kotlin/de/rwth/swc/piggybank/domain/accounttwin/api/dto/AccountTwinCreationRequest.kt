package de.rwth.swc.piggybank.domain.accounttwin.api.dto

import de.rwth.swc.piggybank.domain.accounttwin.valueobject.AccountTwinId
import de.rwth.swc.piggybank.domain.shared.valueobject.AccountReference
import de.rwth.swc.piggybank.domain.shared.valueobject.Currency

/**
 * Data class representing a request to create an account twin.
 */
data class AccountTwinCreationRequest(
    val id: AccountTwinId,
    val accountReference: AccountReference,
    val currency: Currency
)