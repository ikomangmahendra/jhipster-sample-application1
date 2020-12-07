package com.kebunkarta.myapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class PurchaseItemMapperTest {

    private PurchaseItemMapper purchaseItemMapper;

    @BeforeEach
    public void setUp() {
        purchaseItemMapper = new PurchaseItemMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(purchaseItemMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(purchaseItemMapper.fromId(null)).isNull();
    }
}
