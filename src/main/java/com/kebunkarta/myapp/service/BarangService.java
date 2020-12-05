package com.kebunkarta.myapp.service;

import com.kebunkarta.myapp.domain.Barang;
import com.kebunkarta.myapp.repository.BarangRepository;
import com.kebunkarta.myapp.service.dto.BarangDTO;
import com.kebunkarta.myapp.service.mapper.BarangMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Barang}.
 */
@Service
@Transactional
public class BarangService {

    private final Logger log = LoggerFactory.getLogger(BarangService.class);

    private final BarangRepository barangRepository;

    private final BarangMapper barangMapper;

    public BarangService(BarangRepository barangRepository, BarangMapper barangMapper) {
        this.barangRepository = barangRepository;
        this.barangMapper = barangMapper;
    }

    /**
     * Save a barang.
     *
     * @param barangDTO the entity to save.
     * @return the persisted entity.
     */
    public BarangDTO save(BarangDTO barangDTO) {
        log.debug("Request to save Barang : {}", barangDTO);
        Barang barang = barangMapper.toEntity(barangDTO);
        barang = barangRepository.save(barang);
        return barangMapper.toDto(barang);
    }

    /**
     * Get all the barangs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<BarangDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Barangs");
        return barangRepository.findAll(pageable)
            .map(barangMapper::toDto);
    }


    /**
     * Get one barang by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BarangDTO> findOne(Long id) {
        log.debug("Request to get Barang : {}", id);
        return barangRepository.findById(id)
            .map(barangMapper::toDto);
    }

    /**
     * Delete the barang by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Barang : {}", id);
        barangRepository.deleteById(id);
    }
}
