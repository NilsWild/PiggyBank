package de.rwth.swc.piggybank.domain.moneyconversion

import de.rwth.swc.piggybank.domain.moneyconversion.api.MoneyConversionService
import de.rwth.swc.piggybank.domain.moneyconversion.spi.CurrencyConverter
import de.rwth.swc.piggybank.domain.shared.valueobject.Currency
import de.rwth.swc.piggybank.domain.shared.valueobject.Money

class DefaultMoneyCoversionService(private val currencyConverters: List<CurrencyConverter>): MoneyConversionService {
    override fun convert(money: Money, targetCurrency: Currency): Money {
        val converter = currencyConverters.find { it.canConvert(money.currency, targetCurrency) }
        return converter?.convert(money, targetCurrency) ?: throw IllegalArgumentException("No converter found for ${money.currency} to $targetCurrency")
    }
}