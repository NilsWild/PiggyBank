package de.rwth.swc.piggybank.transfers.rest.`in`

import de.interact.domain.rest.RestMessage
import de.interact.junit.jupiter.annotation.InterACtTest
import de.interact.rest.TestRestClient
import de.rwth.swc.piggybank.domain.shared.valueobject.AccountIdentifier
import de.rwth.swc.piggybank.domain.shared.valueobject.AccountReference
import de.rwth.swc.piggybank.domain.shared.valueobject.AccountType
import de.rwth.swc.piggybank.domain.transfers.api.AccountWatchService
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
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
@Import(InterACtConfig::class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class AccountWatchControllerTest {

    @LocalServerPort
    private lateinit var port: Number

    @Autowired
    private lateinit var webClientBuilder: WebClient.Builder

    @Autowired
    lateinit var accountWatchService: AccountWatchService

    private lateinit var testClient: TestRestClient

    @BeforeEach
    fun setUp() {
        testClient = InterACtConfig.testRestClient(webClientBuilder, port)
    }

    @InterACtTest
    @MethodSource("accountReference")
    fun `add watched account should add a new watched account`(accountReference: RestMessage.Request<AccountReference>) {

        testClient.prepare(HttpMethod.POST, accountReference).exchangeToMono {
            it.statusCode() shouldBe HttpStatusCode.valueOf(200)
            it.bodyToMono<String>()
        }.block()

        accountWatchService.isAccountWatched(accountReference.body!!) shouldBe true
    }

    @InterACtTest
    @MethodSource("accountReference")
    fun `add already watched account should not change anything`(accountReference: RestMessage.Request<AccountReference>) {

        accountWatchService.addWatchedAccount(accountReference.body!!)

        testClient.prepare(HttpMethod.POST, accountReference).exchangeToMono {
            it.statusCode() shouldBe HttpStatusCode.valueOf(200)
            it.bodyToMono<String>()
        }.block()

        accountWatchService.isAccountWatched(accountReference.body!!) shouldBe true
    }

    fun accountReference(): Stream<Arguments> = Stream.of(
        Arguments.of(
            RestMessage.Request(
                "/api/watches",
                emptyMap(),
                emptyMap(),
                AccountReference(
                    AccountType("BANK_ACCOUNT"),
                    AccountIdentifier("DE12345678901234567890")
                )
            )
        )
    )
}