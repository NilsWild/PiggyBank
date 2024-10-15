package de.rwth.swc.piggybank.transfers.repository.entity.mapping

import de.rwth.swc.piggybank.domain.shared.valueobject.AccountReference
import de.rwth.swc.piggybank.transfers.repository.entity.AccountEntity
import org.springframework.stereotype.Component
import com.fasterxml.uuid.Generators
import de.rwth.swc.piggybank.domain.shared.valueobject.AccountIdentifier
import de.rwth.swc.piggybank.domain.shared.valueobject.AccountType

/**
 * Mapper interface for converting between MoneyTransferItem domain objects and MoneyTransferItemEntity persistence objects.
 */
interface AccountMapper {
    /**
     * Converts a MoneyTransferItem domain object to a MoneyTransferItemEntity persistence object.
     *
     * @param item The MoneyTransferItem domain object.
     * @return The corresponding MoneyTransferItemEntity persistence object.
     */
    fun toPersistence(item: AccountReference): AccountEntity

    /**
     * Converts a MoneyTransferItemEntity persistence object to a MoneyTransferItem domain object.
     *
     * @param entity The MoneyTransferItemEntity persistence object.
     * @return The corresponding MoneyTransferItem domain object.
     */
    fun toDomain(entity: AccountEntity): AccountReference
}

/**
 * Implementation of the MoneyTransferItemMapper interface.
 */
@Component
class AccountMapperImpl : AccountMapper {
    /**
     * Converts a MoneyTransferItem domain object to a MoneyTransferItemEntity persistence object.
     *
     * @param item The MoneyTransferItem domain object.
     * @return The corresponding MoneyTransferItemEntity persistence object.
     */
    override fun toPersistence(item: AccountReference): AccountEntity {
        return AccountEntity(
            id = Generators.nameBasedGenerator().generate("id-"+item.type + "-" + item.identifier),
            type = item.type.value,
            identifier = item.identifier.value,
        )
    }

    /**
     * Converts a MoneyTransferItemEntity persistence object to a MoneyTransferItem domain object.
     *
     * @param entity The MoneyTransferItemEntity persistence object.
     * @return The corresponding MoneyTransferItem domain object.
     */
    override fun toDomain(entity: AccountEntity): AccountReference {
        return AccountReference(
            identifier = AccountIdentifier(entity.identifier),
            type = AccountType(entity.type),
        )
    }
}