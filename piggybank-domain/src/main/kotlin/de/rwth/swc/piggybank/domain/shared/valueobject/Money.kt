package de.rwth.swc.piggybank.domain.shared

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
        val formattedAmount = amount.value / Math.pow(10.0, currency.decimalPlaces.value.toDouble())
        return "${currency.symbol.value} $formattedAmount"
    }
}

/**
 * Inline value class for the money amount.
 *
 * @property value The amount of money.
 */
@JvmInline
value class MoneyAmount(val value: Long)