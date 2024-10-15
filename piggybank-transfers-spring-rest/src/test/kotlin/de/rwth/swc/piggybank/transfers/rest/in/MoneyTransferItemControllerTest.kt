package de.rwth.swc.piggybank.transfers.rest.`in`

import com.fasterxml.jackson.databind.ObjectMapper
import de.interact.domain.rest.RestMessage
import de.interact.junit.jupiter.annotation.InterACtTest
import de.interact.rest.TestRestClient
import de.rwth.swc.piggybank.domain.shared.valueobject.*
import de.rwth.swc.piggybank.domain.shared.valueobject.Currency
import de.rwth.swc.piggybank.domain.transfers.entity.MoneyTransferItem
import de.rwth.swc.piggybank.domain.transfers.spi.MoneyTransferItems
import de.rwth.swc.piggybank.domain.transfers.valueobject.MoneyTransferItemId
import de.rwth.swc.piggybank.domain.transfers.valueobject.Purpose
import de.rwth.swc.piggybank.domain.transfers.valueobject.ValueDate
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.mockserver.integration.ClientAndServer
import org.mockserver.model.HttpRequest
import org.mockserver.model.HttpResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.context.annotation.Import
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import java.time.LocalDate
import java.util.*
import java.util.stream.Stream

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(MockServerConfig::class, InterACtConfig::class)
class MoneyTransferItemControllerTest {

    @LocalServerPort
    private lateinit var port: Number

    @Autowired
    lateinit var webTestClient: WebTestClient

    @Autowired
    private lateinit var webClientBuilder: WebClient.Builder

    @Autowired
    lateinit var mapper: ObjectMapper

    @Autowired
    lateinit var mockServer: ClientAndServer

    @Autowired
    lateinit var moneyTransferItems: MoneyTransferItems

    private lateinit var testClient: TestRestClient

    @BeforeEach
    fun startMockServer() {
        mockServer.reset()
        testClient = InterACtConfig.testRestClient(webClientBuilder, port)
    }

    @InterACtTest
    @MethodSource("moneyTransfer")
    fun `add money transfer item should call account service`(stimulus: RestMessage.Request<MoneyTransferItem>, accountServiceResponse: RestMessage.Response<String>) {

        val moneyTransferItem = stimulus.body!!

        mockServer.`when`(
            HttpRequest.request()
                .withMethod("POST")
                .withPath(accountServiceResponse.path)
        ).respond(
            HttpResponse.response()
                .withStatusCode(accountServiceResponse.statusCode)
        )

        testClient.prepare(HttpMethod.POST, stimulus).exchangeToMono {
            it.statusCode() shouldBe HttpStatusCode.valueOf(200)
            it.bodyToMono<String>()
        }.block()

        moneyTransferItems.getAll() shouldContainExactly listOf(moneyTransferItem)
        moneyTransferItems.getAllTransferredToTarget(moneyTransferItem.target) shouldContainExactly listOf(
            moneyTransferItem
        )
        moneyTransferItems.getAllReceivedFromSource(moneyTransferItem.source) shouldContainExactly listOf(
            moneyTransferItem
        )

    }

    fun moneyTransfer(): Stream<Arguments> {
        val moneyTransferItem = MoneyTransferItem(
            MoneyTransferItemId(UUID.randomUUID()),
            Money.from(100.0, Currency.EUR),
            ValueDate(LocalDate.now()),
            Purpose("Test"),
            Account(AccountType("BANK_ACCOUNT"), AccountIdentifier("123456789")),
            Account(AccountType("BANK_ACCOUNT"), AccountIdentifier("987654321"))
        )

        val stimulus = RestMessage.Request(
            "/api/transfers",
            mapOf(),
            mapOf(),
            moneyTransferItem
        )

        val response = RestMessage.Response(
            "/api/accounts/BANK_ACCOUNT/987654321/addMoney",
            mapOf(),
            mapOf(),
            "",
            200
        )

        return Stream.of(Arguments.of(stimulus, response))
    }
}