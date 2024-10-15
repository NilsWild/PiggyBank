package de.rwth.swc.piggybank.domain.transferclassification.api.dto

/**
 * Represents a classification result of a transfer.
 */
data class TransferClassificationResult(
    val transfer: TransferReference,
    val classifications: Set<TransferClassification>
)