package com.kebunkarta.myapp.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.kebunkarta.myapp.web.rest.TestUtil;

public class SalesItemDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SalesItemDTO.class);
        SalesItemDTO salesItemDTO1 = new SalesItemDTO();
        salesItemDTO1.setId(1L);
        SalesItemDTO salesItemDTO2 = new SalesItemDTO();
        assertThat(salesItemDTO1).isNotEqualTo(salesItemDTO2);
        salesItemDTO2.setId(salesItemDTO1.getId());
        assertThat(salesItemDTO1).isEqualTo(salesItemDTO2);
        salesItemDTO2.setId(2L);
        assertThat(salesItemDTO1).isNotEqualTo(salesItemDTO2);
        salesItemDTO1.setId(null);
        assertThat(salesItemDTO1).isNotEqualTo(salesItemDTO2);
    }
}
