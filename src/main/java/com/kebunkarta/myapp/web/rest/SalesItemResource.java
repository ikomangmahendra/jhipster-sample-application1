package com.kebunkarta.myapp.web.rest;

import com.kebunkarta.myapp.service.SalesItemService;
import com.kebunkarta.myapp.web.rest.errors.BadRequestAlertException;
import com.kebunkarta.myapp.service.dto.SalesItemDTO;

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
 * REST controller for managing {@link com.kebunkarta.myapp.domain.SalesItem}.
 */
@RestController
@RequestMapping("/api")
public class SalesItemResource {

    private final Logger log = LoggerFactory.getLogger(SalesItemResource.class);

    private static final String ENTITY_NAME = "salesItem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SalesItemService salesItemService;

    public SalesItemResource(SalesItemService salesItemService) {
        this.salesItemService = salesItemService;
    }

    /**
     * {@code POST  /sales-items} : Create a new salesItem.
     *
     * @param salesItemDTO the salesItemDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new salesItemDTO, or with status {@code 400 (Bad Request)} if the salesItem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sales-items")
    public ResponseEntity<SalesItemDTO> createSalesItem(@Valid @RequestBody SalesItemDTO salesItemDTO) throws URISyntaxException {
        log.debug("REST request to save SalesItem : {}", salesItemDTO);
        if (salesItemDTO.getId() != null) {
            throw new BadRequestAlertException("A new salesItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SalesItemDTO result = salesItemService.save(salesItemDTO);
        return ResponseEntity.created(new URI("/api/sales-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sales-items} : Updates an existing salesItem.
     *
     * @param salesItemDTO the salesItemDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated salesItemDTO,
     * or with status {@code 400 (Bad Request)} if the salesItemDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the salesItemDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sales-items")
    public ResponseEntity<SalesItemDTO> updateSalesItem(@Valid @RequestBody SalesItemDTO salesItemDTO) throws URISyntaxException {
        log.debug("REST request to update SalesItem : {}", salesItemDTO);
        if (salesItemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SalesItemDTO result = salesItemService.save(salesItemDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, salesItemDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /sales-items} : get all the salesItems.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of salesItems in body.
     */
    @GetMapping("/sales-items")
    public ResponseEntity<List<SalesItemDTO>> getAllSalesItems(Pageable pageable) {
        log.debug("REST request to get a page of SalesItems");
        Page<SalesItemDTO> page = salesItemService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /sales-items/:id} : get the "id" salesItem.
     *
     * @param id the id of the salesItemDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the salesItemDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sales-items/{id}")
    public ResponseEntity<SalesItemDTO> getSalesItem(@PathVariable Long id) {
        log.debug("REST request to get SalesItem : {}", id);
        Optional<SalesItemDTO> salesItemDTO = salesItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(salesItemDTO);
    }

    /**
     * {@code DELETE  /sales-items/:id} : delete the "id" salesItem.
     *
     * @param id the id of the salesItemDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sales-items/{id}")
    public ResponseEntity<Void> deleteSalesItem(@PathVariable Long id) {
        log.debug("REST request to delete SalesItem : {}", id);
        salesItemService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
