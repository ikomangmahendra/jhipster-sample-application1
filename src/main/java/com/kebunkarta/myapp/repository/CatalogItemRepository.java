package com.kebunkarta.myapp.repository;

import com.kebunkarta.myapp.domain.CatalogItem;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CatalogItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CatalogItemRepository extends JpaRepository<CatalogItem, Long> {
}