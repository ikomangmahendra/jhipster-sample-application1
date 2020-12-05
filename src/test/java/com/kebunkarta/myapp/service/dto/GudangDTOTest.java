package com.kebunkarta.myapp.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.kebunkarta.myapp.web.rest.TestUtil;

public class GudangDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GudangDTO.class);
        GudangDTO gudangDTO1 = new GudangDTO();
        gudangDTO1.setId(1L);
        GudangDTO gudangDTO2 = new GudangDTO();
        assertThat(gudangDTO1).isNotEqualTo(gudangDTO2);
        gudangDTO2.setId(gudangDTO1.getId());
        assertThat(gudangDTO1).isEqualTo(gudangDTO2);
        gudangDTO2.setId(2L);
        assertThat(gudangDTO1).isNotEqualTo(gudangDTO2);
        gudangDTO1.setId(null);
        assertThat(gudangDTO1).isNotEqualTo(gudangDTO2);
    }
}
