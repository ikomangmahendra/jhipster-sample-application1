package com.kebunkarta.myapp.repository;

import com.kebunkarta.myapp.domain.Gudang;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Gudang entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GudangRepository extends JpaRepository<Gudang, Long> {
}
