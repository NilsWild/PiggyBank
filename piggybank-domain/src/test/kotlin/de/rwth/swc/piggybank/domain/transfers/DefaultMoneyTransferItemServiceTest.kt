package de.rwth.swc.piggybank.domain.transfers

import DefaultMoneyTransferItemService
import de.rwth.swc.piggybank.domain.transfers.entity.MoneyTransferItem
import de.rwth.swc.piggybank.domain.shared.valueobject.Account
import de.rwth.swc.piggybank.domain.transfers.spi.MoneyTransferItemChangeListener
import de.rwth.swc.piggybank.domain.transfers.spi.MoneyTransferItems
import de.rwth.swc.piggybank.domain.transfers.spi.event.NewMoneyTransferItemEvent
import io.kotest.matchers.collections.shouldContainExactly
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import io.mockk.every
import org.instancio.Instancio
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class DefaultMoneyTransferItemServiceTest {

    @MockK
    lateinit var transferItems: MoneyTransferItems

    @MockK
    lateinit var listener: MoneyTransferItemChangeListener

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
    }

    @Test
    fun `should add a new MoneyTransferItem and notify listeners`() {
        // Arrange
        val service = DefaultMoneyTransferItemService(transferItems, listOf(listener))
        val item = createMoneyTransferItem()

        // Act
        service.add(item)

        // Assert
        verify { transferItems.save(item) }
        verify { listener.onNewMoneyTransferItem(NewMoneyTransferItemEvent(item)) }
    }

    @Test
    fun `should return all MoneyTransferItems`() {
        // Arrange
        val service = DefaultMoneyTransferItemService(transferItems, emptyList())
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
        val service = DefaultMoneyTransferItemService(transferItems, emptyList())
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
        val service = DefaultMoneyTransferItemService(transferItems, emptyList())
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

    private fun createAccount(): Account {
        return Instancio.create(Account::class.java)
    }
}