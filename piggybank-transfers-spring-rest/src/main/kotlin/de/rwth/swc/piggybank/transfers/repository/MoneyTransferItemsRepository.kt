package de.rwth.swc.piggybank.transfers.repository

import de.rwth.swc.piggybank.domain.shared.valueobject.Account
import de.rwth.swc.piggybank.domain.transfers.entity.MoneyTransferItem
import de.rwth.swc.piggybank.domain.transfers.spi.MoneyTransferItems
import de.rwth.swc.piggybank.transfers.repository.entity.MoneyTransferItemEntity
import de.rwth.swc.piggybank.transfers.repository.entity.mapping.MoneyTransferItemMapper
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import java.util.*

/**
 * Repository interface for performing CRUD operations on MoneyTransferItemEntity.
 */
@Repository
interface MoneyTransferItemsRepo : CrudRepository<MoneyTransferItemEntity, UUID> {
    /**
     * Finds money transfer items by source account type and identifier.
     *
     * @param type The type of the source account.
     * @param identifier The identifier of the source account.
     * @return A collection of MoneyTransferItemEntity matching the criteria.
     */
    fun findBySourceTypeAndSourceIdentifier(type: String, identifier: String): Collection<MoneyTransferItemEntity>

    /**
     * Finds money transfer items by target account type and identifier.
     *
     * @param target The type of the target account.
     * @param identifier The identifier of the target account.
     * @return A collection of MoneyTransferItemEntity matching the criteria.
     */
    fun findByTargetTypeAndTargetIdentifier(target: String, identifier: String): Collection<MoneyTransferItemEntity>
}

/**
 * Implementation of the MoneyTransferItems interface using a repository.
 *
 * @property repo The repository for MoneyTransferItemEntity.
 * @property mapper The mapper for converting between domain and persistence objects.
 */
@Component
class MoneyTransferItemsRepository(
    private val repo: MoneyTransferItemsRepo,
    private val mapper: MoneyTransferItemMapper
) : MoneyTransferItems {
    /**
     * Saves a MoneyTransferItem to the repository.
     *
     * @param item The MoneyTransferItem to save.
     */
    override fun save(item: MoneyTransferItem) {
        repo.save(mapper.toPersistence(item))
    }

    /**
     * Retrieves all MoneyTransferItems from the repository.
     *
     * @return A collection of MoneyTransferItems.
     */
    override fun getAll(): Collection<MoneyTransferItem> {
        return repo.findAll().map { mapper.toDomain(it) }
    }

    /**
     * Retrieves all MoneyTransferItems received from a specific source account.
     *
     * @param source The source account.
     * @return A collection of MoneyTransferItems received from the source account.
     */
    override fun getAllReceivedFromSource(source: Account): Collection<MoneyTransferItem> {
        return repo.findBySourceTypeAndSourceIdentifier(source.type.value, source.identifier.value).map {
            mapper.toDomain(it)
        }
    }

    /**
     * Retrieves all MoneyTransferItems transferred to a specific target account.
     *
     * @param target The target account.
     * @return A collection of MoneyTransferItems transferred to the target account.
     */
    override fun getAllTransferredToTarget(target: Account): Collection<MoneyTransferItem> {
        return repo.findByTargetTypeAndTargetIdentifier(target.type.value, target.identifier.value).map {
            mapper.toDomain(it)
        }
    }
}