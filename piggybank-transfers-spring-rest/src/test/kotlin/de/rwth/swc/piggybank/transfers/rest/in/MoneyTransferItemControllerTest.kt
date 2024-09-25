package de.rwth.swc.piggybank.transfers.rest.`in`

import com.fasterxml.jackson.databind.ObjectMapper
import de.rwth.swc.piggybank.domain.shared.valueobject.*
import de.rwth.swc.piggybank.transfers.rest.`in`.datatransferobject.MoneyTransferDTO
import de.rwth.swc.piggybank.domain.transfers.spi.MoneyTransferItems
import de.rwth.swc.piggybank.domain.transfers.valueobject.MoneyTransferItemId
import de.rwth.swc.piggybank.domain.transfers.valueobject.Purpose
import de.rwth.swc.piggybank.domain.transfers.valueobject.ValueDate
import de.rwth.swc.piggybank.transfers.rest.`in`.mapping.MoneyTransferDTOMapper
import io.kotest.matchers.collections.shouldContainExactly
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockserver.integration.ClientAndServer
import org.mockserver.model.HttpRequest
import org.mockserver.model.HttpResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import java.time.LocalDate
import java.util.*

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(MockServerConfig::class)
class MoneyTransferItemControllerTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @Autowired
    lateinit var mapper: ObjectMapper

    @Autowired
    lateinit var dtoMapper: MoneyTransferDTOMapper

    @Autowired
    lateinit var mockServer: ClientAndServer

    @Autowired
    lateinit var moneyTransferItems: MoneyTransferItems

    @BeforeEach
    fun startMockServer() {
        mockServer.reset()
    }

    @Test
    fun `add money transfer item should call account service`() {
        mockServer.`when`(
            HttpRequest.request()
                .withMethod("POST")
                .withPath("/api/accounts/BANK_ACCOUNT/987654321/addMoney")
        ).respond(
            HttpResponse.response()
                .withStatusCode(200)
        )

        val moneyTransferDTO = MoneyTransferDTO(
            id = MoneyTransferItemId(UUID.randomUUID()),
            currency = "GBP",
            amount = 100.0,
            valueDate = ValueDate(LocalDate.now()),
            purpose = Purpose("Test"),
            source = Account(AccountType("BANK_ACCOUNT"), AccountIdentifier("123456789")),
            target = Account(AccountType("BANK_ACCOUNT"), AccountIdentifier("987654321"))
        )

        webTestClient.post()
            .uri("/api/transfers")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(mapper.writeValueAsString(moneyTransferDTO))
            .exchange()
            .expectStatus().isOk

        moneyTransferItems.getAll() shouldContainExactly listOf(dtoMapper.toDomain(moneyTransferDTO))
        moneyTransferItems.getAllTransferredToTarget(moneyTransferDTO.target) shouldContainExactly listOf(
            dtoMapper.toDomain(moneyTransferDTO)
        )
        moneyTransferItems.getAllReceivedFromSource(moneyTransferDTO.source) shouldContainExactly listOf(
            dtoMapper.toDomain(moneyTransferDTO)
        )

    }
}