package de.rwth.swc.piggybank.transfers.repository.entity.mapping

import de.rwth.swc.piggybank.domain.transfers.entity.AccountWatch
import de.rwth.swc.piggybank.domain.transfers.valueobject.AccountWatchId
import de.rwth.swc.piggybank.transfers.repository.entity.AccountWatchEntity
import org.springframework.stereotype.Component

/**
 * Mapper for converting between [AccountWatch] and [AccountWatchEntity].
 */
interface AccountWatchMapper {

    /**
     * Converts an [AccountWatch] to an [AccountWatchEntity].
     *
     * @param watch The [AccountWatch] to convert.
     * @return The corresponding [AccountWatchEntity].
     */
    fun toPersistence(watch: AccountWatch): AccountWatchEntity

    /**
     * Converts an [AccountWatchEntity] to an [AccountWatch].
     *
     * @param entity The [AccountWatchEntity] to convert.
     * @return The corresponding [AccountWatch].
     */
    fun toDomain(entity: AccountWatchEntity): AccountWatch
}

/**
 * Implementation of the [AccountWatchMapper] interface.
 */
@Component
class AccountWatchMapperImpl(private val accountMapper: AccountMapper) : AccountWatchMapper {

    /**
     * Converts an [AccountWatch] to an [AccountWatchEntity].
     *
     * @param watch The [AccountWatch] to convert.
     * @return The corresponding [AccountWatchEntity].
     */
    override fun toPersistence(watch: AccountWatch): AccountWatchEntity {
        return AccountWatchEntity(
            id = watch.id.value,
            accountReference = accountMapper.toPersistence(watch.accountReference)
        )

    }

    /**
     * Converts an [AccountWatchEntity] to an [AccountWatch].
     *
     * @param entity The [AccountWatchEntity] to convert.
     * @return The corresponding [AccountWatch].
     */
    override fun toDomain(entity: AccountWatchEntity): AccountWatch {
        return AccountWatch(
            id = AccountWatchId(entity.id),
            accountReference = accountMapper.toDomain(entity.accountReference)
        )
    }
}