package com.kebunkarta.myapp.repository;

import com.kebunkarta.myapp.domain.Barang;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Barang entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BarangRepository extends JpaRepository<Barang, Long> {
}
