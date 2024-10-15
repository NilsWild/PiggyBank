package de.rwth.swc.piggybank.domain.accounttwin.entity

import de.rwth.swc.piggybank.domain.util.KSelect.Companion.field
import de.rwth.swc.piggybank.domain.shared.valueobject.Currency
import de.rwth.swc.piggybank.domain.shared.valueobject.Money
import io.kotest.matchers.shouldBe
import org.instancio.Instancio
import org.junit.jupiter.api.Test

class AccountTwinTest {

    @Test
    fun `addToBalance should add money to balance`() {
        // Given
        val currency = Instancio.of(Currency::class.java).create()
        val money = Instancio.of(Money::class.java).set(
            field(Money::currency), currency
        )
        val startBalance = money.create()
        val accountTwin = Instancio.of(AccountTwin::class.java).set(
            field(AccountTwin::balance), startBalance
        ).create()
        val transferAmount = money.create()

        // When
        val result = accountTwin.addToBalance(transferAmount)

        // Then
        result.balance shouldBe startBalance + transferAmount
    }
}