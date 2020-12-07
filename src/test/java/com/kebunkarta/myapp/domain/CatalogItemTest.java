package com.kebunkarta.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.kebunkarta.myapp.web.rest.TestUtil;

public class CatalogItemTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CatalogItem.class);
        CatalogItem catalogItem1 = new CatalogItem();
        catalogItem1.setId(1L);
        CatalogItem catalogItem2 = new CatalogItem();
        catalogItem2.setId(catalogItem1.getId());
        assertThat(catalogItem1).isEqualTo(catalogItem2);
        catalogItem2.setId(2L);
        assertThat(catalogItem1).isNotEqualTo(catalogItem2);
        catalogItem1.setId(null);
        assertThat(catalogItem1).isNotEqualTo(catalogItem2);
    }
}
