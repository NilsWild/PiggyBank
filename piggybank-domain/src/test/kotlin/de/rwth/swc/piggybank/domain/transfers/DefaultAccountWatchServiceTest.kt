package de.rwth.swc.piggybank.domain.transfers

import de.rwth.swc.piggybank.domain.shared.valueobject.AccountReference
import de.rwth.swc.piggybank.domain.transfers.api.AccountWatchService
import de.rwth.swc.piggybank.domain.transfers.entity.AccountWatch
import de.rwth.swc.piggybank.domain.transfers.spi.AccountWatches
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.instancio.Instancio
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class DefaultAccountWatchServiceTest {

    @MockK
    private lateinit var accountWatches: AccountWatches

    @InjectMockKs
    private lateinit var accountWatchService: DefaultAccountWatchService

    @Test
    fun `can create account watch`() {
        // given
        val accountReference = Instancio.of(AccountReference::class.java).create()
        every { accountWatches.save(any()) } answers { firstArg() }

        // when
        val accountWatch = accountWatchService.addWatchedAccount(accountReference)

        // then
        accountWatch.accountReference shouldBe accountReference
    }

    @Test
    fun `can determine if account is watched`() {
        // given
        val accountReference = Instancio.of(AccountReference::class.java).create()
        val slot = mutableListOf<AccountWatch>()
        every { accountWatches.save(capture(slot)) } answers { firstArg() }
        every { accountWatches.findForAccount(accountReference) } answers { slot.first { it.accountReference == accountReference } }

        // when
        accountWatchService.addWatchedAccount(accountReference)

        // then
        accountWatchService.isAccountWatched(accountReference) shouldBe true
    }

}