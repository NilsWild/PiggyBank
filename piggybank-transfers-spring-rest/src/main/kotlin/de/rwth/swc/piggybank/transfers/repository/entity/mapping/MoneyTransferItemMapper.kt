package de.rwth.swc.piggybank.transfers.repository.entity.mapping

import de.rwth.swc.piggybank.domain.shared.valueobject.Currency
import de.rwth.swc.piggybank.domain.shared.valueobject.Money
import de.rwth.swc.piggybank.domain.shared.valueobject.MoneyAmount
import de.rwth.swc.piggybank.domain.transfers.entity.MoneyTransferItem
import de.rwth.swc.piggybank.domain.transfers.valueobject.MoneyTransferItemId
import de.rwth.swc.piggybank.domain.transfers.valueobject.Purpose
import de.rwth.swc.piggybank.domain.transfers.valueobject.ValueDate
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
class MoneyTransferItemMapperImpl(private val accountMapper: AccountMapper) : MoneyTransferItemMapper {
    /**
     * Converts a MoneyTransferItem domain object to a MoneyTransferItemEntity persistence object.
     *
     * @param item The MoneyTransferItem domain object.
     * @return The corresponding MoneyTransferItemEntity persistence object.
     */
    override fun toPersistence(item: MoneyTransferItem): MoneyTransferItemEntity {
        return MoneyTransferItemEntity(
            id = item.id.value,
            amount = item.amount.amount.value,
            currency = item.amount.currency.isoCode.value,
            valueDate = item.valueDate.value,
            purpose = item.purpose.value,
            source = accountMapper.toPersistence(item.source),
            target = accountMapper.toPersistence(item.target),
        )
    }

    /**
     * Converts a MoneyTransferItemEntity persistence object to a MoneyTransferItem domain object.
     *
     * @param entity The MoneyTransferItemEntity persistence object.
     * @return The corresponding MoneyTransferItem domain object.
     */
    override fun toDomain(entity: MoneyTransferItemEntity): MoneyTransferItem {
        return MoneyTransferItem(
            id = MoneyTransferItemId(entity.id),
            amount = Money(
                amount = MoneyAmount(entity.amount),
                currency = Currency.fromISOCode(entity.currency),
            ),
            valueDate = ValueDate(entity.valueDate),
            purpose = Purpose(entity.purpose),
            source = accountMapper.toDomain(entity.source),
            target = accountMapper.toDomain(entity.target),
        )
    }
}