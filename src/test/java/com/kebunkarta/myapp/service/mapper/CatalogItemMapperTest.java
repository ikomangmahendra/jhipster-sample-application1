package com.kebunkarta.myapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CatalogItemMapperTest {

    private CatalogItemMapper catalogItemMapper;

    @BeforeEach
    public void setUp() {
        catalogItemMapper = new CatalogItemMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(catalogItemMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(catalogItemMapper.fromId(null)).isNull();
    }
}
