package com.kebunkarta.myapp.service.mapper;


import com.kebunkarta.myapp.domain.*;
import com.kebunkarta.myapp.service.dto.GudangDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Gudang} and its DTO {@link GudangDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface GudangMapper extends EntityMapper<GudangDTO, Gudang> {


    @Mapping(target = "purchases", ignore = true)
    @Mapping(target = "removePurchase", ignore = true)
    @Mapping(target = "sales", ignore = true)
    @Mapping(target = "removeSales", ignore = true)
    Gudang toEntity(GudangDTO gudangDTO);

    default Gudang fromId(Long id) {
        if (id == null) {
            return null;
        }
        Gudang gudang = new Gudang();
        gudang.setId(id);
        return gudang;
    }
}
