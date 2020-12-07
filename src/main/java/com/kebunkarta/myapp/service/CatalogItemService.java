package com.kebunkarta.myapp.service;

import com.kebunkarta.myapp.domain.CatalogItem;
import com.kebunkarta.myapp.repository.CatalogItemRepository;
import com.kebunkarta.myapp.service.dto.CatalogItemDTO;
import com.kebunkarta.myapp.service.mapper.CatalogItemMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CatalogItem}.
 */
@Service
@Transactional
public class CatalogItemService {

    private final Logger log = LoggerFactory.getLogger(CatalogItemService.class);

    private final CatalogItemRepository catalogItemRepository;

    private final CatalogItemMapper catalogItemMapper;

    public CatalogItemService(CatalogItemRepository catalogItemRepository, CatalogItemMapper catalogItemMapper) {
        this.catalogItemRepository = catalogItemRepository;
        this.catalogItemMapper = catalogItemMapper;
    }

    /**
     * Save a catalogItem.
     *
     * @param catalogItemDTO the entity to save.
     * @return the persisted entity.
     */
    public CatalogItemDTO save(CatalogItemDTO catalogItemDTO) {
        log.debug("Request to save CatalogItem : {}", catalogItemDTO);
        CatalogItem catalogItem = catalogItemMapper.toEntity(catalogItemDTO);
        catalogItem = catalogItemRepository.save(catalogItem);
        return catalogItemMapper.toDto(catalogItem);
    }

    /**
     * Get all the catalogItems.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CatalogItemDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CatalogItems");
        return catalogItemRepository.findAll(pageable)
            .map(catalogItemMapper::toDto);
    }


    /**
     * Get one catalogItem by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CatalogItemDTO> findOne(Long id) {
        log.debug("Request to get CatalogItem : {}", id);
        return catalogItemRepository.findById(id)
            .map(catalogItemMapper::toDto);
    }

    /**
     * Delete the catalogItem by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CatalogItem : {}", id);
        catalogItemRepository.deleteById(id);
    }
}
