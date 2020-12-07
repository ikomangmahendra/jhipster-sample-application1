package com.kebunkarta.myapp.web.rest;

import com.kebunkarta.myapp.JhipsterSampleApplication1App;
import com.kebunkarta.myapp.domain.SalesItem;
import com.kebunkarta.myapp.repository.SalesItemRepository;
import com.kebunkarta.myapp.service.SalesItemService;
import com.kebunkarta.myapp.service.dto.SalesItemDTO;
import com.kebunkarta.myapp.service.mapper.SalesItemMapper;

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
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link SalesItemResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplication1App.class)
@AutoConfigureMockMvc
@WithMockUser
public class SalesItemResourceIT {

    private static final Integer DEFAULT_TYPE_ID = 1;
    private static final Integer UPDATED_TYPE_ID = 2;

    private static final BigDecimal DEFAULT_QTY = new BigDecimal(1);
    private static final BigDecimal UPDATED_QTY = new BigDecimal(2);

    private static final BigDecimal DEFAULT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRICE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_DISC = new BigDecimal(1);
    private static final BigDecimal UPDATED_DISC = new BigDecimal(2);

    private static final Boolean DEFAULT_DISC_PERCENT_STATUS = false;
    private static final Boolean UPDATED_DISC_PERCENT_STATUS = true;

    private static final BigDecimal DEFAULT_TAX_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_TAX_AMOUNT = new BigDecimal(2);

    private static final String DEFAULT_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_NOTE = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_RECORD_STATUS_ID = 1;
    private static final Integer UPDATED_RECORD_STATUS_ID = 2;

    @Autowired
    private SalesItemRepository salesItemRepository;

    @Autowired
    private SalesItemMapper salesItemMapper;

    @Autowired
    private SalesItemService salesItemService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSalesItemMockMvc;

