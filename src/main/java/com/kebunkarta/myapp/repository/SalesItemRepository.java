package com.kebunkarta.myapp.repository;

import com.kebunkarta.myapp.domain.SalesItem;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the SalesItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SalesItemRepository extends JpaRepository<SalesItem, Long> {
}
