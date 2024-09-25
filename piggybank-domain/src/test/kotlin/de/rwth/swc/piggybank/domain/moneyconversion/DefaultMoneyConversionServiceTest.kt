package de.rwth.swc.piggybank.domain.moneyconversion

import de.rwth.swc.piggybank.domain.moneyconversion.spi.CurrencyConverter
import de.rwth.swc.piggybank.domain.shared.valueobject.Currency
import de.rwth.swc.piggybank.domain.shared.valueobject.Currency.Companion.EUR
import de.rwth.swc.piggybank.domain.shared.valueobject.Currency.Companion.USD
import de.rwth.swc.piggybank.domain.shared.valueobject.Money
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class DefaultMoneyConversionServiceTest {

    private val usd = USD
    private val eur = EUR
    private val moneyInUsd = Money.from(100.0, usd)
    private val moneyInEur = Money.from(85.0, eur)

    @MockK
    lateinit var mockConverter: CurrencyConverter

    private lateinit var service: DefaultMoneyConversionService

    @BeforeEach
    fun setUp() {
        service = DefaultMoneyConversionService(listOf(mockConverter))
    }

    @Test
    fun `test convert USD to EUR`() {
        every { mockConverter.canConvert(usd, eur) } returns true
        every { mockConverter.convert(moneyInUsd, eur) } returns moneyInEur

        val result = service.convert(moneyInUsd, eur)
        result shouldBe moneyInEur

        verify { mockConverter.canConvert(usd, eur) }
        verify { mockConverter.convert(moneyInUsd, eur) }
    }

    @Test
    fun `test convert EUR to USD`() {
        val expectedMoneyInUsd = Money.from(100.3, usd)
        every { mockConverter.canConvert(eur, usd) } returns true
        every { mockConverter.convert(moneyInEur, usd) } returns expectedMoneyInUsd

        val result = service.convert(moneyInEur, usd)
        result shouldBe expectedMoneyInUsd

        verify { mockConverter.canConvert(eur, usd) }
        verify { mockConverter.convert(moneyInEur, usd) }
    }

    @Test
    fun `test convert with no suitable converter`() {
        val inr = Currency.from("Rupie", "₹", 0, "INR")
        val moneyInInr = Money.from(100.0, inr)
        every { mockConverter.canConvert(inr, usd) } returns false

        shouldThrow<IllegalArgumentException> {
            service.convert(moneyInInr, usd)
        }

        verify { mockConverter.canConvert(inr, usd) }
    }
}