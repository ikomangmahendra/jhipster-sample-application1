package com.kebunkarta.myapp.web.rest;

import com.kebunkarta.myapp.JhipsterSampleApplication1App;
import com.kebunkarta.myapp.domain.Barang;
import com.kebunkarta.myapp.repository.BarangRepository;
import com.kebunkarta.myapp.service.BarangService;
import com.kebunkarta.myapp.service.dto.BarangDTO;
import com.kebunkarta.myapp.service.mapper.BarangMapper;

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
 * Integration tests for the {@link BarangResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplication1App.class)
@AutoConfigureMockMvc
@WithMockUser
public class BarangResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private BarangRepository barangRepository;

    @Autowired
    private BarangMapper barangMapper;

    @Autowired
    private BarangService barangService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBarangMockMvc;

    private Barang barang;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Barang createEntity(EntityManager em) {
        Barang barang = new Barang()
            .name(DEFAULT_NAME);
        return barang;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Barang createUpdatedEntity(EntityManager em) {
        Barang barang = new Barang()
            .name(UPDATED_NAME);
        return barang;
    }

    @BeforeEach
    public void initTest() {
        barang = createEntity(em);
    }

    @Test
    @Transactional
    public void createBarang() throws Exception {
        int databaseSizeBeforeCreate = barangRepository.findAll().size();
        // Create the Barang
        BarangDTO barangDTO = barangMapper.toDto(barang);
        restBarangMockMvc.perform(post("/api/barangs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(barangDTO)))
            .andExpect(status().isCreated());

        // Validate the Barang in the database
        List<Barang> barangList = barangRepository.findAll();
        assertThat(barangList).hasSize(databaseSizeBeforeCreate + 1);
        Barang testBarang = barangList.get(barangList.size() - 1);
        assertThat(testBarang.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createBarangWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = barangRepository.findAll().size();

        // Create the Barang with an existing ID
        barang.setId(1L);
        BarangDTO barangDTO = barangMapper.toDto(barang);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBarangMockMvc.perform(post("/api/barangs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(barangDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Barang in the database
        List<Barang> barangList = barangRepository.findAll();
        assertThat(barangList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllBarangs() throws Exception {
        // Initialize the database
        barangRepository.saveAndFlush(barang);

        // Get all the barangList
        restBarangMockMvc.perform(get("/api/barangs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(barang.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getBarang() throws Exception {
        // Initialize the database
        barangRepository.saveAndFlush(barang);

        // Get the barang
        restBarangMockMvc.perform(get("/api/barangs/{id}", barang.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(barang.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }
    @Test
    @Transactional
    public void getNonExistingBarang() throws Exception {
        // Get the barang
        restBarangMockMvc.perform(get("/api/barangs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBarang() throws Exception {
        // Initialize the database
        barangRepository.saveAndFlush(barang);

        int databaseSizeBeforeUpdate = barangRepository.findAll().size();

        // Update the barang
        Barang updatedBarang = barangRepository.findById(barang.getId()).get();
        // Disconnect from session so that the updates on updatedBarang are not directly saved in db
        em.detach(updatedBarang);
        updatedBarang
            .name(UPDATED_NAME);
        BarangDTO barangDTO = barangMapper.toDto(updatedBarang);

        restBarangMockMvc.perform(put("/api/barangs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(barangDTO)))
            .andExpect(status().isOk());

        // Validate the Barang in the database
        List<Barang> barangList = barangRepository.findAll();
        assertThat(barangList).hasSize(databaseSizeBeforeUpdate);
        Barang testBarang = barangList.get(barangList.size() - 1);
        assertThat(testBarang.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingBarang() throws Exception {
        int databaseSizeBeforeUpdate = barangRepository.findAll().size();

        // Create the Barang
        BarangDTO barangDTO = barangMapper.toDto(barang);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBarangMockMvc.perform(put("/api/barangs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(barangDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Barang in the database
        List<Barang> barangList = barangRepository.findAll();
        assertThat(barangList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBarang() throws Exception {
        // Initialize the database
        barangRepository.saveAndFlush(barang);

        int databaseSizeBeforeDelete = barangRepository.findAll().size();

        // Delete the barang
        restBarangMockMvc.perform(delete("/api/barangs/{id}", barang.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Barang> barangList = barangRepository.findAll();
        assertThat(barangList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
