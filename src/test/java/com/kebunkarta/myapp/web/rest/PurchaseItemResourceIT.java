package com.kebunkarta.myapp.web.rest;

import com.kebunkarta.myapp.JhipsterSampleApplication1App;
import com.kebunkarta.myapp.domain.PurchaseItem;
import com.kebunkarta.myapp.repository.PurchaseItemRepository;
import com.kebunkarta.myapp.service.PurchaseItemService;
import com.kebunkarta.myapp.service.dto.PurchaseItemDTO;
import com.kebunkarta.myapp.service.mapper.PurchaseItemMapper;

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
 * Integration tests for the {@link PurchaseItemResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplication1App.class)
@AutoConfigureMockMvc
@WithMockUser
public class PurchaseItemResourceIT {

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
    private PurchaseItemRepository purchaseItemRepository;

    @Autowired
    private PurchaseItemMapper purchaseItemMapper;

    @Autowired
    private PurchaseItemService purchaseItemService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPurchaseItemMockMvc;

    private PurchaseItem purchaseItem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PurchaseItem createEntity(EntityManager em) {
        PurchaseItem purchaseItem = new PurchaseItem()
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
        return purchaseItem;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PurchaseItem createUpdatedEntity(EntityManager em) {
        PurchaseItem purchaseItem = new PurchaseItem()
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
        return purchaseItem;
    }

    @BeforeEach
    public void initTest() {
        purchaseItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createPurchaseItem() throws Exception {
        int databaseSizeBeforeCreate = purchaseItemRepository.findAll().size();
        // Create the PurchaseItem
        PurchaseItemDTO purchaseItemDTO = purchaseItemMapper.toDto(purchaseItem);
        restPurchaseItemMockMvc.perform(post("/api/purchase-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(purchaseItemDTO)))
            .andExpect(status().isCreated());

        // Validate the PurchaseItem in the database
        List<PurchaseItem> purchaseItemList = purchaseItemRepository.findAll();
        assertThat(purchaseItemList).hasSize(databaseSizeBeforeCreate + 1);
        PurchaseItem testPurchaseItem = purchaseItemList.get(purchaseItemList.size() - 1);
        assertThat(testPurchaseItem.getTypeId()).isEqualTo(DEFAULT_TYPE_ID);
        assertThat(testPurchaseItem.getQty()).isEqualTo(DEFAULT_QTY);
        assertThat(testPurchaseItem.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testPurchaseItem.getDisc()).isEqualTo(DEFAULT_DISC);
        assertThat(testPurchaseItem.isDiscPercentStatus()).isEqualTo(DEFAULT_DISC_PERCENT_STATUS);
        assertThat(testPurchaseItem.getTaxAmount()).isEqualTo(DEFAULT_TAX_AMOUNT);
        assertThat(testPurchaseItem.getNote()).isEqualTo(DEFAULT_NOTE);
        assertThat(testPurchaseItem.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testPurchaseItem.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testPurchaseItem.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testPurchaseItem.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
        assertThat(testPurchaseItem.getRecordStatusId()).isEqualTo(DEFAULT_RECORD_STATUS_ID);
    }

    @Test
    @Transactional
    public void createPurchaseItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = purchaseItemRepository.findAll().size();

        // Create the PurchaseItem with an existing ID
        purchaseItem.setId(1L);
        PurchaseItemDTO purchaseItemDTO = purchaseItemMapper.toDto(purchaseItem);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPurchaseItemMockMvc.perform(post("/api/purchase-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(purchaseItemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PurchaseItem in the database
        List<PurchaseItem> purchaseItemList = purchaseItemRepository.findAll();
        assertThat(purchaseItemList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTypeIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = purchaseItemRepository.findAll().size();
        // set the field null
        purchaseItem.setTypeId(null);

        // Create the PurchaseItem, which fails.
        PurchaseItemDTO purchaseItemDTO = purchaseItemMapper.toDto(purchaseItem);


        restPurchaseItemMockMvc.perform(post("/api/purchase-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(purchaseItemDTO)))
            .andExpect(status().isBadRequest());

        List<PurchaseItem> purchaseItemList = purchaseItemRepository.findAll();
        assertThat(purchaseItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkQtyIsRequired() throws Exception {
        int databaseSizeBeforeTest = purchaseItemRepository.findAll().size();
        // set the field null
        purchaseItem.setQty(null);

        // Create the PurchaseItem, which fails.
        PurchaseItemDTO purchaseItemDTO = purchaseItemMapper.toDto(purchaseItem);


        restPurchaseItemMockMvc.perform(post("/api/purchase-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(purchaseItemDTO)))
            .andExpect(status().isBadRequest());

        List<PurchaseItem> purchaseItemList = purchaseItemRepository.findAll();
        assertThat(purchaseItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = purchaseItemRepository.findAll().size();
        // set the field null
        purchaseItem.setPrice(null);

        // Create the PurchaseItem, which fails.
        PurchaseItemDTO purchaseItemDTO = purchaseItemMapper.toDto(purchaseItem);


        restPurchaseItemMockMvc.perform(post("/api/purchase-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(purchaseItemDTO)))
            .andExpect(status().isBadRequest());

        List<PurchaseItem> purchaseItemList = purchaseItemRepository.findAll();
        assertThat(purchaseItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTaxAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = purchaseItemRepository.findAll().size();
        // set the field null
        purchaseItem.setTaxAmount(null);

        // Create the PurchaseItem, which fails.
        PurchaseItemDTO purchaseItemDTO = purchaseItemMapper.toDto(purchaseItem);


        restPurchaseItemMockMvc.perform(post("/api/purchase-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(purchaseItemDTO)))
            .andExpect(status().isBadRequest());

        List<PurchaseItem> purchaseItemList = purchaseItemRepository.findAll();
        assertThat(purchaseItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRecordStatusIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = purchaseItemRepository.findAll().size();
        // set the field null
        purchaseItem.setRecordStatusId(null);

        // Create the PurchaseItem, which fails.
        PurchaseItemDTO purchaseItemDTO = purchaseItemMapper.toDto(purchaseItem);


        restPurchaseItemMockMvc.perform(post("/api/purchase-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(purchaseItemDTO)))
            .andExpect(status().isBadRequest());

        List<PurchaseItem> purchaseItemList = purchaseItemRepository.findAll();
        assertThat(purchaseItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPurchaseItems() throws Exception {
        // Initialize the database
        purchaseItemRepository.saveAndFlush(purchaseItem);

        // Get all the purchaseItemList
        restPurchaseItemMockMvc.perform(get("/api/purchase-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(purchaseItem.getId().intValue())))
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
    public void getPurchaseItem() throws Exception {
        // Initialize the database
        purchaseItemRepository.saveAndFlush(purchaseItem);

        // Get the purchaseItem
        restPurchaseItemMockMvc.perform(get("/api/purchase-items/{id}", purchaseItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(purchaseItem.getId().intValue()))
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
    public void getNonExistingPurchaseItem() throws Exception {
        // Get the purchaseItem
        restPurchaseItemMockMvc.perform(get("/api/purchase-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePurchaseItem() throws Exception {
        // Initialize the database
        purchaseItemRepository.saveAndFlush(purchaseItem);

        int databaseSizeBeforeUpdate = purchaseItemRepository.findAll().size();

        // Update the purchaseItem
        PurchaseItem updatedPurchaseItem = purchaseItemRepository.findById(purchaseItem.getId()).get();
        // Disconnect from session so that the updates on updatedPurchaseItem are not directly saved in db
        em.detach(updatedPurchaseItem);
        updatedPurchaseItem
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
        PurchaseItemDTO purchaseItemDTO = purchaseItemMapper.toDto(updatedPurchaseItem);

        restPurchaseItemMockMvc.perform(put("/api/purchase-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(purchaseItemDTO)))
            .andExpect(status().isOk());

        // Validate the PurchaseItem in the database
        List<PurchaseItem> purchaseItemList = purchaseItemRepository.findAll();
        assertThat(purchaseItemList).hasSize(databaseSizeBeforeUpdate);
        PurchaseItem testPurchaseItem = purchaseItemList.get(purchaseItemList.size() - 1);
        assertThat(testPurchaseItem.getTypeId()).isEqualTo(UPDATED_TYPE_ID);
        assertThat(testPurchaseItem.getQty()).isEqualTo(UPDATED_QTY);
        assertThat(testPurchaseItem.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testPurchaseItem.getDisc()).isEqualTo(UPDATED_DISC);
        assertThat(testPurchaseItem.isDiscPercentStatus()).isEqualTo(UPDATED_DISC_PERCENT_STATUS);
        assertThat(testPurchaseItem.getTaxAmount()).isEqualTo(UPDATED_TAX_AMOUNT);
        assertThat(testPurchaseItem.getNote()).isEqualTo(UPDATED_NOTE);
        assertThat(testPurchaseItem.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testPurchaseItem.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testPurchaseItem.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testPurchaseItem.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
        assertThat(testPurchaseItem.getRecordStatusId()).isEqualTo(UPDATED_RECORD_STATUS_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingPurchaseItem() throws Exception {
        int databaseSizeBeforeUpdate = purchaseItemRepository.findAll().size();

        // Create the PurchaseItem
        PurchaseItemDTO purchaseItemDTO = purchaseItemMapper.toDto(purchaseItem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPurchaseItemMockMvc.perform(put("/api/purchase-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(purchaseItemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PurchaseItem in the database
        List<PurchaseItem> purchaseItemList = purchaseItemRepository.findAll();
        assertThat(purchaseItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePurchaseItem() throws Exception {
        // Initialize the database
        purchaseItemRepository.saveAndFlush(purchaseItem);

        int databaseSizeBeforeDelete = purchaseItemRepository.findAll().size();

        // Delete the purchaseItem
        restPurchaseItemMockMvc.perform(delete("/api/purchase-items/{id}", purchaseItem.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PurchaseItem> purchaseItemList = purchaseItemRepository.findAll();
        assertThat(purchaseItemList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
