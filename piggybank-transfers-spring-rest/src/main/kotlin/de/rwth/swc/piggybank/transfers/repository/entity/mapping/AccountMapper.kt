package de.rwth.swc.piggybank.transfers.repository.entity.mapping

import com.fasterxml.jackson.annotation.ObjectIdGenerators.UUIDGenerator
import de.rwth.swc.piggybank.domain.shared.valueobject.Account
import de.rwth.swc.piggybank.domain.shared.valueobject.Money
import de.rwth.swc.piggybank.domain.transfers.entity.MoneyTransferItem
import de.rwth.swc.piggybank.domain.transfers.valueobject.ValueDate
import de.rwth.swc.piggybank.transfers.repository.entity.AccountEntity
import de.rwth.swc.piggybank.transfers.repository.entity.MoneyTransferItemEntity
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.util.*
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
    fun toPersistence(item: Account): AccountEntity

    /**
     * Converts a MoneyTransferItemEntity persistence object to a MoneyTransferItem domain object.
     *
     * @param entity The MoneyTransferItemEntity persistence object.
     * @return The corresponding MoneyTransferItem domain object.
     */
    fun toDomain(entity: AccountEntity): Account
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
    override fun toPersistence(item: Account): AccountEntity {
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
    override fun toDomain(entity: AccountEntity): Account {
        return Account(
            identifier = AccountIdentifier(entity.identifier),
            type = AccountType(entity.type),
        )
    }
}