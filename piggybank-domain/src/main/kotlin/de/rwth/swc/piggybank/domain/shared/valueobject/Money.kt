package de.rwth.swc.piggybank.domain.shared.valueobject

import kotlin.math.pow

/**
 * Represents money with an amount and a currency.
 *
 * @property amount The amount of money.
 * @property currency The currency of the money.
 */
data class Money(
    val amount: MoneyAmount,
    val currency: Currency
) {
    override fun toString(): String {
        val formattedAmount = String.format(
            "%.${currency.decimalPlaces}f",
            amount.value / 10.0.pow(currency.decimalPlaces.value.toDouble())
        )
        return "${currency.symbol} $formattedAmount"
    }

    companion object {
        /**
         * Factory method to create a Money instance from a double representation of the amount and a currency.
         *
         * @param amount The double representation of the amount.
         * @param currency The currency of the money.
         * @return A new instance of Money.
         * @throws IllegalArgumentException If the amount cannot be exactly represented.
         */
        fun from(amount: Double, currency: Currency): Money {
            val scaleFactor = 10.0.pow(currency.decimalPlaces.value.toDouble())
            val scaledAmount = (amount * scaleFactor).toLong()
            if (scaledAmount / scaleFactor != amount) {
                throw IllegalArgumentException("Amount cannot be exactly represented in ${currency.decimalPlaces.value} decimal places: $amount")
            }
            return Money(MoneyAmount(scaledAmount), currency)
        }
    }
}

/**
 * Inline value class for the money amount.
 *
 * @property value The amount of money.
 */
@JvmInline
value class MoneyAmount(val value: Long) {
    override fun toString(): String {
        return value.toString()
    }
}