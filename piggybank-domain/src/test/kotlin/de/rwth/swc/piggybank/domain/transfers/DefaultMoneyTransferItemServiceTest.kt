package de.rwth.swc.piggybank.domain.transfers

import DefaultMoneyTransferItemService
import de.rwth.swc.piggybank.domain.shared.valueobject.AccountReference
import de.rwth.swc.piggybank.domain.transfers.api.AccountWatchService
import de.rwth.swc.piggybank.domain.transfers.api.MoneyTransferItemService
import de.rwth.swc.piggybank.domain.transfers.entity.MoneyTransferItem
import de.rwth.swc.piggybank.domain.transfers.spi.MoneyTransferItemChangeListener
import de.rwth.swc.piggybank.domain.transfers.spi.MoneyTransferItems
import de.rwth.swc.piggybank.domain.transfers.spi.event.NewMoneyTransferItemEvent
import io.kotest.matchers.collections.shouldContainExactly
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.instancio.Instancio
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class DefaultMoneyTransferItemServiceTest {

    @MockK
    private lateinit var transferItems: MoneyTransferItems

    @MockK
    private lateinit var listener: MoneyTransferItemChangeListener

    @MockK
    private lateinit var accountWatchService: AccountWatchService

    private val service: DefaultMoneyTransferItemService

    init {
        MockKAnnotations.init(this)
        service = DefaultMoneyTransferItemService(transferItems, accountWatchService, listOf(listener))
    }

    @Test
    fun `should add a new MoneyTransferItem and notify listeners`() {
        // Arrange
        val item = createMoneyTransferItem()
        every { accountWatchService.isAccountWatched(item.source) } returns true
        every { accountWatchService.isAccountWatched(item.target) } returns true
        every { transferItems.save(item) } answers {}
        every { listener.onNewMoneyTransferItem(any()) } answers {}

        // Act
        service.add(item)

        // Assert
        verify { transferItems.save(item) }
        verify { listener.onNewMoneyTransferItem(NewMoneyTransferItemEvent(item)) }
    }

    @Test
    fun `should return all MoneyTransferItems`() {
        // Arrange
        val items = createMoneyTransferItemList(3)
        every { transferItems.getAll() } returns items

        // Act
        val result = service.getAll()

        // Assert
        result shouldContainExactly items
    }

    @Test
    fun `should return MoneyTransferItems received from a specific source`() {
        // Arrange
        val source = createAccount()
        val items = createMoneyTransferItemList(2)
        every { transferItems.getAllReceivedFromSource(source) } returns items

        // Act
        val result = service.getReceivedFromSource(source)

        // Assert
        result shouldContainExactly items
    }

    @Test
    fun `should return MoneyTransferItems transferred to a specific target`() {
        // Arrange
        val target = createAccount()
        val items = createMoneyTransferItemList(2)
        every { transferItems.getAllTransferredToTarget(target) } returns items

        // Act
        val result = service.getTransferredToTarget(target)

        // Assert
        result shouldContainExactly items
    }

    private fun createMoneyTransferItem(): MoneyTransferItem {
        return Instancio.create(MoneyTransferItem::class.java)
    }

    private fun createMoneyTransferItemList(size: Int): List<MoneyTransferItem> {
        return Instancio.ofList(MoneyTransferItem::class.java).size(size).create()
    }

    private fun createAccount(): AccountReference {
        return Instancio.create(AccountReference::class.java)
    }
}