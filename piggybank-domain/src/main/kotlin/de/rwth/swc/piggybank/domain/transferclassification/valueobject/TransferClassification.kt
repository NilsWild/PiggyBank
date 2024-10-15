package de.rwth.swc.piggybank.domain.transferclassification.valueobject

/**
 * Represents a classification of a transfer.
 */
@JvmInline
value class TransferClassification(val value: String) {
    override fun toString(): String {
        return value
    }
}