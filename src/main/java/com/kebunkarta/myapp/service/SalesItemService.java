package com.kebunkarta.myapp.service;

import com.kebunkarta.myapp.domain.SalesItem;
import com.kebunkarta.myapp.repository.SalesItemRepository;
import com.kebunkarta.myapp.service.dto.SalesItemDTO;
import com.kebunkarta.myapp.service.mapper.SalesItemMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link SalesItem}.
 */
@Service
@Transactional
public class SalesItemService {

    private final Logger log = LoggerFactory.getLogger(SalesItemService.class);

    private final SalesItemRepository salesItemRepository;

    private final SalesItemMapper salesItemMapper;

    public SalesItemService(SalesItemRepository salesItemRepository, SalesItemMapper salesItemMapper) {
        this.salesItemRepository = salesItemRepository;
        this.salesItemMapper = salesItemMapper;
    }

    /**
     * Save a salesItem.
     *
     * @param salesItemDTO the entity to save.
     * @return the persisted entity.
     */
    public SalesItemDTO save(SalesItemDTO salesItemDTO) {
        log.debug("Request to save SalesItem : {}", salesItemDTO);
        SalesItem salesItem = salesItemMapper.toEntity(salesItemDTO);
        salesItem = salesItemRepository.save(salesItem);
        return salesItemMapper.toDto(salesItem);
    }

    /**
     * Get all the salesItems.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SalesItemDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SalesItems");
        return salesItemRepository.findAll(pageable)
            .map(salesItemMapper::toDto);
    }


    /**
     * Get one salesItem by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SalesItemDTO> findOne(Long id) {
        log.debug("Request to get SalesItem : {}", id);
        return salesItemRepository.findById(id)
            .map(salesItemMapper::toDto);
    }

    /**
     * Delete the salesItem by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete SalesItem : {}", id);
        salesItemRepository.deleteById(id);
    }
}
