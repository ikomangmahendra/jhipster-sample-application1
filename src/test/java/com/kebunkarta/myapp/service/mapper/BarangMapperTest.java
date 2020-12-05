package com.kebunkarta.myapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class BarangMapperTest {

    private BarangMapper barangMapper;

    @BeforeEach
    public void setUp() {
        barangMapper = new BarangMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(barangMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(barangMapper.fromId(null)).isNull();
    }
}
