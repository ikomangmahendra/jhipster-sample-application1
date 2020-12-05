package com.kebunkarta.myapp.repository;

import com.kebunkarta.myapp.domain.PurchaseItem;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PurchaseItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PurchaseItemRepository extends JpaRepository<PurchaseItem, Long> {
}
