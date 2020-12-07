package com.kebunkarta.myapp.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.kebunkarta.myapp.web.rest.TestUtil;

public class CatalogItemDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CatalogItemDTO.class);
        CatalogItemDTO catalogItemDTO1 = new CatalogItemDTO();
        catalogItemDTO1.setId(1L);
        CatalogItemDTO catalogItemDTO2 = new CatalogItemDTO();
        assertThat(catalogItemDTO1).isNotEqualTo(catalogItemDTO2);
        catalogItemDTO2.setId(catalogItemDTO1.getId());
        assertThat(catalogItemDTO1).isEqualTo(catalogItemDTO2);
        catalogItemDTO2.setId(2L);
        assertThat(catalogItemDTO1).isNotEqualTo(catalogItemDTO2);
        catalogItemDTO1.setId(null);
        assertThat(catalogItemDTO1).isNotEqualTo(catalogItemDTO2);
    }
}
