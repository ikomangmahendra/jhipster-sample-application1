package com.kebunkarta.myapp.web.rest;

import com.kebunkarta.myapp.JhipsterSampleApplication1App;
import com.kebunkarta.myapp.domain.Gudang;
import com.kebunkarta.myapp.repository.GudangRepository;
import com.kebunkarta.myapp.service.GudangService;
import com.kebunkarta.myapp.service.dto.GudangDTO;
import com.kebunkarta.myapp.service.mapper.GudangMapper;

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
 * Integration tests for the {@link GudangResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplication1App.class)
@AutoConfigureMockMvc
@WithMockUser
public class GudangResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private GudangRepository gudangRepository;

    @Autowired
    private GudangMapper gudangMapper;

    @Autowired
    private GudangService gudangService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGudangMockMvc;

    private Gudang gudang;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Gudang createEntity(EntityManager em) {
        Gudang gudang = new Gudang()
            .name(DEFAULT_NAME);
        return gudang;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Gudang createUpdatedEntity(EntityManager em) {
        Gudang gudang = new Gudang()
            .name(UPDATED_NAME);
        return gudang;
    }

    @BeforeEach
    public void initTest() {
        gudang = createEntity(em);
    }

    @Test
    @Transactional
    public void createGudang() throws Exception {
        int databaseSizeBeforeCreate = gudangRepository.findAll().size();
        // Create the Gudang
        GudangDTO gudangDTO = gudangMapper.toDto(gudang);
        restGudangMockMvc.perform(post("/api/gudangs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gudangDTO)))
            .andExpect(status().isCreated());

        // Validate the Gudang in the database
        List<Gudang> gudangList = gudangRepository.findAll();
        assertThat(gudangList).hasSize(databaseSizeBeforeCreate + 1);
        Gudang testGudang = gudangList.get(gudangList.size() - 1);
        assertThat(testGudang.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createGudangWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = gudangRepository.findAll().size();

        // Create the Gudang with an existing ID
        gudang.setId(1L);
        GudangDTO gudangDTO = gudangMapper.toDto(gudang);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGudangMockMvc.perform(post("/api/gudangs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gudangDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Gudang in the database
        List<Gudang> gudangList = gudangRepository.findAll();
        assertThat(gudangList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllGudangs() throws Exception {
        // Initialize the database
        gudangRepository.saveAndFlush(gudang);

        // Get all the gudangList
        restGudangMockMvc.perform(get("/api/gudangs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gudang.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getGudang() throws Exception {
        // Initialize the database
        gudangRepository.saveAndFlush(gudang);

        // Get the gudang
        restGudangMockMvc.perform(get("/api/gudangs/{id}", gudang.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(gudang.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }
    @Test
    @Transactional
    public void getNonExistingGudang() throws Exception {
        // Get the gudang
        restGudangMockMvc.perform(get("/api/gudangs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGudang() throws Exception {
        // Initialize the database
        gudangRepository.saveAndFlush(gudang);

        int databaseSizeBeforeUpdate = gudangRepository.findAll().size();

        // Update the gudang
        Gudang updatedGudang = gudangRepository.findById(gudang.getId()).get();
        // Disconnect from session so that the updates on updatedGudang are not directly saved in db
        em.detach(updatedGudang);
        updatedGudang
            .name(UPDATED_NAME);
        GudangDTO gudangDTO = gudangMapper.toDto(updatedGudang);

        restGudangMockMvc.perform(put("/api/gudangs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gudangDTO)))
            .andExpect(status().isOk());

        // Validate the Gudang in the database
        List<Gudang> gudangList = gudangRepository.findAll();
        assertThat(gudangList).hasSize(databaseSizeBeforeUpdate);
        Gudang testGudang = gudangList.get(gudangList.size() - 1);
        assertThat(testGudang.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingGudang() throws Exception {
        int databaseSizeBeforeUpdate = gudangRepository.findAll().size();

        // Create the Gudang
        GudangDTO gudangDTO = gudangMapper.toDto(gudang);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGudangMockMvc.perform(put("/api/gudangs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gudangDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Gudang in the database
        List<Gudang> gudangList = gudangRepository.findAll();
        assertThat(gudangList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGudang() throws Exception {
        // Initialize the database
        gudangRepository.saveAndFlush(gudang);

        int databaseSizeBeforeDelete = gudangRepository.findAll().size();

        // Delete the gudang
        restGudangMockMvc.perform(delete("/api/gudangs/{id}", gudang.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Gudang> gudangList = gudangRepository.findAll();
        assertThat(gudangList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
