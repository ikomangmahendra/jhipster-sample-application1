package com.kebunkarta.myapp.service.mapper;


import com.kebunkarta.myapp.domain.*;
import com.kebunkarta.myapp.service.dto.BarangDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Barang} and its DTO {@link BarangDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BarangMapper extends EntityMapper<BarangDTO, Barang> {



    default Barang fromId(Long id) {
        if (id == null) {
            return null;
        }
        Barang barang = new Barang();
        barang.setId(id);
        return barang;
    }
}
