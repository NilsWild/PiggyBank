package de.rwth.swc.piggybank.transfers.rest.`in`.mapping

import de.rwth.swc.piggybank.domain.shared.valueobject.*
import de.rwth.swc.piggybank.domain.transfers.entity.MoneyTransferItem
import de.rwth.swc.piggybank.transfers.rest.`in`.datatransferobject.MoneyTransferDTO
import org.springframework.stereotype.Component

/**
 * Mapper interface for converting between MoneyTransferDTOs and MoneyTransferItem domain objects.
 */
interface MoneyTransferDTOMapper {
    /**
     * Converts a MoneyTransferDTO to a MoneyTransferItem domain object.
     *
     * @param dto The MoneyTransferDTO.
     * @return The corresponding MoneyTransferItem domain object.
     */
    fun toDomain(dto: MoneyTransferDTO): MoneyTransferItem

    /**
     * Converts a MoneyTransferItem domain object to a MoneyTransferDTO.
     *
     * @param item The MoneyTransferItem domain object.
     * @return The corresponding MoneyTransferDTO.
     */
    fun toDTO(item: MoneyTransferItem): MoneyTransferDTO
}

/**
 * Implementation of the MoneyTransferItemMapper interface.
 */
@Component
class MoneyTransferDTOMapperImpl : MoneyTransferDTOMapper {
    /**
     * Converts a MoneyTransferDTO to a MoneyTransferItem domain object.
     *
     * @param dto The MoneyTransferDTO.
     * @return The corresponding MoneyTransferItem domain object.
     */
    override fun toDomain(dto: MoneyTransferDTO): MoneyTransferItem {
        return MoneyTransferItem(
            id = dto.id,
            amount = Money(
                amount = MoneyAmount(dto.amount.toLong()),
                currency = Currency.toISOCurrencyStandard(dto.currency)
            ),
            valueDate = dto.valueDate,
            purpose = dto.purpose,
            source = dto.source,
            target = dto.target
        )
    }

    /**
     * Converts a MoneyTransferItem domain object to a MoneyTransferDTO.
     *
     * @param item The MoneyTransferItem domain object.
     * @return The corresponding MoneyTransferDTO.
     */
    override fun toDTO(item: MoneyTransferItem): MoneyTransferDTO {
        return MoneyTransferDTO(
            id = item.id,
            amount = item.amount.amount.value.toDouble(),
            currency = item.amount.currency.fromCurrencyNameToISOName(),
            valueDate = item.valueDate,
            purpose = item.purpose,
            source = item.source,
            target = item.target
        )
    }
}