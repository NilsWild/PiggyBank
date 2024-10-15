package de.rwth.swc.piggybank.domain.transferclassification.spi

import de.rwth.swc.piggybank.domain.transferclassification.api.dto.Transfer
import de.rwth.swc.piggybank.domain.transferclassification.valueobject.TransferClassification

/**
 * Interface for TransferClassifier implementations.
 */
interface TransferClassifier {

    fun classify(transfer: Transfer) : Set<TransferClassification>
}