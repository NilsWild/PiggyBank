package de.rwth.swc.piggybank.domain.accounttwin

import de.rwth.swc.piggybank.domain.accounttwin.entity.AccountTwin
import de.rwth.swc.piggybank.domain.accounttwin.spi.AccountTwins
import de.rwth.swc.piggybank.domain.accounttwin.spi.MoneyConverter
import de.rwth.swc.piggybank.domain.shared.valueobject.*
import io.mockk.Runs
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class DefaultAccountTwinServiceTest {

    @MockK
    lateinit var accountTwins: AccountTwins

    @MockK
    lateinit var moneyConverter: MoneyConverter

    private lateinit var service: DefaultAccountTwinService

    @BeforeEach
    fun setUp() {
        service = DefaultAccountTwinService(accountTwins, moneyConverter)
    }

    @Test
    fun `test transaction in EUR`() {
        val account1 = Account(AccountType("TEST1"), AccountIdentifier("TESTACCOUNT1"))
        val account2 = Account(AccountType("TEST2"), AccountIdentifier("TESTACCOUNT2"))

        val accountTwin1 = AccountTwin(account1, Money.from(100.0, Currency.EUR))
        val accountTwin2 = AccountTwin(account1, Money.from(100.0, Currency.EUR))
        val amount = Money.from(50.0, Currency.EUR)

        every { accountTwins.getAccount(account1) } returns accountTwin1
        every { accountTwins.getAccount(account2) } returns accountTwin2
        every { accountTwins.save(any()) } just Runs

        service.registerTransaction(account1, account2, amount)

        val accountTwinResult1 = AccountTwin(account1, Money.from(50.0, Currency.EUR))
        val accountTwinResult2 = AccountTwin(account1, Money.from(150.0, Currency.EUR))

        verify { accountTwins.save(accountTwinResult1) }
        verify { accountTwins.save(accountTwinResult2) }
    }
}