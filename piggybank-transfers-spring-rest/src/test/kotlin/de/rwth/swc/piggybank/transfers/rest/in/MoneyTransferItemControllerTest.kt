package de.rwth.swc.piggybank.transfers.rest.`in`

import de.interact.domain.rest.RestMessage
import de.interact.junit.jupiter.annotation.InterACtTest
import de.interact.rest.TestRestClient
import de.rwth.swc.piggybank.domain.shared.valueobject.Currency
import de.rwth.swc.piggybank.domain.transfers.api.AccountWatchService
import de.rwth.swc.piggybank.domain.transfers.entity.MoneyTransferItem
import de.rwth.swc.piggybank.domain.transfers.spi.MoneyTransferItems
import de.rwth.swc.piggybank.util.CURRENCY_SUPPLIER
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import jakarta.transaction.Transactional
import org.instancio.Instancio
import org.instancio.Select.all
import org.junit.jupiter.api.BeforeEach
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
import org.springframework.http.HttpStatusCode
import org.springframework.test.annotation.DirtiesContext
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import java.util.function.Supplier
import java.util.stream.Stream

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(MockServerConfig::class, InterACtConfig::class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class MoneyTransferItemControllerTest {

    @LocalServerPort
    private lateinit var port: Number

    @Autowired
    private lateinit var webClientBuilder: WebClient.Builder

    @Autowired
    lateinit var mockServer: ClientAndServer

    @Autowired
    lateinit var moneyTransferItems: MoneyTransferItems

    @Autowired
    lateinit var accountWatchService: AccountWatchService

    private lateinit var testClient: TestRestClient

    @BeforeEach
    fun startMockServer() {
        mockServer.reset()
        testClient = InterACtConfig.testRestClient(webClientBuilder, port)
    }

    @InterACtTest
    @MethodSource("moneyTransfer")
    fun `add money transfer item to watched account should call account service`(stimulus: RestMessage.Request<MoneyTransferItem>, accountServiceResponse: RestMessage.Response<String>) {

        val moneyTransferItem = stimulus.body!!
        accountWatchService.addWatchedAccount(moneyTransferItem.target)

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

        mockServer.verify(
            HttpRequest.request()
                .withMethod("POST")
                .withPath(accountServiceResponse.path)
        )

    }

    @InterACtTest
    @MethodSource("moneyTransfer")
    fun `add money transfer item to non watched account should not call account service`(stimulus: RestMessage.Request<MoneyTransferItem>, accountServiceResponse: RestMessage.Response<String>) {

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

        moneyTransferItems.getAll() shouldContainExactly emptyList()
        moneyTransferItems.getAllTransferredToTarget(moneyTransferItem.target) shouldContainExactly emptyList()
        moneyTransferItems.getAllReceivedFromSource(moneyTransferItem.source) shouldContainExactly emptyList()

        mockServer.verifyZeroInteractions()
    }

    fun moneyTransfer(): Stream<Arguments> {
        val moneyTransferItem = Instancio.of(MoneyTransferItem::class.java).supply(
            all(Currency::class.java), Supplier { CURRENCY_SUPPLIER.create() }
        ).create()

        val stimulus = RestMessage.Request(
            "/api/transfers",
            mapOf(),
            mapOf(),
            moneyTransferItem
        )

        val response = RestMessage.Response(
            "/api/accounts/${moneyTransferItem.target.type}/${moneyTransferItem.target.identifier}/addMoney",
            mapOf(),
            mapOf(),
            "",
            200
        )

        return Stream.of(Arguments.of(stimulus, response))
    }
}