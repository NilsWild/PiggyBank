package de.rwth.swc.piggybank.domain.transferclassification

import de.rwth.swc.piggybank.domain.transferclassification.api.TransferClassifierService
import de.rwth.swc.piggybank.domain.transferclassification.api.dto.Transfer
import de.rwth.swc.piggybank.domain.transferclassification.api.dto.TransferClassificationResult
import de.rwth.swc.piggybank.domain.transferclassification.spi.TransferClassifier

/**
 * Manager for classifying transfers
 */
class TransferClassificationManager(private val classifiers: List<TransferClassifier>): TransferClassifierService {
    override fun classify(transfer: Transfer): TransferClassificationResult {
        return TransferClassificationResult(
            transfer.id,
            classifiers.flatMap { it.classify(transfer) }.toSet()
        )
    }
}