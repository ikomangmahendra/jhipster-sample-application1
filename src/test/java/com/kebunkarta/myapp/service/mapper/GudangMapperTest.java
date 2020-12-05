package com.kebunkarta.myapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class GudangMapperTest {

    private GudangMapper gudangMapper;

    @BeforeEach
    public void setUp() {
        gudangMapper = new GudangMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(gudangMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(gudangMapper.fromId(null)).isNull();
    }
}
