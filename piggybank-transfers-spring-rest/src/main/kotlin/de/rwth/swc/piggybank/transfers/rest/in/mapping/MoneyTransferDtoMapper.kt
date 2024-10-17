package de.rwth.swc.piggybank.transfers.rest.`in`.mapping

import de.rwth.swc.piggybank.domain.shared.valueobject.Currency
import de.rwth.swc.piggybank.domain.shared.valueobject.Money
import de.rwth.swc.piggybank.domain.transfers.entity.MoneyTransferItem
import de.rwth.swc.piggybank.transfers.rest.`in`.dto.MoneyTransferDto
import org.springframework.stereotype.Component

/**
 * Mapper interface for converting between MoneyTransferItem domain objects and MoneyTransferDto REST objects.
 */
interface MoneyTransferDtoMapper {

    /**
     * Converts a MoneyTransferDto REST object to a MoneyTransferItem domain object.
     *
     * @param dto The MoneyTransferDto REST object.
     * @return The corresponding MoneyTransferItem domain object.
     */
    fun toDomain(dto: MoneyTransferDto): MoneyTransferItem

    /**
     * Converts a MoneyTransferItem domain object to a MoneyTransferDto REST object.
     *
     * @param domain The MoneyTransferItem domain object.
     * @return The corresponding MoneyTransferDto REST object.
     */
    fun toDto(domain: MoneyTransferItem): MoneyTransferDto
}

/**
 * Implementation of the MoneyTransferDtoMapper interface.
 */
@Component
class MoneyTransferDtoMapperImpl : MoneyTransferDtoMapper {

    override fun toDomain(dto: MoneyTransferDto): MoneyTransferItem {
        return MoneyTransferItem(
            dto.id,
            Money(dto.amount, Currency.fromISOCode(dto.currencyIsoCode)),
            dto.valueDate,
            dto.purpose,
            dto.source,
            dto.target
        )
    }

    override fun toDto(domain: MoneyTransferItem): MoneyTransferDto {
        return MoneyTransferDto(
            domain.id,
            domain.amount.amount,
            domain.amount.currency.isoCode,
            domain.valueDate,
            domain.purpose,
            domain.source,
            domain.target
        )
    }
}