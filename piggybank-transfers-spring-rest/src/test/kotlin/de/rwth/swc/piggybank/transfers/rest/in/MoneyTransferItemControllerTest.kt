package de.rwth.swc.piggybank.transfers.rest.`in`

import de.interact.domain.rest.RestMessage
import de.interact.junit.jupiter.annotation.InterACtTest
import de.interact.rest.TestRestClient
import de.interact.test.inherently
import de.rwth.swc.piggybank.domain.shared.valueobject.Currency.Companion.EUR
import de.rwth.swc.piggybank.domain.shared.valueobject.Currency.Companion.USD
import de.rwth.swc.piggybank.domain.shared.valueobject.CurrencyISOCode
import de.rwth.swc.piggybank.domain.transfers.api.AccountWatchService
import de.rwth.swc.piggybank.domain.transfers.spi.MoneyTransferItems
import de.rwth.swc.piggybank.transfers.rest.`in`.dto.MoneyTransferDto
import de.rwth.swc.piggybank.transfers.rest.`in`.mapping.MoneyTransferDtoMapper
import de.rwth.swc.piggybank.util.KSelect.Companion.field
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
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

    @Autowired
    lateinit var moneyTransferMapper: MoneyTransferDtoMapper

    private lateinit var testClient: TestRestClient

    @BeforeEach
    fun startMockServer() {
        mockServer.reset()
        testClient = InterACtConfig.testRestClient(webClientBuilder, port)
    }

    @InterACtTest
    @MethodSource("moneyTransfer")
    fun `add money transfer item to watched account should call account service`(stimulus: RestMessage.Request<MoneyTransferDto>, accountServiceResponse: RestMessage.Response<String>) {

        val moneyTransferDto = stimulus.body!!
        accountWatchService.addWatchedAccount(moneyTransferDto.target)

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

        val mappedMoneyTransferDto = moneyTransferMapper.toDomain(moneyTransferDto)

        inherently {
            moneyTransferItems.getAll() shouldContainExactly listOf(mappedMoneyTransferDto)
            moneyTransferItems.getAllTransferredToTarget(moneyTransferDto.target) shouldContainExactly listOf(
                mappedMoneyTransferDto
            )
            moneyTransferItems.getAllReceivedFromSource(moneyTransferDto.source) shouldContainExactly listOf(
                mappedMoneyTransferDto
            )
            mockServer.verify(
                HttpRequest.request()
                    .withMethod("POST")
                    .withPath(accountServiceResponse.path)
            )
        }
    }

    @InterACtTest
    @MethodSource("moneyTransfer")
    fun `add money transfer item to non watched account should not call account service`(stimulus: RestMessage.Request<MoneyTransferDto>, accountServiceResponse: RestMessage.Response<String>) {

        val moneyTransferDto = stimulus.body!!

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

        inherently {
            moneyTransferItems.getAll() shouldContainExactly emptyList()
            moneyTransferItems.getAllTransferredToTarget(moneyTransferDto.target) shouldContainExactly emptyList()
            moneyTransferItems.getAllReceivedFromSource(moneyTransferDto.source) shouldContainExactly emptyList()
            mockServer.verifyZeroInteractions()
        }
    }

    fun moneyTransfer(): Stream<Arguments> {
        val moneyTransferItem = Instancio.of(MoneyTransferDto::class.java).supply(
            field(MoneyTransferDto::currencyIsoCode)
        ) { random -> random.oneOf(EUR.isoCode.value, USD.isoCode.value) }.create()

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