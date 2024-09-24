package de.rwth.swc.piggybank.domain.transfers.valueobject

/**
 * Inline value class for the purpose of the transfer.
 *
 * @property value The purpose of the transfer.
 */
@JvmInline
value class Purpose(val value: String) {
    init {
        require(value.isNotBlank()) { "Purpose must not be blank" }
    }

    override fun toString(): String {
        return value
    }
}