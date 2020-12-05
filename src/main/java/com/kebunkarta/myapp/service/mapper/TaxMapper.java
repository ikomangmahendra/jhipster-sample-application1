package com.kebunkarta.myapp.service.mapper;


import com.kebunkarta.myapp.domain.*;
import com.kebunkarta.myapp.service.dto.TaxDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Tax} and its DTO {@link TaxDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TaxMapper extends EntityMapper<TaxDTO, Tax> {



    default Tax fromId(Long id) {
        if (id == null) {
            return null;
        }
        Tax tax = new Tax();
        tax.setId(id);
        return tax;
    }
}
