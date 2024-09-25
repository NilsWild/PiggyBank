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
    fun `test money addition with same currency`() {
        val money1 = Money.from(100.0, Currency.USD)
        val money2 = Money.from(50.0, Currency.USD)

        val money = money1 + money2

        money.toString() shouldBe "$ 150,00"
    }

    @Test
    fun `test money addition with different currency`() {
        val money1 = Money.from(100.0, Currency.USD)
        val money2 = Money.from(50.0, Currency.EUR)

        shouldThrow<IllegalArgumentException> {
            money1 + money2
        }
    }

    @Test
    fun `test money subtraction with same currency`() {
        val money1 = Money.from(100.0, Currency.USD)
        val money2 = Money.from(50.0, Currency.USD)

        val money = money1 - money2

        money.toString() shouldBe "$ 50,00"
    }

    @Test
    fun `test money subtraction with different currency`() {
        val money1 = Money.from(100.0, Currency.USD)
        val money2 = Money.from(50.0, Currency.EUR)

        shouldThrow<IllegalArgumentException> {
            money1 - money2
        }
    }
}