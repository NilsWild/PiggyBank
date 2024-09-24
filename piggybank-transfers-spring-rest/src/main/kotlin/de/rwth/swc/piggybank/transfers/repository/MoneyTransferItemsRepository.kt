package de.rwth.swc.piggybank.transfers.repository

import de.rwth.swc.piggybank.domain.shared.valueobject.Account
import de.rwth.swc.piggybank.domain.transfers.entity.MoneyTransferItem
import de.rwth.swc.piggybank.domain.transfers.spi.MoneyTransferItems
import org.springframework.stereotype.Component

@Component
class MoneyTransferItemsRepository: MoneyTransferItems {
    override fun save(item: MoneyTransferItem) {

    }

    override fun getAll(): Collection<MoneyTransferItem> {
        TODO("Not yet implemented")
    }

    override fun getAllReceivedFromSource(source: Account): Collection<MoneyTransferItem> {
        TODO("Not yet implemented")
    }

    override fun getAllTransferredToTarget(target: Account): Collection<MoneyTransferItem> {
        TODO("Not yet implemented")
    }

}