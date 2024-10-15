package de.rwth.swc.piggybank.domain.transferclassification.api

import de.rwth.swc.piggybank.domain.transferclassification.api.dto.Transfer
import de.rwth.swc.piggybank.domain.transferclassification.api.dto.TransferClassificationResult

/**
 * Service interface for classifying transfers.
 */
interface TransferClassifierService {

    /**
     * Classifies a transfer.
     */
    fun classify(transfer: Transfer) : TransferClassificationResult

}