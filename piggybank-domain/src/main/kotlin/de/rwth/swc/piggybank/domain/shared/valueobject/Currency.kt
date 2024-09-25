package de.rwth.swc.piggybank.domain.shared.valueobject

/**
 * Represents a currency with a name, symbol, and the number of decimal places.
 *
 * @property name The name of the currency.
 * @property symbol The symbol of the currency.
 * @property decimalPlaces The number of decimal places used with this currency.
 */
data class Currency(
    val name: CurrencyName,
    val symbol: CurrencySymbol,
    val decimalPlaces: DecimalPlaces,
    val isoCode: ISOCode
) {
    companion object {
        val EUR = Currency(CurrencyName("Euro"), CurrencySymbol("€"), DecimalPlaces(2), ISOCode("EUR"))
        val USD = Currency(CurrencyName("US Dollar"), CurrencySymbol("$"), DecimalPlaces(2), ISOCode("USD"))
        val GBP = Currency(CurrencyName("British Pound"), CurrencySymbol("£"), DecimalPlaces(2), ISOCode("GBP"))

        /**
         * Factory method to create a Currency instance from simple types.
         *
         * @param name The name of the currency.
         * @param symbol The symbol of the currency.
         * @param decimalPlaces The number of decimal places used with the currency.
         * @param isoCode The iso code of the currency
         * @return A new instance of Currency.
         */
        fun from(name: String, symbol: String, decimalPlaces: Int, isoCode: String): Currency {
            return Currency(CurrencyName(name), CurrencySymbol(symbol), DecimalPlaces(decimalPlaces), ISOCode(isoCode))
        }

        /**
         * Factory method to get a Currency by ISO code
         *
         * @param isoCode The iso code of the currency
         */
        fun fromISOCode(isoCode: String): Currency {
            return Currency::class.java.declaredFields
                .filter { it.type == Currency::class.java }  // Ensure the field is of type Currency
                .mapNotNull {
                    it.isAccessible = true
                    try {
                        it.get(null) as? Currency  // Safely cast to Currency, or null if the cast fails
                    } catch (e: Exception) {
                        null  // Ignore fields that can't be cast to Currency
                    }
                }
                .firstOrNull { it.isoCode.value == isoCode }
                ?: throw IllegalArgumentException("Invalid currency code: $isoCode")
        }
    }
}

/**
 * Inline value class for the currency name.
 *
 * @property value The name of the currency.
 */
@JvmInline
value class CurrencyName(val value: String) {
    init {
        require(value.isNotBlank()) { "Currency name must not be blank" }
    }

    override fun toString(): String {
        return value
    }
}

/**
 * Inline value class for the currency symbol.
 *
 * @property value The symbol of the currency.
 */
@JvmInline
value class CurrencySymbol(val value: String) {
    init {
        require(value.isNotBlank()) { "Currency symbol must not be blank" }
    }

    override fun toString(): String {
        return value
    }
}

/**
 * Inline value class for the number of decimal places.
 *
 * @property value The number of decimal places used with the currency.
 */
@JvmInline
value class DecimalPlaces(val value: Int) {
    init {
        require(value >= 0) { "Decimal places must be greater than or equal to 0" }
    }

    override fun toString(): String {
        return value.toString()
    }
}

/**
 * Inline value class for the ISO code.
 *
 * @property value The ISO code of a currency
 */
@JvmInline
value class ISOCode(val value: String) {
    init {
        require(value.length == 3) { "ISO code must have 3 characters" }
    }

    override fun toString(): String {
        return value
    }
}