package com.kebunkarta.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.kebunkarta.myapp.web.rest.TestUtil;

public class BarangTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Barang.class);
        Barang barang1 = new Barang();
        barang1.setId(1L);
        Barang barang2 = new Barang();
        barang2.setId(barang1.getId());
        assertThat(barang1).isEqualTo(barang2);
        barang2.setId(2L);
        assertThat(barang1).isNotEqualTo(barang2);
        barang1.setId(null);
        assertThat(barang1).isNotEqualTo(barang2);
    }
}
