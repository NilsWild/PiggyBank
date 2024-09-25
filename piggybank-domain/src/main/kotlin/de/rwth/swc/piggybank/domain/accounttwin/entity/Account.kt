package de.rwth.swc.piggybank.domain.accounttwin.entity

import de.rwth.swc.piggybank.domain.shared.valueobject.Account
import de.rwth.swc.piggybank.domain.shared.valueobject.Money

data class AccountTwin (
    val account: Account,
    var balance: Money
)