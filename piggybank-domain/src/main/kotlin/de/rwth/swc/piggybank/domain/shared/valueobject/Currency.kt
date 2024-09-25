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
    val decimalPlaces: DecimalPlaces
) {
    companion object {
        val EUR = Currency(CurrencyName("Euro"), CurrencySymbol("€"), DecimalPlaces(2))
        val USD = Currency(CurrencyName("US Dollar"), CurrencySymbol("$"), DecimalPlaces(2))
        val GBP = Currency(CurrencyName("British Pound"), CurrencySymbol("£"), DecimalPlaces(2))

        /**
         * Factory method to create a Currency instance from simple types.
         *
         * @param name The name of the currency.
         * @param symbol The symbol of the currency.
         * @param decimalPlaces The number of decimal places used with the currency.
         * @return A new instance of Currency.
         */
        fun from(name: String, symbol: String, decimalPlaces: Int): Currency {
            return Currency(CurrencyName(name), CurrencySymbol(symbol), DecimalPlaces(decimalPlaces))
        }

        fun fromISOCode(id: String): Currency {
            return when (id) {
                "EUR" -> EUR
                "USD" -> USD
                "GBP" -> GBP
                else -> {
                    throw IllegalArgumentException("Invalid currency id: $id")
                }
            }
        }
    }

    fun toISOCode(): String {
        return when (name.value) {
            "Euro" -> "EUR"
            "US Dollar" -> "USD"
            "British Pound" -> "GBP"
            else -> {
                throw IllegalArgumentException("Invalid currency name: $name")
            }
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