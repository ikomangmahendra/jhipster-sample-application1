package com.kebunkarta.myapp.web.rest;

import com.kebunkarta.myapp.JhipsterSampleApplication1App;
import com.kebunkarta.myapp.domain.Purchase;
import com.kebunkarta.myapp.repository.PurchaseRepository;
import com.kebunkarta.myapp.service.PurchaseService;
import com.kebunkarta.myapp.service.dto.PurchaseDTO;
import com.kebunkarta.myapp.service.mapper.PurchaseMapper;

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
import java.time.LocalDate;
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link PurchaseResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplication1App.class)
@AutoConfigureMockMvc
@WithMockUser
public class PurchaseResourceIT {

    private static final Integer DEFAULT_TYPE_ID = 1;
    private static final Integer UPDATED_TYPE_ID = 2;

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_REF_NO = "AAAAAAAAAA";
    private static final String UPDATED_REF_NO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_OTHER_COST = new BigDecimal(1);
    private static final BigDecimal UPDATED_OTHER_COST = new BigDecimal(2);

    private static final BigDecimal DEFAULT_SUBTOTAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_SUBTOTAL = new BigDecimal(2);

    private static final BigDecimal DEFAULT_TOTAL_TAX = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_TAX = new BigDecimal(2);

    private static final BigDecimal DEFAULT_DISC = new BigDecimal(1);
    private static final BigDecimal UPDATED_DISC = new BigDecimal(2);

    private static final Boolean DEFAULT_IS_DISC_PERCENT = false;
    private static final Boolean UPDATED_IS_DISC_PERCENT = true;

    private static final String DEFAULT_STATUS = "AA";
    private static final String UPDATED_STATUS = "BB";

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
    private PurchaseRepository purchaseRepository;

    @Autowired
    private PurchaseMapper purchaseMapper;

    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPurchaseMockMvc;

