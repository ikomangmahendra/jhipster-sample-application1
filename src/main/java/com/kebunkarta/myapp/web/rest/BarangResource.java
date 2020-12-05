package com.kebunkarta.myapp.web.rest;

import com.kebunkarta.myapp.service.BarangService;
import com.kebunkarta.myapp.web.rest.errors.BadRequestAlertException;
import com.kebunkarta.myapp.service.dto.BarangDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.kebunkarta.myapp.domain.Barang}.
 */
@RestController
@RequestMapping("/api")
public class BarangResource {

    private final Logger log = LoggerFactory.getLogger(BarangResource.class);

    private static final String ENTITY_NAME = "barang";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BarangService barangService;

    public BarangResource(BarangService barangService) {
        this.barangService = barangService;
    }

    /**
     * {@code POST  /barangs} : Create a new barang.
     *
     * @param barangDTO the barangDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new barangDTO, or with status {@code 400 (Bad Request)} if the barang has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/barangs")
    public ResponseEntity<BarangDTO> createBarang(@Valid @RequestBody BarangDTO barangDTO) throws URISyntaxException {
        log.debug("REST request to save Barang : {}", barangDTO);
        if (barangDTO.getId() != null) {
            throw new BadRequestAlertException("A new barang cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BarangDTO result = barangService.save(barangDTO);
        return ResponseEntity.created(new URI("/api/barangs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /barangs} : Updates an existing barang.
     *
     * @param barangDTO the barangDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated barangDTO,
     * or with status {@code 400 (Bad Request)} if the barangDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the barangDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/barangs")
    public ResponseEntity<BarangDTO> updateBarang(@Valid @RequestBody BarangDTO barangDTO) throws URISyntaxException {
        log.debug("REST request to update Barang : {}", barangDTO);
        if (barangDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BarangDTO result = barangService.save(barangDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, barangDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /barangs} : get all the barangs.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of barangs in body.
     */
    @GetMapping("/barangs")
    public ResponseEntity<List<BarangDTO>> getAllBarangs(Pageable pageable) {
        log.debug("REST request to get a page of Barangs");
        Page<BarangDTO> page = barangService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /barangs/:id} : get the "id" barang.
     *
     * @param id the id of the barangDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the barangDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/barangs/{id}")
    public ResponseEntity<BarangDTO> getBarang(@PathVariable Long id) {
        log.debug("REST request to get Barang : {}", id);
        Optional<BarangDTO> barangDTO = barangService.findOne(id);
        return ResponseUtil.wrapOrNotFound(barangDTO);
    }

    /**
     * {@code DELETE  /barangs/:id} : delete the "id" barang.
     *
     * @param id the id of the barangDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/barangs/{id}")
    public ResponseEntity<Void> deleteBarang(@PathVariable Long id) {
        log.debug("REST request to delete Barang : {}", id);
        barangService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
