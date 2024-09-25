package de.rwth.swc.piggybank.domain.shared.valueobject

import io.kotest.matchers.types.shouldBeSameInstanceAs
import org.junit.jupiter.api.Test

class CurrencyTest {
    @Test
    fun `test currency fromISOCode`() {
        Currency.fromISOCode("EUR") shouldBeSameInstanceAs Currency.EUR
        Currency.fromISOCode("USD") shouldBeSameInstanceAs Currency.USD
        Currency.fromISOCode("GBP") shouldBeSameInstanceAs Currency.GBP
    }
}