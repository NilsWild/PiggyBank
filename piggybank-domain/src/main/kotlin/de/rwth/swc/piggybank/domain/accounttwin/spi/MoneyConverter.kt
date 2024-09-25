package de.rwth.swc.piggybank.domain.accounttwin.spi

import de.rwth.swc.piggybank.domain.shared.valueobject.Currency
import de.rwth.swc.piggybank.domain.shared.valueobject.Money

interface MoneyConverter {

    /**
     * Converts the given money to the specified target currency.
     *
     * @param money The money to convert.
     * @param targetCurrency The target currency.
     * @return The converted money in the target currency.
     */
    fun convert(money: Money, targetCurrency: Currency): Money
}