    private SalesItem salesItem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SalesItem createEntity(EntityManager em) {
        SalesItem salesItem = new SalesItem()
            .typeId(DEFAULT_TYPE_ID)
            .qty(DEFAULT_QTY)
            .price(DEFAULT_PRICE)
            .disc(DEFAULT_DISC)
            .discPercentStatus(DEFAULT_DISC_PERCENT_STATUS)
            .taxAmount(DEFAULT_TAX_AMOUNT)
            .note(DEFAULT_NOTE)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE)
            .recordStatusId(DEFAULT_RECORD_STATUS_ID);
        return salesItem;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SalesItem createUpdatedEntity(EntityManager em) {
        SalesItem salesItem = new SalesItem()
            .typeId(UPDATED_TYPE_ID)
            .qty(UPDATED_QTY)
            .price(UPDATED_PRICE)
            .disc(UPDATED_DISC)
            .discPercentStatus(UPDATED_DISC_PERCENT_STATUS)
            .taxAmount(UPDATED_TAX_AMOUNT)
            .note(UPDATED_NOTE)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE)
            .recordStatusId(UPDATED_RECORD_STATUS_ID);
        return salesItem;
    }

    @BeforeEach
    public void initTest() {
        salesItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createSalesItem() throws Exception {
        int databaseSizeBeforeCreate = salesItemRepository.findAll().size();
        // Create the SalesItem
        SalesItemDTO salesItemDTO = salesItemMapper.toDto(salesItem);
        restSalesItemMockMvc.perform(post("/api/sales-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(salesItemDTO)))
            .andExpect(status().isCreated());

        // Validate the SalesItem in the database
        List<SalesItem> salesItemList = salesItemRepository.findAll();
        assertThat(salesItemList).hasSize(databaseSizeBeforeCreate + 1);
        SalesItem testSalesItem = salesItemList.get(salesItemList.size() - 1);
        assertThat(testSalesItem.getTypeId()).isEqualTo(DEFAULT_TYPE_ID);
        assertThat(testSalesItem.getQty()).isEqualTo(DEFAULT_QTY);
        assertThat(testSalesItem.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testSalesItem.getDisc()).isEqualTo(DEFAULT_DISC);
        assertThat(testSalesItem.isDiscPercentStatus()).isEqualTo(DEFAULT_DISC_PERCENT_STATUS);
        assertThat(testSalesItem.getTaxAmount()).isEqualTo(DEFAULT_TAX_AMOUNT);
        assertThat(testSalesItem.getNote()).isEqualTo(DEFAULT_NOTE);
        assertThat(testSalesItem.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testSalesItem.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testSalesItem.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testSalesItem.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
        assertThat(testSalesItem.getRecordStatusId()).isEqualTo(DEFAULT_RECORD_STATUS_ID);
    }

    @Test
    @Transactional
    public void createSalesItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = salesItemRepository.findAll().size();

        // Create the SalesItem with an existing ID
        salesItem.setId(1L);
        SalesItemDTO salesItemDTO = salesItemMapper.toDto(salesItem);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSalesItemMockMvc.perform(post("/api/sales-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(salesItemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SalesItem in the database
        List<SalesItem> salesItemList = salesItemRepository.findAll();
        assertThat(salesItemList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTypeIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = salesItemRepository.findAll().size();
        // set the field null
        salesItem.setTypeId(null);

        // Create the SalesItem, which fails.
        SalesItemDTO salesItemDTO = salesItemMapper.toDto(salesItem);


        restSalesItemMockMvc.perform(post("/api/sales-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(salesItemDTO)))
            .andExpect(status().isBadRequest());

        List<SalesItem> salesItemList = salesItemRepository.findAll();
        assertThat(salesItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkQtyIsRequired() throws Exception {
        int databaseSizeBeforeTest = salesItemRepository.findAll().size();
        // set the field null
        salesItem.setQty(null);

        // Create the SalesItem, which fails.
        SalesItemDTO salesItemDTO = salesItemMapper.toDto(salesItem);


        restSalesItemMockMvc.perform(post("/api/sales-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(salesItemDTO)))
            .andExpect(status().isBadRequest());

        List<SalesItem> salesItemList = salesItemRepository.findAll();
        assertThat(salesItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = salesItemRepository.findAll().size();
        // set the field null
        salesItem.setPrice(null);

        // Create the SalesItem, which fails.
        SalesItemDTO salesItemDTO = salesItemMapper.toDto(salesItem);


        restSalesItemMockMvc.perform(post("/api/sales-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(salesItemDTO)))
            .andExpect(status().isBadRequest());

        List<SalesItem> salesItemList = salesItemRepository.findAll();
        assertThat(salesItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTaxAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = salesItemRepository.findAll().size();
        // set the field null
        salesItem.setTaxAmount(null);

        // Create the SalesItem, which fails.
        SalesItemDTO salesItemDTO = salesItemMapper.toDto(salesItem);


        restSalesItemMockMvc.perform(post("/api/sales-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(salesItemDTO)))
            .andExpect(status().isBadRequest());

        List<SalesItem> salesItemList = salesItemRepository.findAll();
        assertThat(salesItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRecordStatusIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = salesItemRepository.findAll().size();
        // set the field null
        salesItem.setRecordStatusId(null);

        // Create the SalesItem, which fails.
        SalesItemDTO salesItemDTO = salesItemMapper.toDto(salesItem);


        restSalesItemMockMvc.perform(post("/api/sales-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(salesItemDTO)))
            .andExpect(status().isBadRequest());

        List<SalesItem> salesItemList = salesItemRepository.findAll();
        assertThat(salesItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSalesItems() throws Exception {
        // Initialize the database
        salesItemRepository.saveAndFlush(salesItem);

        // Get all the salesItemList
        restSalesItemMockMvc.perform(get("/api/sales-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(salesItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].typeId").value(hasItem(DEFAULT_TYPE_ID)))
            .andExpect(jsonPath("$.[*].qty").value(hasItem(DEFAULT_QTY.intValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].disc").value(hasItem(DEFAULT_DISC.intValue())))
            .andExpect(jsonPath("$.[*].discPercentStatus").value(hasItem(DEFAULT_DISC_PERCENT_STATUS.booleanValue())))
            .andExpect(jsonPath("$.[*].taxAmount").value(hasItem(DEFAULT_TAX_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())))
            .andExpect(jsonPath("$.[*].recordStatusId").value(hasItem(DEFAULT_RECORD_STATUS_ID)));
    }
    
    @Test
    @Transactional
    public void getSalesItem() throws Exception {
        // Initialize the database
        salesItemRepository.saveAndFlush(salesItem);

        // Get the salesItem
        restSalesItemMockMvc.perform(get("/api/sales-items/{id}", salesItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(salesItem.getId().intValue()))
            .andExpect(jsonPath("$.typeId").value(DEFAULT_TYPE_ID))
            .andExpect(jsonPath("$.qty").value(DEFAULT_QTY.intValue()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.intValue()))
            .andExpect(jsonPath("$.disc").value(DEFAULT_DISC.intValue()))
            .andExpect(jsonPath("$.discPercentStatus").value(DEFAULT_DISC_PERCENT_STATUS.booleanValue()))
            .andExpect(jsonPath("$.taxAmount").value(DEFAULT_TAX_AMOUNT.intValue()))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()))
            .andExpect(jsonPath("$.recordStatusId").value(DEFAULT_RECORD_STATUS_ID));
    }
    @Test
    @Transactional
    public void getNonExistingSalesItem() throws Exception {
        // Get the salesItem
        restSalesItemMockMvc.perform(get("/api/sales-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSalesItem() throws Exception {
        // Initialize the database
        salesItemRepository.saveAndFlush(salesItem);

        int databaseSizeBeforeUpdate = salesItemRepository.findAll().size();

        // Update the salesItem
        SalesItem updatedSalesItem = salesItemRepository.findById(salesItem.getId()).get();
        // Disconnect from session so that the updates on updatedSalesItem are not directly saved in db
        em.detach(updatedSalesItem);
        updatedSalesItem
            .typeId(UPDATED_TYPE_ID)
            .qty(UPDATED_QTY)
            .price(UPDATED_PRICE)
            .disc(UPDATED_DISC)
            .discPercentStatus(UPDATED_DISC_PERCENT_STATUS)
            .taxAmount(UPDATED_TAX_AMOUNT)
            .note(UPDATED_NOTE)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE)
            .recordStatusId(UPDATED_RECORD_STATUS_ID);
        SalesItemDTO salesItemDTO = salesItemMapper.toDto(updatedSalesItem);

        restSalesItemMockMvc.perform(put("/api/sales-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(salesItemDTO)))
            .andExpect(status().isOk());

        // Validate the SalesItem in the database
        List<SalesItem> salesItemList = salesItemRepository.findAll();
        assertThat(salesItemList).hasSize(databaseSizeBeforeUpdate);
        SalesItem testSalesItem = salesItemList.get(salesItemList.size() - 1);
        assertThat(testSalesItem.getTypeId()).isEqualTo(UPDATED_TYPE_ID);
        assertThat(testSalesItem.getQty()).isEqualTo(UPDATED_QTY);
        assertThat(testSalesItem.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testSalesItem.getDisc()).isEqualTo(UPDATED_DISC);
        assertThat(testSalesItem.isDiscPercentStatus()).isEqualTo(UPDATED_DISC_PERCENT_STATUS);
        assertThat(testSalesItem.getTaxAmount()).isEqualTo(UPDATED_TAX_AMOUNT);
        assertThat(testSalesItem.getNote()).isEqualTo(UPDATED_NOTE);
        assertThat(testSalesItem.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testSalesItem.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testSalesItem.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testSalesItem.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
        assertThat(testSalesItem.getRecordStatusId()).isEqualTo(UPDATED_RECORD_STATUS_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingSalesItem() throws Exception {
        int databaseSizeBeforeUpdate = salesItemRepository.findAll().size();

        // Create the SalesItem
        SalesItemDTO salesItemDTO = salesItemMapper.toDto(salesItem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSalesItemMockMvc.perform(put("/api/sales-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(salesItemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SalesItem in the database
        List<SalesItem> salesItemList = salesItemRepository.findAll();
        assertThat(salesItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSalesItem() throws Exception {
        // Initialize the database
        salesItemRepository.saveAndFlush(salesItem);

        int databaseSizeBeforeDelete = salesItemRepository.findAll().size();

        // Delete the salesItem
        restSalesItemMockMvc.perform(delete("/api/sales-items/{id}", salesItem.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SalesItem> salesItemList = salesItemRepository.findAll();
        assertThat(salesItemList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
