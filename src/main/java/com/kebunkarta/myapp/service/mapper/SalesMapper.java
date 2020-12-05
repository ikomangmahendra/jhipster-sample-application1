package com.kebunkarta.myapp.service.mapper;


import com.kebunkarta.myapp.domain.*;
import com.kebunkarta.myapp.service.dto.SalesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Sales} and its DTO {@link SalesDTO}.
 */
@Mapper(componentModel = "spring", uses = {ContactMapper.class, GudangMapper.class})
public interface SalesMapper extends EntityMapper<SalesDTO, Sales> {

    @Mapping(source = "contact.id", target = "contactId")
    @Mapping(source = "warehouse.id", target = "warehouseId")
    SalesDTO toDto(Sales sales);

    @Mapping(target = "items", ignore = true)
    @Mapping(target = "removeItems", ignore = true)
    @Mapping(source = "contactId", target = "contact")
    @Mapping(source = "warehouseId", target = "warehouse")
    Sales toEntity(SalesDTO salesDTO);

    default Sales fromId(Long id) {
        if (id == null) {
            return null;
        }
        Sales sales = new Sales();
        sales.setId(id);
        return sales;
    }
}
