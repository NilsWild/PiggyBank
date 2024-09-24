package de.rwth.swc.piggybank.domain.moneyconversion.spi

import de.rwth.swc.piggybank.domain.shared.valueobject.Currency
import de.rwth.swc.piggybank.domain.shared.valueobject.Money

/**
 * Interface for currency conversion.
 */
interface CurrencyConverter {

    /**
     * Checks if the converter can convert from one currency to another.
     *
     * @param from The source currency.
     * @param to The target currency.
     * @return True if the conversion is possible, false otherwise.
     */
    fun canConvert(from: Currency, to: Currency): Boolean

    /**
     * Converts the given money to the target currency.
     *
     * @param money The money to convert.
     * @param targetCurrency The target currency.
     * @return The converted money.
     */
    fun convert(money: Money, targetCurrency: Currency): Money
}