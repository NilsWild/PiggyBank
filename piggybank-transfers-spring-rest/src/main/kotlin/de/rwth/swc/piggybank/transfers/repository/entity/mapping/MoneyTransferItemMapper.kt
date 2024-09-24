package de.rwth.swc.piggybank.transfers.repository.entity.mapping

import de.rwth.swc.piggybank.domain.transfers.entity.MoneyTransferItem
import de.rwth.swc.piggybank.transfers.repository.entity.MoneyTransferItemEntity
import org.springframework.stereotype.Component

/**
 * Mapper interface for converting between MoneyTransferItem domain objects and MoneyTransferItemEntity persistence objects.
 */
interface MoneyTransferItemMapper {
    /**
     * Converts a MoneyTransferItem domain object to a MoneyTransferItemEntity persistence object.
     *
     * @param item The MoneyTransferItem domain object.
     * @return The corresponding MoneyTransferItemEntity persistence object.
     */
    fun toPersistence(item: MoneyTransferItem): MoneyTransferItemEntity

    /**
     * Converts a MoneyTransferItemEntity persistence object to a MoneyTransferItem domain object.
     *
     * @param entity The MoneyTransferItemEntity persistence object.
     * @return The corresponding MoneyTransferItem domain object.
     */
    fun toDomain(entity: MoneyTransferItemEntity): MoneyTransferItem
}

/**
 * Implementation of the MoneyTransferItemMapper interface.
 */
@Component
class MoneyTransferItemMapperImpl : MoneyTransferItemMapper {
    /**
     * Converts a MoneyTransferItem domain object to a MoneyTransferItemEntity persistence object.
     *
     * @param item The MoneyTransferItem domain object.
     * @return The corresponding MoneyTransferItemEntity persistence object.
     */
    override fun toPersistence(item: MoneyTransferItem): MoneyTransferItemEntity {
        TODO("Not yet implemented")
    }

    /**
     * Converts a MoneyTransferItemEntity persistence object to a MoneyTransferItem domain object.
     *
     * @param entity The MoneyTransferItemEntity persistence object.
     * @return The corresponding MoneyTransferItem domain object.
     */
    override fun toDomain(entity: MoneyTransferItemEntity): MoneyTransferItem {
        TODO("Not yet implemented")
    }
}