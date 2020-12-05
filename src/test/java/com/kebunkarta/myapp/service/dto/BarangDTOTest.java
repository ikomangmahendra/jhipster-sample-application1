package com.kebunkarta.myapp.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.kebunkarta.myapp.web.rest.TestUtil;

public class BarangDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BarangDTO.class);
        BarangDTO barangDTO1 = new BarangDTO();
        barangDTO1.setId(1L);
        BarangDTO barangDTO2 = new BarangDTO();
        assertThat(barangDTO1).isNotEqualTo(barangDTO2);
        barangDTO2.setId(barangDTO1.getId());
        assertThat(barangDTO1).isEqualTo(barangDTO2);
        barangDTO2.setId(2L);
        assertThat(barangDTO1).isNotEqualTo(barangDTO2);
        barangDTO1.setId(null);
        assertThat(barangDTO1).isNotEqualTo(barangDTO2);
    }
}
