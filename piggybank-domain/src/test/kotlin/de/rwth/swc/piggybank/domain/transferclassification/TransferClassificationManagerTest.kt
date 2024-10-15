package de.rwth.swc.piggybank.domain.transferclassification

import de.rwth.swc.piggybank.domain.transferclassification.api.dto.Transfer
import de.rwth.swc.piggybank.domain.transferclassification.valueobject.TransferClassification
import de.rwth.swc.piggybank.domain.transferclassification.api.dto.TransferClassificationResult
import de.rwth.swc.piggybank.domain.transferclassification.spi.TransferClassifier
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.instancio.Instancio
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class TransferClassificationManagerTest {

    @MockK
    lateinit var transferClassifier: TransferClassifier

    @Test
    fun `classify should return classification result`() {
        // Given
        val transfer = Instancio.of(Transfer::class.java).create()
        val transferClassification = TransferClassification("classification")

        every { transferClassifier.classify(any()) } returns setOf(transferClassification)
        val transferClassificationManager = TransferClassificationManager(listOf(transferClassifier))

        // When
        val result = transferClassificationManager.classify(transfer)

        // Then
        result shouldBe TransferClassificationResult(transfer.id, setOf(transferClassification))
    }
}