import de.rwth.swc.piggybank.domain.transfers.api.MoneyTransferItemService
import de.rwth.swc.piggybank.domain.transfers.entity.MoneyTransferItem
import de.rwth.swc.piggybank.domain.shared.valueobject.Account
import de.rwth.swc.piggybank.domain.transfers.spi.MoneyTransferItems
import de.rwth.swc.piggybank.domain.transfers.spi.MoneyTransferItemChangeListener
import de.rwth.swc.piggybank.domain.transfers.spi.event.NewMoneyTransferItemEvent

/**
 * Default implementation of the MoneyTransferItemService interface.
 *
 * @property transferItems The SPI interface for managing money transfer items.
 * @property listeners The list of listeners to notify about changes to money transfer items.
 */
class DefaultMoneyTransferItemService(
    private val transferItems: MoneyTransferItems,
    private val listeners: List<MoneyTransferItemChangeListener>
) : MoneyTransferItemService {

    override fun add(item: MoneyTransferItem) {
        transferItems.save(item)
        notifyListeners(NewMoneyTransferItemEvent(item))
    }

    override fun getAll(): Collection<MoneyTransferItem> {
        return transferItems.getAll()
    }

    override fun getReceivedFromSource(source: Account): Collection<MoneyTransferItem> {
        return transferItems.getAllReceivedFromSource(source)
    }

    override fun getTransferredToTarget(target: Account): Collection<MoneyTransferItem> {
        return transferItems.getAllTransferredToTarget(target)
    }

    private fun notifyListeners(event: NewMoneyTransferItemEvent) {
        listeners.forEach { it.onNewMoneyTransferItem(event) }
    }
}