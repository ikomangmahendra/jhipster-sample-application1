package com.kebunkarta.myapp.web.rest;

import com.kebunkarta.myapp.JhipsterSampleApplication1App;
import com.kebunkarta.myapp.domain.Contact;
import com.kebunkarta.myapp.repository.ContactRepository;
import com.kebunkarta.myapp.service.ContactService;
import com.kebunkarta.myapp.service.dto.ContactDTO;
import com.kebunkarta.myapp.service.mapper.ContactMapper;

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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ContactResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplication1App.class)
@AutoConfigureMockMvc
@WithMockUser
public class ContactResourceIT {

    private static final Boolean DEFAULT_IS_CUSTOMER = false;
    private static final Boolean UPDATED_IS_CUSTOMER = true;

    private static final Boolean DEFAULT_IS_SUPPLIER = false;
    private static final Boolean UPDATED_IS_SUPPLIER = true;

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_1 = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_1 = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_2 = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_2 = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_PROVINCE = "AAAAAAAAAA";
    private static final String UPDATED_PROVINCE = "BBBBBBBBBB";

    private static final String DEFAULT_POST_CODE = "AAAAAAAAAA";
    private static final String UPDATED_POST_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_SALES_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SALES_NAME = "BBBBBBBBBB";

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
    private ContactRepository contactRepository;

    @Autowired
    private ContactMapper contactMapper;

    @Autowired
    private ContactService contactService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restContactMockMvc;

