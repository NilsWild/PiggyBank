package de.rwth.swc.piggybank.transfers.repository.entity.mapping

import de.rwth.swc.piggybank.domain.shared.valueobject.Account
import de.rwth.swc.piggybank.domain.shared.valueobject.AccountIdentifier
import de.rwth.swc.piggybank.domain.shared.valueobject.AccountType
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AccountMapperTest {
    @Autowired
    lateinit var mapper: AccountMapper

    @Test
    fun `same acccounts are mapped to same id`() {
        val account1 = Account(
            type = AccountType("TEST"),
            identifier = AccountIdentifier("TESTACCOUNT")
        )
        val account2 = account1.copy()

        val persistentAccount1 = mapper.toPersistence(account1)
        val persistentAccount2 = mapper.toPersistence(account2)
        persistentAccount1.type shouldBe persistentAccount2.type
        persistentAccount1.identifier shouldBe persistentAccount2.identifier
        persistentAccount1.id shouldBe persistentAccount2.id
    }

    @Test
    fun `different accounts are mapped to a different id (type differs)`() {
        val account1 = Account(
            type = AccountType("TEST1"),
            identifier = AccountIdentifier("TESTACCOUNT"),
        )
        val account2 = Account(
            type = AccountType("TEST2"),
            identifier = AccountIdentifier("TESTACCOUNT"),
        )

        val persistentAccount1 = mapper.toPersistence(account1)
        val persistentAccount2 = mapper.toPersistence(account2)

        persistentAccount1.type shouldNotBe persistentAccount2.type
        persistentAccount1.identifier shouldBe persistentAccount2.identifier
        persistentAccount1.id shouldNotBe persistentAccount2.id
    }

    @Test
    fun `different accounts are mapped to a different id (identifier differs)`() {
        val account1 = Account(
            type = AccountType("TEST"),
            identifier = AccountIdentifier("TESTACCOUNT1"),
        )
        val account2 = Account(
            type = AccountType("TEST"),
            identifier = AccountIdentifier("TESTACCOUNT2"),
        )

        val persistentAccount1 = mapper.toPersistence(account1)
        val persistentAccount2 = mapper.toPersistence(account2)

        persistentAccount1.type shouldBe persistentAccount2.type
        persistentAccount1.identifier shouldNotBe persistentAccount2.identifier
        persistentAccount1.id shouldNotBe persistentAccount2.id
    }

    @Test
    fun `different accounts are mapped to a different id (type and identifier differ)`() {
        val account1 = Account(
            type = AccountType("TEST1"),
            identifier = AccountIdentifier("TESTACCOUNT1"),
        )
        val account2 = Account(
            type = AccountType("TEST2"),
            identifier = AccountIdentifier("TESTACCOUNT2"),
        )

        val persistentAccount1 = mapper.toPersistence(account1)
        val persistentAccount2 = mapper.toPersistence(account2)

        persistentAccount1.type shouldNotBe persistentAccount2.type
        persistentAccount1.identifier shouldNotBe persistentAccount2.identifier
        persistentAccount1.id shouldNotBe persistentAccount2.id
    }
}