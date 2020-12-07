package com.kebunkarta.myapp.service;

import com.kebunkarta.myapp.domain.PurchaseItem;
import com.kebunkarta.myapp.repository.PurchaseItemRepository;
import com.kebunkarta.myapp.service.dto.PurchaseItemDTO;
import com.kebunkarta.myapp.service.mapper.PurchaseItemMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link PurchaseItem}.
 */
@Service
@Transactional
public class PurchaseItemService {

    private final Logger log = LoggerFactory.getLogger(PurchaseItemService.class);

    private final PurchaseItemRepository purchaseItemRepository;

    private final PurchaseItemMapper purchaseItemMapper;

    public PurchaseItemService(PurchaseItemRepository purchaseItemRepository, PurchaseItemMapper purchaseItemMapper) {
        this.purchaseItemRepository = purchaseItemRepository;
        this.purchaseItemMapper = purchaseItemMapper;
    }

    /**
     * Save a purchaseItem.
     *
     * @param purchaseItemDTO the entity to save.
     * @return the persisted entity.
     */
    public PurchaseItemDTO save(PurchaseItemDTO purchaseItemDTO) {
        log.debug("Request to save PurchaseItem : {}", purchaseItemDTO);
        PurchaseItem purchaseItem = purchaseItemMapper.toEntity(purchaseItemDTO);
        purchaseItem = purchaseItemRepository.save(purchaseItem);
        return purchaseItemMapper.toDto(purchaseItem);
    }

    /**
     * Get all the purchaseItems.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PurchaseItemDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PurchaseItems");
        return purchaseItemRepository.findAll(pageable)
            .map(purchaseItemMapper::toDto);
    }


    /**
     * Get one purchaseItem by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PurchaseItemDTO> findOne(Long id) {
        log.debug("Request to get PurchaseItem : {}", id);
        return purchaseItemRepository.findById(id)
            .map(purchaseItemMapper::toDto);
    }

    /**
     * Delete the purchaseItem by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PurchaseItem : {}", id);
        purchaseItemRepository.deleteById(id);
    }
}
