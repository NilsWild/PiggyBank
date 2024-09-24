package de.rwth.swc.piggybank.domain.moneyconversion

import de.rwth.swc.piggybank.domain.moneyconversion.api.MoneyConversionService
import de.rwth.swc.piggybank.domain.moneyconversion.spi.CurrencyConverter
import de.rwth.swc.piggybank.domain.shared.valueobject.Currency
import de.rwth.swc.piggybank.domain.shared.valueobject.Money

/**
 * Default implementation of the MoneyConversionService interface.
 *
 * @property currencyConverters A list of available currency converters.
 */
class DefaultMoneyConversionService(private val currencyConverters: List<CurrencyConverter>) : MoneyConversionService {

    /**
     * Converts the given money to the specified target currency using an appropriate converter.
     *
     * @param money The money to convert.
     * @param targetCurrency The target currency.
     * @return The converted money in the target currency.
     * @throws IllegalArgumentException If no suitable converter is found.
     */
    override fun convert(money: Money, targetCurrency: Currency): Money {
        val converter = currencyConverters.find { it.canConvert(money.currency, targetCurrency) }
        return converter?.convert(money, targetCurrency)
            ?: throw IllegalArgumentException("No converter found for ${money.currency} to $targetCurrency")
    }
}