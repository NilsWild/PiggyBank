package de.rwth.swc.piggybank.transfers.repository

import de.rwth.swc.piggybank.domain.shared.valueobject.AccountReference
import de.rwth.swc.piggybank.domain.transfers.entity.AccountWatch
import de.rwth.swc.piggybank.domain.transfers.spi.AccountWatches
import de.rwth.swc.piggybank.transfers.repository.entity.AccountWatchEntity
import de.rwth.swc.piggybank.transfers.repository.entity.mapping.AccountWatchMapper
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface AccountWatchesRepo: CrudRepository<AccountWatchEntity, UUID> {

    fun findByAccountReferenceTypeAndAccountReferenceIdentifier(type: String, identifier: String): AccountWatchEntity?
}

@Component
class AccountWatchesRepository(
    private val mapper: AccountWatchMapper,
    private val repo: AccountWatchesRepo
): AccountWatches {

    override fun save(accountWatch: AccountWatch): AccountWatch {
        val entity = mapper.toPersistence(accountWatch)
        val persistedEntity = repo.save(entity)
        return mapper.toDomain(persistedEntity)
    }

    override fun findForAccount(accountReference: AccountReference): AccountWatch? {
        return repo.findByAccountReferenceTypeAndAccountReferenceIdentifier(
            accountReference.type.value,
            accountReference.identifier.value
        )?.let {
            mapper.toDomain(it)
        }
    }
}