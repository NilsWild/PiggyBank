package de.rwth.swc.piggybank.domain.adapter

import de.rwth.swc.piggybank.domain.accounttwin.spi.MoneyConverter
import de.rwth.swc.piggybank.domain.moneyconversion.api.MoneyConversionService
import de.rwth.swc.piggybank.domain.shared.valueobject.Currency
import de.rwth.swc.piggybank.domain.shared.valueobject.Money

class AccountTwinToMoneyConversion(private val moneyConversionService: MoneyConversionService): MoneyConverter{
    override fun convert(money: Money, targetCurrency: Currency): Money {
        return moneyConversionService.convert(money, targetCurrency)
    }
}