package com.kebunkarta.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.kebunkarta.myapp.web.rest.TestUtil;

public class SalesItemTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SalesItem.class);
        SalesItem salesItem1 = new SalesItem();
        salesItem1.setId(1L);
        SalesItem salesItem2 = new SalesItem();
        salesItem2.setId(salesItem1.getId());
        assertThat(salesItem1).isEqualTo(salesItem2);
        salesItem2.setId(2L);
        assertThat(salesItem1).isNotEqualTo(salesItem2);
        salesItem1.setId(null);
        assertThat(salesItem1).isNotEqualTo(salesItem2);
    }
}
