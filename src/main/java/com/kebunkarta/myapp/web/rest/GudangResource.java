package com.kebunkarta.myapp.web.rest;

import com.kebunkarta.myapp.service.GudangService;
import com.kebunkarta.myapp.web.rest.errors.BadRequestAlertException;
import com.kebunkarta.myapp.service.dto.GudangDTO;

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
 * REST controller for managing {@link com.kebunkarta.myapp.domain.Gudang}.
 */
@RestController
@RequestMapping("/api")
public class GudangResource {

    private final Logger log = LoggerFactory.getLogger(GudangResource.class);

    private static final String ENTITY_NAME = "gudang";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GudangService gudangService;

    public GudangResource(GudangService gudangService) {
        this.gudangService = gudangService;
    }

    /**
     * {@code POST  /gudangs} : Create a new gudang.
     *
     * @param gudangDTO the gudangDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new gudangDTO, or with status {@code 400 (Bad Request)} if the gudang has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/gudangs")
    public ResponseEntity<GudangDTO> createGudang(@Valid @RequestBody GudangDTO gudangDTO) throws URISyntaxException {
        log.debug("REST request to save Gudang : {}", gudangDTO);
        if (gudangDTO.getId() != null) {
            throw new BadRequestAlertException("A new gudang cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GudangDTO result = gudangService.save(gudangDTO);
        return ResponseEntity.created(new URI("/api/gudangs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /gudangs} : Updates an existing gudang.
     *
     * @param gudangDTO the gudangDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gudangDTO,
     * or with status {@code 400 (Bad Request)} if the gudangDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the gudangDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/gudangs")
    public ResponseEntity<GudangDTO> updateGudang(@Valid @RequestBody GudangDTO gudangDTO) throws URISyntaxException {
        log.debug("REST request to update Gudang : {}", gudangDTO);
        if (gudangDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GudangDTO result = gudangService.save(gudangDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, gudangDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /gudangs} : get all the gudangs.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of gudangs in body.
     */
    @GetMapping("/gudangs")
    public ResponseEntity<List<GudangDTO>> getAllGudangs(Pageable pageable) {
        log.debug("REST request to get a page of Gudangs");
        Page<GudangDTO> page = gudangService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /gudangs/:id} : get the "id" gudang.
     *
     * @param id the id of the gudangDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the gudangDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/gudangs/{id}")
    public ResponseEntity<GudangDTO> getGudang(@PathVariable Long id) {
        log.debug("REST request to get Gudang : {}", id);
        Optional<GudangDTO> gudangDTO = gudangService.findOne(id);
        return ResponseUtil.wrapOrNotFound(gudangDTO);
    }

    /**
     * {@code DELETE  /gudangs/:id} : delete the "id" gudang.
     *
     * @param id the id of the gudangDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/gudangs/{id}")
    public ResponseEntity<Void> deleteGudang(@PathVariable Long id) {
        log.debug("REST request to delete Gudang : {}", id);
        gudangService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
