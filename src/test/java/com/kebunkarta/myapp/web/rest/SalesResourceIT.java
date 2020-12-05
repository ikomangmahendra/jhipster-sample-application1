package com.kebunkarta.myapp.web.rest;

import com.kebunkarta.myapp.JhipsterSampleApplication1App;
import com.kebunkarta.myapp.domain.Sales;
import com.kebunkarta.myapp.repository.SalesRepository;
import com.kebunkarta.myapp.service.SalesService;
import com.kebunkarta.myapp.service.dto.SalesDTO;
import com.kebunkarta.myapp.service.mapper.SalesMapper;

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
 * Integration tests for the {@link SalesResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplication1App.class)
@AutoConfigureMockMvc
@WithMockUser
public class SalesResourceIT {

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
    private SalesRepository salesRepository;

    @Autowired
    private SalesMapper salesMapper;

    @Autowired
    private SalesService salesService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSalesMockMvc;

    private Sales sales;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sales createEntity(EntityManager em) {
        Sales sales = new Sales()
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
        return sales;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sales createUpdatedEntity(EntityManager em) {
        Sales sales = new Sales()
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
        return sales;
    }

    @BeforeEach
    public void initTest() {
        sales = createEntity(em);
    }

    @Test
    @Transactional
    public void createSales() throws Exception {
        int databaseSizeBeforeCreate = salesRepository.findAll().size();
        // Create the Sales
        SalesDTO salesDTO = salesMapper.toDto(sales);
        restSalesMockMvc.perform(post("/api/sales")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(salesDTO)))
            .andExpect(status().isCreated());

        // Validate the Sales in the database
        List<Sales> salesList = salesRepository.findAll();
        assertThat(salesList).hasSize(databaseSizeBeforeCreate + 1);
        Sales testSales = salesList.get(salesList.size() - 1);
        assertThat(testSales.getTypeId()).isEqualTo(DEFAULT_TYPE_ID);
        assertThat(testSales.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testSales.getRefNo()).isEqualTo(DEFAULT_REF_NO);
        assertThat(testSales.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testSales.getOtherCost()).isEqualTo(DEFAULT_OTHER_COST);
        assertThat(testSales.getSubtotal()).isEqualTo(DEFAULT_SUBTOTAL);
        assertThat(testSales.getTotalTax()).isEqualTo(DEFAULT_TOTAL_TAX);
        assertThat(testSales.getDisc()).isEqualTo(DEFAULT_DISC);
        assertThat(testSales.isIsDiscPercent()).isEqualTo(DEFAULT_IS_DISC_PERCENT);
        assertThat(testSales.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testSales.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testSales.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testSales.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testSales.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
        assertThat(testSales.getRecordStatusId()).isEqualTo(DEFAULT_RECORD_STATUS_ID);
    }

    @Test
    @Transactional
    public void createSalesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = salesRepository.findAll().size();

        // Create the Sales with an existing ID
        sales.setId(1L);
        SalesDTO salesDTO = salesMapper.toDto(sales);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSalesMockMvc.perform(post("/api/sales")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(salesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Sales in the database
        List<Sales> salesList = salesRepository.findAll();
        assertThat(salesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTypeIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = salesRepository.findAll().size();
        // set the field null
        sales.setTypeId(null);

        // Create the Sales, which fails.
        SalesDTO salesDTO = salesMapper.toDto(sales);


        restSalesMockMvc.perform(post("/api/sales")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(salesDTO)))
            .andExpect(status().isBadRequest());

        List<Sales> salesList = salesRepository.findAll();
        assertThat(salesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = salesRepository.findAll().size();
        // set the field null
        sales.setDate(null);

        // Create the Sales, which fails.
        SalesDTO salesDTO = salesMapper.toDto(sales);


        restSalesMockMvc.perform(post("/api/sales")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(salesDTO)))
            .andExpect(status().isBadRequest());

        List<Sales> salesList = salesRepository.findAll();
        assertThat(salesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRefNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = salesRepository.findAll().size();
        // set the field null
        sales.setRefNo(null);

        // Create the Sales, which fails.
        SalesDTO salesDTO = salesMapper.toDto(sales);


        restSalesMockMvc.perform(post("/api/sales")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(salesDTO)))
            .andExpect(status().isBadRequest());

        List<Sales> salesList = salesRepository.findAll();
        assertThat(salesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSubtotalIsRequired() throws Exception {
        int databaseSizeBeforeTest = salesRepository.findAll().size();
        // set the field null
        sales.setSubtotal(null);

        // Create the Sales, which fails.
        SalesDTO salesDTO = salesMapper.toDto(sales);


        restSalesMockMvc.perform(post("/api/sales")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(salesDTO)))
            .andExpect(status().isBadRequest());

        List<Sales> salesList = salesRepository.findAll();
        assertThat(salesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTotalTaxIsRequired() throws Exception {
        int databaseSizeBeforeTest = salesRepository.findAll().size();
        // set the field null
        sales.setTotalTax(null);

        // Create the Sales, which fails.
        SalesDTO salesDTO = salesMapper.toDto(sales);


        restSalesMockMvc.perform(post("/api/sales")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(salesDTO)))
            .andExpect(status().isBadRequest());

        List<Sales> salesList = salesRepository.findAll();
        assertThat(salesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRecordStatusIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = salesRepository.findAll().size();
        // set the field null
        sales.setRecordStatusId(null);

        // Create the Sales, which fails.
        SalesDTO salesDTO = salesMapper.toDto(sales);


        restSalesMockMvc.perform(post("/api/sales")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(salesDTO)))
            .andExpect(status().isBadRequest());

        List<Sales> salesList = salesRepository.findAll();
        assertThat(salesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSales() throws Exception {
        // Initialize the database
        salesRepository.saveAndFlush(sales);

        // Get all the salesList
        restSalesMockMvc.perform(get("/api/sales?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sales.getId().intValue())))
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
    public void getSales() throws Exception {
        // Initialize the database
        salesRepository.saveAndFlush(sales);

        // Get the sales
        restSalesMockMvc.perform(get("/api/sales/{id}", sales.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sales.getId().intValue()))
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
    public void getNonExistingSales() throws Exception {
        // Get the sales
        restSalesMockMvc.perform(get("/api/sales/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSales() throws Exception {
        // Initialize the database
        salesRepository.saveAndFlush(sales);

        int databaseSizeBeforeUpdate = salesRepository.findAll().size();

        // Update the sales
        Sales updatedSales = salesRepository.findById(sales.getId()).get();
        // Disconnect from session so that the updates on updatedSales are not directly saved in db
        em.detach(updatedSales);
        updatedSales
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
        SalesDTO salesDTO = salesMapper.toDto(updatedSales);

        restSalesMockMvc.perform(put("/api/sales")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(salesDTO)))
            .andExpect(status().isOk());

        // Validate the Sales in the database
        List<Sales> salesList = salesRepository.findAll();
        assertThat(salesList).hasSize(databaseSizeBeforeUpdate);
        Sales testSales = salesList.get(salesList.size() - 1);
        assertThat(testSales.getTypeId()).isEqualTo(UPDATED_TYPE_ID);
        assertThat(testSales.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testSales.getRefNo()).isEqualTo(UPDATED_REF_NO);
        assertThat(testSales.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testSales.getOtherCost()).isEqualTo(UPDATED_OTHER_COST);
        assertThat(testSales.getSubtotal()).isEqualTo(UPDATED_SUBTOTAL);
        assertThat(testSales.getTotalTax()).isEqualTo(UPDATED_TOTAL_TAX);
        assertThat(testSales.getDisc()).isEqualTo(UPDATED_DISC);
        assertThat(testSales.isIsDiscPercent()).isEqualTo(UPDATED_IS_DISC_PERCENT);
        assertThat(testSales.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testSales.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testSales.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testSales.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testSales.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
        assertThat(testSales.getRecordStatusId()).isEqualTo(UPDATED_RECORD_STATUS_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingSales() throws Exception {
        int databaseSizeBeforeUpdate = salesRepository.findAll().size();

        // Create the Sales
        SalesDTO salesDTO = salesMapper.toDto(sales);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSalesMockMvc.perform(put("/api/sales")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(salesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Sales in the database
        List<Sales> salesList = salesRepository.findAll();
        assertThat(salesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSales() throws Exception {
        // Initialize the database
        salesRepository.saveAndFlush(sales);

        int databaseSizeBeforeDelete = salesRepository.findAll().size();

        // Delete the sales
        restSalesMockMvc.perform(delete("/api/sales/{id}", sales.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Sales> salesList = salesRepository.findAll();
        assertThat(salesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
