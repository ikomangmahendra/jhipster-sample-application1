package com.kebunkarta.myapp.service;

import com.kebunkarta.myapp.domain.Gudang;
import com.kebunkarta.myapp.repository.GudangRepository;
import com.kebunkarta.myapp.service.dto.GudangDTO;
import com.kebunkarta.myapp.service.mapper.GudangMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Gudang}.
 */
@Service
@Transactional
public class GudangService {

    private final Logger log = LoggerFactory.getLogger(GudangService.class);

    private final GudangRepository gudangRepository;

    private final GudangMapper gudangMapper;

    public GudangService(GudangRepository gudangRepository, GudangMapper gudangMapper) {
        this.gudangRepository = gudangRepository;
        this.gudangMapper = gudangMapper;
    }

    /**
     * Save a gudang.
     *
     * @param gudangDTO the entity to save.
     * @return the persisted entity.
     */
    public GudangDTO save(GudangDTO gudangDTO) {
        log.debug("Request to save Gudang : {}", gudangDTO);
        Gudang gudang = gudangMapper.toEntity(gudangDTO);
        gudang = gudangRepository.save(gudang);
        return gudangMapper.toDto(gudang);
    }

    /**
     * Get all the gudangs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<GudangDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Gudangs");
        return gudangRepository.findAll(pageable)
            .map(gudangMapper::toDto);
    }


    /**
     * Get one gudang by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<GudangDTO> findOne(Long id) {
        log.debug("Request to get Gudang : {}", id);
        return gudangRepository.findById(id)
            .map(gudangMapper::toDto);
    }

    /**
     * Delete the gudang by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Gudang : {}", id);
        gudangRepository.deleteById(id);
    }
}
