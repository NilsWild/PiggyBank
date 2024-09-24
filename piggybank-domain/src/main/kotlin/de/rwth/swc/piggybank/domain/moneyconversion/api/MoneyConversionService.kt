package de.rwth.swc.piggybank.domain.moneyconversion.api

import de.rwth.swc.piggybank.domain.shared.valueobject.Currency
import de.rwth.swc.piggybank.domain.shared.valueobject.Money

/**
 * Service interface for converting money between different currencies.
 */
interface MoneyConversionService {

    /**
     * Converts the given money to the specified target currency.
     *
     * @param money The money to convert.
     * @param targetCurrency The target currency.
     * @return The converted money in the target currency.
     */
    fun convert(money: Money, targetCurrency: Currency): Money
}