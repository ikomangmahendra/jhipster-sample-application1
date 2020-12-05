package com.kebunkarta.myapp.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.kebunkarta.myapp.web.rest.TestUtil;

public class PurchaseItemDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PurchaseItemDTO.class);
        PurchaseItemDTO purchaseItemDTO1 = new PurchaseItemDTO();
        purchaseItemDTO1.setId(1L);
        PurchaseItemDTO purchaseItemDTO2 = new PurchaseItemDTO();
        assertThat(purchaseItemDTO1).isNotEqualTo(purchaseItemDTO2);
        purchaseItemDTO2.setId(purchaseItemDTO1.getId());
        assertThat(purchaseItemDTO1).isEqualTo(purchaseItemDTO2);
        purchaseItemDTO2.setId(2L);
        assertThat(purchaseItemDTO1).isNotEqualTo(purchaseItemDTO2);
        purchaseItemDTO1.setId(null);
        assertThat(purchaseItemDTO1).isNotEqualTo(purchaseItemDTO2);
    }
}
