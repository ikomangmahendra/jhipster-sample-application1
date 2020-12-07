package com.kebunkarta.myapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class SalesItemMapperTest {

    private SalesItemMapper salesItemMapper;

    @BeforeEach
    public void setUp() {
        salesItemMapper = new SalesItemMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(salesItemMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(salesItemMapper.fromId(null)).isNull();
    }
}
