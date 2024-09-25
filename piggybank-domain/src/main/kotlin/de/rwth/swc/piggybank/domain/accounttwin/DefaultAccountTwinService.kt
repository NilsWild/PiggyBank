package de.rwth.swc.piggybank.domain.accounttwin

import de.rwth.swc.piggybank.domain.accounttwin.api.AccountTwinService
import de.rwth.swc.piggybank.domain.accounttwin.entity.AccountTwin
import de.rwth.swc.piggybank.domain.accounttwin.spi.AccountTwins
import de.rwth.swc.piggybank.domain.accounttwin.spi.MoneyConverter
import de.rwth.swc.piggybank.domain.shared.valueobject.Account
import de.rwth.swc.piggybank.domain.shared.valueobject.Money

class DefaultAccountTwinService(
    private val accountTwins: AccountTwins,
    private val moneyConverter: MoneyConverter
): AccountTwinService {
    override fun registerTransaction(source: Account, target: Account, amount: Money) {
        var sourceAccount = accountTwins.getAccount(source) ?: AccountTwin(source, Money.from(0.0, amount.currency))
        var targetAccount = accountTwins.getAccount(target) ?: AccountTwin(source, Money.from(0.0, amount.currency))

        val sourceConvertedAmount = if (sourceAccount.balance.currency == amount.currency) amount else moneyConverter.convert(amount, sourceAccount.balance.currency)

        val targetConvertedAmount = if (targetAccount.balance.currency == amount.currency) amount else moneyConverter.convert(amount, targetAccount.balance.currency)

        sourceAccount.balance -= sourceConvertedAmount
        targetAccount.balance += targetConvertedAmount

        accountTwins.save(sourceAccount)
        accountTwins.save(targetAccount)
    }
}