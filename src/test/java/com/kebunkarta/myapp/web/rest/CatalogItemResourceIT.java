package com.kebunkarta.myapp.web.rest;

import com.kebunkarta.myapp.JhipsterSampleApplication1App;
import com.kebunkarta.myapp.domain.CatalogItem;
import com.kebunkarta.myapp.repository.CatalogItemRepository;
import com.kebunkarta.myapp.service.CatalogItemService;
import com.kebunkarta.myapp.service.dto.CatalogItemDTO;
import com.kebunkarta.myapp.service.mapper.CatalogItemMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CatalogItemResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplication1App.class)
@AutoConfigureMockMvc
@WithMockUser
public class CatalogItemResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private CatalogItemRepository catalogItemRepository;

    @Autowired
    private CatalogItemMapper catalogItemMapper;

    @Autowired
    private CatalogItemService catalogItemService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCatalogItemMockMvc;

    private CatalogItem catalogItem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CatalogItem createEntity(EntityManager em) {
        CatalogItem catalogItem = new CatalogItem()
            .name(DEFAULT_NAME);
        return catalogItem;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CatalogItem createUpdatedEntity(EntityManager em) {
        CatalogItem catalogItem = new CatalogItem()
            .name(UPDATED_NAME);
        return catalogItem;
    }

    @BeforeEach
    public void initTest() {
        catalogItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createCatalogItem() throws Exception {
        int databaseSizeBeforeCreate = catalogItemRepository.findAll().size();
        // Create the CatalogItem
        CatalogItemDTO catalogItemDTO = catalogItemMapper.toDto(catalogItem);
        restCatalogItemMockMvc.perform(post("/api/catalog-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(catalogItemDTO)))
            .andExpect(status().isCreated());

        // Validate the CatalogItem in the database
        List<CatalogItem> catalogItemList = catalogItemRepository.findAll();
        assertThat(catalogItemList).hasSize(databaseSizeBeforeCreate + 1);
        CatalogItem testCatalogItem = catalogItemList.get(catalogItemList.size() - 1);
        assertThat(testCatalogItem.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createCatalogItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = catalogItemRepository.findAll().size();

        // Create the CatalogItem with an existing ID
        catalogItem.setId(1L);
        CatalogItemDTO catalogItemDTO = catalogItemMapper.toDto(catalogItem);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCatalogItemMockMvc.perform(post("/api/catalog-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(catalogItemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CatalogItem in the database
        List<CatalogItem> catalogItemList = catalogItemRepository.findAll();
        assertThat(catalogItemList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCatalogItems() throws Exception {
        // Initialize the database
        catalogItemRepository.saveAndFlush(catalogItem);

        // Get all the catalogItemList
        restCatalogItemMockMvc.perform(get("/api/catalog-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(catalogItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getCatalogItem() throws Exception {
        // Initialize the database
        catalogItemRepository.saveAndFlush(catalogItem);

        // Get the catalogItem
        restCatalogItemMockMvc.perform(get("/api/catalog-items/{id}", catalogItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(catalogItem.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }
    @Test
    @Transactional
    public void getNonExistingCatalogItem() throws Exception {
        // Get the catalogItem
        restCatalogItemMockMvc.perform(get("/api/catalog-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCatalogItem() throws Exception {
        // Initialize the database
        catalogItemRepository.saveAndFlush(catalogItem);

        int databaseSizeBeforeUpdate = catalogItemRepository.findAll().size();

        // Update the catalogItem
        CatalogItem updatedCatalogItem = catalogItemRepository.findById(catalogItem.getId()).get();
        // Disconnect from session so that the updates on updatedCatalogItem are not directly saved in db
        em.detach(updatedCatalogItem);
        updatedCatalogItem
            .name(UPDATED_NAME);
        CatalogItemDTO catalogItemDTO = catalogItemMapper.toDto(updatedCatalogItem);

        restCatalogItemMockMvc.perform(put("/api/catalog-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(catalogItemDTO)))
            .andExpect(status().isOk());

        // Validate the CatalogItem in the database
        List<CatalogItem> catalogItemList = catalogItemRepository.findAll();
        assertThat(catalogItemList).hasSize(databaseSizeBeforeUpdate);
        CatalogItem testCatalogItem = catalogItemList.get(catalogItemList.size() - 1);
        assertThat(testCatalogItem.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingCatalogItem() throws Exception {
        int databaseSizeBeforeUpdate = catalogItemRepository.findAll().size();

        // Create the CatalogItem
        CatalogItemDTO catalogItemDTO = catalogItemMapper.toDto(catalogItem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCatalogItemMockMvc.perform(put("/api/catalog-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(catalogItemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CatalogItem in the database
        List<CatalogItem> catalogItemList = catalogItemRepository.findAll();
        assertThat(catalogItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCatalogItem() throws Exception {
        // Initialize the database
        catalogItemRepository.saveAndFlush(catalogItem);

        int databaseSizeBeforeDelete = catalogItemRepository.findAll().size();

        // Delete the catalogItem
        restCatalogItemMockMvc.perform(delete("/api/catalog-items/{id}", catalogItem.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CatalogItem> catalogItemList = catalogItemRepository.findAll();
        assertThat(catalogItemList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
