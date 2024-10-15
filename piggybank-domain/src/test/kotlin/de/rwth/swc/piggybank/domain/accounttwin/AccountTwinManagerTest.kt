package de.rwth.swc.piggybank.domain.accounttwin

import de.rwth.swc.piggybank.domain.accounttwin.api.dto.AccountTwinCreationRequest
import de.rwth.swc.piggybank.domain.accounttwin.api.dto.Transfer
import de.rwth.swc.piggybank.domain.accounttwin.entity.AccountTwin
import de.rwth.swc.piggybank.domain.accounttwin.spi.Accounts
import de.rwth.swc.piggybank.domain.shared.valueobject.Currency
import de.rwth.swc.piggybank.domain.shared.valueobject.Money
import de.rwth.swc.piggybank.domain.shared.valueobject.MoneyAmount
import de.rwth.swc.piggybank.domain.util.CURRENCY_SUPPLIER
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.slot
import org.instancio.Instancio
import org.instancio.Select.all
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.util.function.Supplier

@ExtendWith(MockKExtension::class)
class AccountTwinManagerTest {

    @MockK
    private lateinit var accounts: Accounts

    @Test
    fun `createAccountTwin should create a new account twin`() {
        // Given
        val accountTwinManager = AccountTwinManager(accounts)
        val request = Instancio.of(AccountTwinCreationRequest::class.java).create()
        val slot = slot<AccountTwin>()

        every { accounts.save(capture(slot)) } answers { slot.captured }

        // When
        val result = accountTwinManager.createAccountTwin(request)

        // Then
        result.id shouldBe request.id
        result.accountReference shouldBe request.accountReference
        result.balance shouldBe Money(MoneyAmount(0), request.currency)
    }

    @Test
    fun `handleTransfer should subtract from source account and add to target account`() {
        // Given
        val currency = CURRENCY_SUPPLIER.create()
        val accountTwinManager = AccountTwinManager(accounts)
        val transfer = Instancio.of(Transfer::class.java).supply(
            all(Currency::class.java), Supplier { currency }
        ).create()
        val sourceAccount = Instancio.of(AccountTwin::class.java).supply(
            all(Currency::class.java), Supplier { currency }
        ).create()
        val targetAccount = Instancio.of(AccountTwin::class.java).supply(
            all(Currency::class.java), Supplier { currency }
        ).create()

        every { accounts.findForAccount(transfer.source) } returns sourceAccount
        every { accounts.findForAccount(transfer.target) } returns targetAccount
        val slot = mutableListOf<AccountTwin>()
        every { accounts.save(capture(slot)) } answers { slot.last() }

        // When
        accountTwinManager.handleTransfer(transfer)

        // Then
        slot.find { it.id == sourceAccount.id }?.balance shouldBe sourceAccount.balance - transfer.amount
        slot.find { it.id == targetAccount.id }?.balance shouldBe targetAccount.balance + transfer.amount
    }

    @Test
    fun `handleTransfer should not subtract from source account if it does not exist`() {
        // Given
        val currency = CURRENCY_SUPPLIER.create()
        val accountTwinManager = AccountTwinManager(accounts)
        val transfer = Instancio.of(Transfer::class.java).supply(
            all(Currency::class.java), Supplier { currency }
        ).create()
        val targetAccount = Instancio.of(AccountTwin::class.java).supply(
            all(Currency::class.java), Supplier { currency }
        ).create()
        val slot = mutableListOf<AccountTwin>()

        every { accounts.findForAccount(transfer.source) } returns null
        every { accounts.findForAccount(transfer.target) } returns targetAccount
        every { accounts.save(capture(slot)) } answers { slot.last() }

        // When
        accountTwinManager.handleTransfer(transfer)

        // Then
        slot.find { it.id == targetAccount.id }?.balance shouldBe targetAccount.balance + transfer.amount
    }

    @Test
    fun `handleTransfer should not add to target account if it does not exist`() {
        // Given
        val currency = CURRENCY_SUPPLIER.create()
        val accountTwinManager = AccountTwinManager(accounts)
        val transfer = Instancio.of(Transfer::class.java).supply(
            all(Currency::class.java), Supplier { currency }
        ).create()
        val sourceAccount = Instancio.of(AccountTwin::class.java).supply(
            all(Currency::class.java), Supplier { currency }
        ).create()
        val slot = mutableListOf<AccountTwin>()

        every { accounts.findForAccount(transfer.source) } returns sourceAccount
        every { accounts.findForAccount(transfer.target) } returns null
        every { accounts.save(capture(slot)) } answers { slot.last() }

        // When
        accountTwinManager.handleTransfer(transfer)

        // Then
        slot.find { it.id == sourceAccount.id }?.balance shouldBe sourceAccount.balance - transfer.amount
    }
}