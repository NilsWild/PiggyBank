package de.rwth.swc.piggybank.util

import de.rwth.swc.piggybank.domain.shared.valueobject.Currency
import de.rwth.swc.piggybank.domain.shared.valueobject.Currency.Companion.EUR
import de.rwth.swc.piggybank.domain.shared.valueobject.Currency.Companion.USD
import org.instancio.Instancio
import org.instancio.Select

val CURRENCY_SUPPLIER = Instancio.of(Currency::class.java).supply(Select.root(), { random ->
    listOf(EUR, USD)[random.intRange(0,1)]
})