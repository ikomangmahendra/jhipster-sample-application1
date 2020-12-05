package com.kebunkarta.myapp.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.kebunkarta.myapp.domain.Barang} entity.
 */
public class BarangDTO implements Serializable {
    
    private Long id;

    @Size(max = 50)
    private String name;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BarangDTO)) {
            return false;
        }

        return id != null && id.equals(((BarangDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BarangDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
