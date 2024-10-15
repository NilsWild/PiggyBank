package de.rwth.swc.piggybank.domain.transferclassification.api.dto

import de.rwth.swc.piggybank.domain.transferclassification.valueobject.TransferClassification
import de.rwth.swc.piggybank.domain.transferclassification.valueobject.TransferReference

/**
 * Represents a classification result of a transfer.
 */
data class TransferClassificationResult(
    val transfer: TransferReference,
    val classifications: Set<TransferClassification>
)