    private Contact contact;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Contact createEntity(EntityManager em) {
        Contact contact = new Contact()
            .isCustomer(DEFAULT_IS_CUSTOMER)
            .isSupplier(DEFAULT_IS_SUPPLIER)
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .phone1(DEFAULT_PHONE_1)
            .phone2(DEFAULT_PHONE_2)
            .email(DEFAULT_EMAIL)
            .address(DEFAULT_ADDRESS)
            .city(DEFAULT_CITY)
            .province(DEFAULT_PROVINCE)
            .postCode(DEFAULT_POST_CODE)
            .salesName(DEFAULT_SALES_NAME)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE)
            .recordStatusId(DEFAULT_RECORD_STATUS_ID);
        return contact;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Contact createUpdatedEntity(EntityManager em) {
        Contact contact = new Contact()
            .isCustomer(UPDATED_IS_CUSTOMER)
            .isSupplier(UPDATED_IS_SUPPLIER)
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .phone1(UPDATED_PHONE_1)
            .phone2(UPDATED_PHONE_2)
            .email(UPDATED_EMAIL)
            .address(UPDATED_ADDRESS)
            .city(UPDATED_CITY)
            .province(UPDATED_PROVINCE)
            .postCode(UPDATED_POST_CODE)
            .salesName(UPDATED_SALES_NAME)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE)
            .recordStatusId(UPDATED_RECORD_STATUS_ID);
        return contact;
    }

    @BeforeEach
    public void initTest() {
        contact = createEntity(em);
    }

    @Test
    @Transactional
    public void createContact() throws Exception {
        int databaseSizeBeforeCreate = contactRepository.findAll().size();
        // Create the Contact
        ContactDTO contactDTO = contactMapper.toDto(contact);
        restContactMockMvc.perform(post("/api/contacts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contactDTO)))
            .andExpect(status().isCreated());

        // Validate the Contact in the database
        List<Contact> contactList = contactRepository.findAll();
        assertThat(contactList).hasSize(databaseSizeBeforeCreate + 1);
        Contact testContact = contactList.get(contactList.size() - 1);
        assertThat(testContact.isIsCustomer()).isEqualTo(DEFAULT_IS_CUSTOMER);
        assertThat(testContact.isIsSupplier()).isEqualTo(DEFAULT_IS_SUPPLIER);
        assertThat(testContact.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testContact.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testContact.getPhone1()).isEqualTo(DEFAULT_PHONE_1);
        assertThat(testContact.getPhone2()).isEqualTo(DEFAULT_PHONE_2);
        assertThat(testContact.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testContact.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testContact.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testContact.getProvince()).isEqualTo(DEFAULT_PROVINCE);
        assertThat(testContact.getPostCode()).isEqualTo(DEFAULT_POST_CODE);
        assertThat(testContact.getSalesName()).isEqualTo(DEFAULT_SALES_NAME);
        assertThat(testContact.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testContact.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testContact.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testContact.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
        assertThat(testContact.getRecordStatusId()).isEqualTo(DEFAULT_RECORD_STATUS_ID);
    }

    @Test
    @Transactional
    public void createContactWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = contactRepository.findAll().size();

        // Create the Contact with an existing ID
        contact.setId(1L);
        ContactDTO contactDTO = contactMapper.toDto(contact);

        // An entity with an existing ID cannot be created, so this API call must fail
        restContactMockMvc.perform(post("/api/contacts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contactDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Contact in the database
        List<Contact> contactList = contactRepository.findAll();
        assertThat(contactList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = contactRepository.findAll().size();
        // set the field null
        contact.setCode(null);

        // Create the Contact, which fails.
        ContactDTO contactDTO = contactMapper.toDto(contact);


        restContactMockMvc.perform(post("/api/contacts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contactDTO)))
            .andExpect(status().isBadRequest());

        List<Contact> contactList = contactRepository.findAll();
        assertThat(contactList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = contactRepository.findAll().size();
        // set the field null
        contact.setName(null);

        // Create the Contact, which fails.
        ContactDTO contactDTO = contactMapper.toDto(contact);


        restContactMockMvc.perform(post("/api/contacts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contactDTO)))
            .andExpect(status().isBadRequest());

        List<Contact> contactList = contactRepository.findAll();
        assertThat(contactList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRecordStatusIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = contactRepository.findAll().size();
        // set the field null
        contact.setRecordStatusId(null);

        // Create the Contact, which fails.
        ContactDTO contactDTO = contactMapper.toDto(contact);


        restContactMockMvc.perform(post("/api/contacts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contactDTO)))
            .andExpect(status().isBadRequest());

        List<Contact> contactList = contactRepository.findAll();
        assertThat(contactList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllContacts() throws Exception {
        // Initialize the database
        contactRepository.saveAndFlush(contact);

        // Get all the contactList
        restContactMockMvc.perform(get("/api/contacts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contact.getId().intValue())))
            .andExpect(jsonPath("$.[*].isCustomer").value(hasItem(DEFAULT_IS_CUSTOMER.booleanValue())))
            .andExpect(jsonPath("$.[*].isSupplier").value(hasItem(DEFAULT_IS_SUPPLIER.booleanValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].phone1").value(hasItem(DEFAULT_PHONE_1)))
            .andExpect(jsonPath("$.[*].phone2").value(hasItem(DEFAULT_PHONE_2)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].province").value(hasItem(DEFAULT_PROVINCE)))
            .andExpect(jsonPath("$.[*].postCode").value(hasItem(DEFAULT_POST_CODE)))
            .andExpect(jsonPath("$.[*].salesName").value(hasItem(DEFAULT_SALES_NAME)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())))
            .andExpect(jsonPath("$.[*].recordStatusId").value(hasItem(DEFAULT_RECORD_STATUS_ID)));
    }
    
    @Test
    @Transactional
    public void getContact() throws Exception {
        // Initialize the database
        contactRepository.saveAndFlush(contact);

        // Get the contact
        restContactMockMvc.perform(get("/api/contacts/{id}", contact.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(contact.getId().intValue()))
            .andExpect(jsonPath("$.isCustomer").value(DEFAULT_IS_CUSTOMER.booleanValue()))
            .andExpect(jsonPath("$.isSupplier").value(DEFAULT_IS_SUPPLIER.booleanValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.phone1").value(DEFAULT_PHONE_1))
            .andExpect(jsonPath("$.phone2").value(DEFAULT_PHONE_2))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY))
            .andExpect(jsonPath("$.province").value(DEFAULT_PROVINCE))
            .andExpect(jsonPath("$.postCode").value(DEFAULT_POST_CODE))
            .andExpect(jsonPath("$.salesName").value(DEFAULT_SALES_NAME))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()))
            .andExpect(jsonPath("$.recordStatusId").value(DEFAULT_RECORD_STATUS_ID));
    }
    @Test
    @Transactional
    public void getNonExistingContact() throws Exception {
        // Get the contact
        restContactMockMvc.perform(get("/api/contacts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContact() throws Exception {
        // Initialize the database
        contactRepository.saveAndFlush(contact);

        int databaseSizeBeforeUpdate = contactRepository.findAll().size();

        // Update the contact
        Contact updatedContact = contactRepository.findById(contact.getId()).get();
        // Disconnect from session so that the updates on updatedContact are not directly saved in db
        em.detach(updatedContact);
        updatedContact
            .isCustomer(UPDATED_IS_CUSTOMER)
            .isSupplier(UPDATED_IS_SUPPLIER)
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .phone1(UPDATED_PHONE_1)
            .phone2(UPDATED_PHONE_2)
            .email(UPDATED_EMAIL)
            .address(UPDATED_ADDRESS)
            .city(UPDATED_CITY)
            .province(UPDATED_PROVINCE)
            .postCode(UPDATED_POST_CODE)
            .salesName(UPDATED_SALES_NAME)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE)
            .recordStatusId(UPDATED_RECORD_STATUS_ID);
        ContactDTO contactDTO = contactMapper.toDto(updatedContact);

        restContactMockMvc.perform(put("/api/contacts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contactDTO)))
            .andExpect(status().isOk());

        // Validate the Contact in the database
        List<Contact> contactList = contactRepository.findAll();
        assertThat(contactList).hasSize(databaseSizeBeforeUpdate);
        Contact testContact = contactList.get(contactList.size() - 1);
        assertThat(testContact.isIsCustomer()).isEqualTo(UPDATED_IS_CUSTOMER);
        assertThat(testContact.isIsSupplier()).isEqualTo(UPDATED_IS_SUPPLIER);
        assertThat(testContact.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testContact.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testContact.getPhone1()).isEqualTo(UPDATED_PHONE_1);
        assertThat(testContact.getPhone2()).isEqualTo(UPDATED_PHONE_2);
        assertThat(testContact.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testContact.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testContact.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testContact.getProvince()).isEqualTo(UPDATED_PROVINCE);
        assertThat(testContact.getPostCode()).isEqualTo(UPDATED_POST_CODE);
        assertThat(testContact.getSalesName()).isEqualTo(UPDATED_SALES_NAME);
        assertThat(testContact.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testContact.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testContact.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testContact.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
        assertThat(testContact.getRecordStatusId()).isEqualTo(UPDATED_RECORD_STATUS_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingContact() throws Exception {
        int databaseSizeBeforeUpdate = contactRepository.findAll().size();

        // Create the Contact
        ContactDTO contactDTO = contactMapper.toDto(contact);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContactMockMvc.perform(put("/api/contacts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contactDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Contact in the database
        List<Contact> contactList = contactRepository.findAll();
        assertThat(contactList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteContact() throws Exception {
        // Initialize the database
        contactRepository.saveAndFlush(contact);

        int databaseSizeBeforeDelete = contactRepository.findAll().size();

        // Delete the contact
        restContactMockMvc.perform(delete("/api/contacts/{id}", contact.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Contact> contactList = contactRepository.findAll();
        assertThat(contactList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