    private Purchase purchase;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Purchase createEntity(EntityManager em) {
        Purchase purchase = new Purchase()
            .typeId(DEFAULT_TYPE_ID)
            .date(DEFAULT_DATE)
            .refNo(DEFAULT_REF_NO)
            .description(DEFAULT_DESCRIPTION)
            .otherCost(DEFAULT_OTHER_COST)
            .subtotal(DEFAULT_SUBTOTAL)
            .totalTax(DEFAULT_TOTAL_TAX)
            .disc(DEFAULT_DISC)
            .isDiscPercent(DEFAULT_IS_DISC_PERCENT)
            .status(DEFAULT_STATUS)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE)
            .recordStatusId(DEFAULT_RECORD_STATUS_ID);
        return purchase;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Purchase createUpdatedEntity(EntityManager em) {
        Purchase purchase = new Purchase()
            .typeId(UPDATED_TYPE_ID)
            .date(UPDATED_DATE)
            .refNo(UPDATED_REF_NO)
            .description(UPDATED_DESCRIPTION)
            .otherCost(UPDATED_OTHER_COST)
            .subtotal(UPDATED_SUBTOTAL)
            .totalTax(UPDATED_TOTAL_TAX)
            .disc(UPDATED_DISC)
            .isDiscPercent(UPDATED_IS_DISC_PERCENT)
            .status(UPDATED_STATUS)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE)
            .recordStatusId(UPDATED_RECORD_STATUS_ID);
        return purchase;
    }

    @BeforeEach
    public void initTest() {
        purchase = createEntity(em);
    }

    @Test
    @Transactional
    public void createPurchase() throws Exception {
        int databaseSizeBeforeCreate = purchaseRepository.findAll().size();
        // Create the Purchase
        PurchaseDTO purchaseDTO = purchaseMapper.toDto(purchase);
        restPurchaseMockMvc.perform(post("/api/purchases")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(purchaseDTO)))
            .andExpect(status().isCreated());

        // Validate the Purchase in the database
        List<Purchase> purchaseList = purchaseRepository.findAll();
        assertThat(purchaseList).hasSize(databaseSizeBeforeCreate + 1);
        Purchase testPurchase = purchaseList.get(purchaseList.size() - 1);
        assertThat(testPurchase.getTypeId()).isEqualTo(DEFAULT_TYPE_ID);
        assertThat(testPurchase.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testPurchase.getRefNo()).isEqualTo(DEFAULT_REF_NO);
        assertThat(testPurchase.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testPurchase.getOtherCost()).isEqualTo(DEFAULT_OTHER_COST);
        assertThat(testPurchase.getSubtotal()).isEqualTo(DEFAULT_SUBTOTAL);
        assertThat(testPurchase.getTotalTax()).isEqualTo(DEFAULT_TOTAL_TAX);
        assertThat(testPurchase.getDisc()).isEqualTo(DEFAULT_DISC);
        assertThat(testPurchase.isIsDiscPercent()).isEqualTo(DEFAULT_IS_DISC_PERCENT);
        assertThat(testPurchase.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testPurchase.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testPurchase.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testPurchase.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testPurchase.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
        assertThat(testPurchase.getRecordStatusId()).isEqualTo(DEFAULT_RECORD_STATUS_ID);
    }

    @Test
    @Transactional
    public void createPurchaseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = purchaseRepository.findAll().size();

        // Create the Purchase with an existing ID
        purchase.setId(1L);
        PurchaseDTO purchaseDTO = purchaseMapper.toDto(purchase);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPurchaseMockMvc.perform(post("/api/purchases")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(purchaseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Purchase in the database
        List<Purchase> purchaseList = purchaseRepository.findAll();
        assertThat(purchaseList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTypeIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = purchaseRepository.findAll().size();
        // set the field null
        purchase.setTypeId(null);

        // Create the Purchase, which fails.
        PurchaseDTO purchaseDTO = purchaseMapper.toDto(purchase);


        restPurchaseMockMvc.perform(post("/api/purchases")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(purchaseDTO)))
            .andExpect(status().isBadRequest());

        List<Purchase> purchaseList = purchaseRepository.findAll();
        assertThat(purchaseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = purchaseRepository.findAll().size();
        // set the field null
        purchase.setDate(null);

        // Create the Purchase, which fails.
        PurchaseDTO purchaseDTO = purchaseMapper.toDto(purchase);


        restPurchaseMockMvc.perform(post("/api/purchases")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(purchaseDTO)))
            .andExpect(status().isBadRequest());

        List<Purchase> purchaseList = purchaseRepository.findAll();
        assertThat(purchaseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRefNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = purchaseRepository.findAll().size();
        // set the field null
        purchase.setRefNo(null);

        // Create the Purchase, which fails.
        PurchaseDTO purchaseDTO = purchaseMapper.toDto(purchase);


        restPurchaseMockMvc.perform(post("/api/purchases")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(purchaseDTO)))
            .andExpect(status().isBadRequest());

        List<Purchase> purchaseList = purchaseRepository.findAll();
        assertThat(purchaseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSubtotalIsRequired() throws Exception {
        int databaseSizeBeforeTest = purchaseRepository.findAll().size();
        // set the field null
        purchase.setSubtotal(null);

        // Create the Purchase, which fails.
        PurchaseDTO purchaseDTO = purchaseMapper.toDto(purchase);


        restPurchaseMockMvc.perform(post("/api/purchases")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(purchaseDTO)))
            .andExpect(status().isBadRequest());

        List<Purchase> purchaseList = purchaseRepository.findAll();
        assertThat(purchaseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTotalTaxIsRequired() throws Exception {
        int databaseSizeBeforeTest = purchaseRepository.findAll().size();
        // set the field null
        purchase.setTotalTax(null);

        // Create the Purchase, which fails.
        PurchaseDTO purchaseDTO = purchaseMapper.toDto(purchase);


        restPurchaseMockMvc.perform(post("/api/purchases")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(purchaseDTO)))
            .andExpect(status().isBadRequest());

        List<Purchase> purchaseList = purchaseRepository.findAll();
        assertThat(purchaseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRecordStatusIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = purchaseRepository.findAll().size();
        // set the field null
        purchase.setRecordStatusId(null);

        // Create the Purchase, which fails.
        PurchaseDTO purchaseDTO = purchaseMapper.toDto(purchase);


        restPurchaseMockMvc.perform(post("/api/purchases")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(purchaseDTO)))
            .andExpect(status().isBadRequest());

        List<Purchase> purchaseList = purchaseRepository.findAll();
        assertThat(purchaseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPurchases() throws Exception {
        // Initialize the database
        purchaseRepository.saveAndFlush(purchase);

        // Get all the purchaseList
        restPurchaseMockMvc.perform(get("/api/purchases?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(purchase.getId().intValue())))
            .andExpect(jsonPath("$.[*].typeId").value(hasItem(DEFAULT_TYPE_ID)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].refNo").value(hasItem(DEFAULT_REF_NO)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].otherCost").value(hasItem(DEFAULT_OTHER_COST.intValue())))
            .andExpect(jsonPath("$.[*].subtotal").value(hasItem(DEFAULT_SUBTOTAL.intValue())))
            .andExpect(jsonPath("$.[*].totalTax").value(hasItem(DEFAULT_TOTAL_TAX.intValue())))
            .andExpect(jsonPath("$.[*].disc").value(hasItem(DEFAULT_DISC.intValue())))
            .andExpect(jsonPath("$.[*].isDiscPercent").value(hasItem(DEFAULT_IS_DISC_PERCENT.booleanValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())))
            .andExpect(jsonPath("$.[*].recordStatusId").value(hasItem(DEFAULT_RECORD_STATUS_ID)));
    }
    
    @Test
    @Transactional
    public void getPurchase() throws Exception {
        // Initialize the database
        purchaseRepository.saveAndFlush(purchase);

        // Get the purchase
        restPurchaseMockMvc.perform(get("/api/purchases/{id}", purchase.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(purchase.getId().intValue()))
            .andExpect(jsonPath("$.typeId").value(DEFAULT_TYPE_ID))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.refNo").value(DEFAULT_REF_NO))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.otherCost").value(DEFAULT_OTHER_COST.intValue()))
            .andExpect(jsonPath("$.subtotal").value(DEFAULT_SUBTOTAL.intValue()))
            .andExpect(jsonPath("$.totalTax").value(DEFAULT_TOTAL_TAX.intValue()))
            .andExpect(jsonPath("$.disc").value(DEFAULT_DISC.intValue()))
            .andExpect(jsonPath("$.isDiscPercent").value(DEFAULT_IS_DISC_PERCENT.booleanValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()))
            .andExpect(jsonPath("$.recordStatusId").value(DEFAULT_RECORD_STATUS_ID));
    }
    @Test
    @Transactional
    public void getNonExistingPurchase() throws Exception {
        // Get the purchase
        restPurchaseMockMvc.perform(get("/api/purchases/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePurchase() throws Exception {
        // Initialize the database
        purchaseRepository.saveAndFlush(purchase);

        int databaseSizeBeforeUpdate = purchaseRepository.findAll().size();

        // Update the purchase
        Purchase updatedPurchase = purchaseRepository.findById(purchase.getId()).get();
        // Disconnect from session so that the updates on updatedPurchase are not directly saved in db
        em.detach(updatedPurchase);
        updatedPurchase
            .typeId(UPDATED_TYPE_ID)
            .date(UPDATED_DATE)
            .refNo(UPDATED_REF_NO)
            .description(UPDATED_DESCRIPTION)
            .otherCost(UPDATED_OTHER_COST)
            .subtotal(UPDATED_SUBTOTAL)
            .totalTax(UPDATED_TOTAL_TAX)
            .disc(UPDATED_DISC)
            .isDiscPercent(UPDATED_IS_DISC_PERCENT)
            .status(UPDATED_STATUS)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE)
            .recordStatusId(UPDATED_RECORD_STATUS_ID);
        PurchaseDTO purchaseDTO = purchaseMapper.toDto(updatedPurchase);

        restPurchaseMockMvc.perform(put("/api/purchases")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(purchaseDTO)))
            .andExpect(status().isOk());

        // Validate the Purchase in the database
        List<Purchase> purchaseList = purchaseRepository.findAll();
        assertThat(purchaseList).hasSize(databaseSizeBeforeUpdate);
        Purchase testPurchase = purchaseList.get(purchaseList.size() - 1);
        assertThat(testPurchase.getTypeId()).isEqualTo(UPDATED_TYPE_ID);
        assertThat(testPurchase.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testPurchase.getRefNo()).isEqualTo(UPDATED_REF_NO);
        assertThat(testPurchase.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPurchase.getOtherCost()).isEqualTo(UPDATED_OTHER_COST);
        assertThat(testPurchase.getSubtotal()).isEqualTo(UPDATED_SUBTOTAL);
        assertThat(testPurchase.getTotalTax()).isEqualTo(UPDATED_TOTAL_TAX);
        assertThat(testPurchase.getDisc()).isEqualTo(UPDATED_DISC);
        assertThat(testPurchase.isIsDiscPercent()).isEqualTo(UPDATED_IS_DISC_PERCENT);
        assertThat(testPurchase.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testPurchase.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testPurchase.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testPurchase.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testPurchase.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
        assertThat(testPurchase.getRecordStatusId()).isEqualTo(UPDATED_RECORD_STATUS_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingPurchase() throws Exception {
        int databaseSizeBeforeUpdate = purchaseRepository.findAll().size();

        // Create the Purchase
        PurchaseDTO purchaseDTO = purchaseMapper.toDto(purchase);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPurchaseMockMvc.perform(put("/api/purchases")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(purchaseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Purchase in the database
        List<Purchase> purchaseList = purchaseRepository.findAll();
        assertThat(purchaseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePurchase() throws Exception {
        // Initialize the database
        purchaseRepository.saveAndFlush(purchase);

        int databaseSizeBeforeDelete = purchaseRepository.findAll().size();

        // Delete the purchase
        restPurchaseMockMvc.perform(delete("/api/purchases/{id}", purchase.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Purchase> purchaseList = purchaseRepository.findAll();
        assertThat(purchaseList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
