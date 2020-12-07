package com.kebunkarta.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.kebunkarta.myapp.web.rest.TestUtil;

public class GudangTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Gudang.class);
        Gudang gudang1 = new Gudang();
        gudang1.setId(1L);
        Gudang gudang2 = new Gudang();
        gudang2.setId(gudang1.getId());
        assertThat(gudang1).isEqualTo(gudang2);
        gudang2.setId(2L);
        assertThat(gudang1).isNotEqualTo(gudang2);
        gudang1.setId(null);
        assertThat(gudang1).isNotEqualTo(gudang2);
    }
}
