package de.rwth.swc.piggybank.domain.shared.valueobject

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class MoneyTest {

    @Test
    fun `test valid money creation`() {
        val currency = Currency.USD
        val amount = 100.0

        val money = Money.from(amount, currency)

        money.amount.value shouldBe 10000L
        money.currency shouldBe currency
    }

    @Test
    fun `test invalid money creation`() {
        val currency = Currency.USD
        val amount = 100.123

        val exception = shouldThrow<IllegalArgumentException> {
            Money.from(amount, currency)
        }

        exception.message shouldBe "Amount cannot be exactly represented in 2 decimal places: 100.123"
    }

    @Test
    fun `test money toString`() {
        val currency = Currency.USD
        val amount = 100.0
        val money = Money.from(amount, currency)

        money.toString() shouldBe "$ 100,00"
    }

    @Test
    fun `test money addition`() {
        val currency = Currency.USD
        val amount1 = 100.0
        val amount2 = 200.0
        val money1 = Money.from(amount1, currency)
        val money2 = Money.from(amount2, currency)

        val result = money1 + money2

        result.amount.value shouldBe 30000L
        result.currency shouldBe currency
    }

    @Test
    fun `test money subtraction`() {
        val currency = Currency.USD
        val amount1 = 100.0
        val amount2 = 200.0
        val money1 = Money.from(amount1, currency)
        val money2 = Money.from(amount2, currency)

        val result = money2 - money1

        result.amount.value shouldBe 10000L
        result.currency shouldBe currency
    }

    @Test
    fun `test money addition with different currencies`() {
        val currency1 = Currency.USD
        val currency2 = Currency.EUR
        val amount1 = 100.0
        val amount2 = 200.0
        val money1 = Money.from(amount1, currency1)
        val money2 = Money.from(amount2, currency2)

        val exception = shouldThrow<IllegalArgumentException> {
            money1 + money2
        }

        exception.message shouldBe "Currencies must be the same. Is $currency1 and $currency2. Use conversion service"
    }

    @Test
    fun `test money subtraction with different currencies`() {
        val currency1 = Currency.USD
        val currency2 = Currency.EUR
        val amount1 = 100.0
        val amount2 = 200.0
        val money1 = Money.from(amount1, currency1)
        val money2 = Money.from(amount2, currency2)

        val exception = shouldThrow<IllegalArgumentException> {
            money1 - money2
        }

        exception.message shouldBe "Currencies must be the same. Is $currency1 and $currency2. Use conversion service"
    }
}