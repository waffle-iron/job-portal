package com.factly.jobportal.web.rest;

import com.factly.jobportal.JobportalApp;

import com.factly.jobportal.domain.QuotaJobDetails;
import com.factly.jobportal.domain.QuotaCategory;
import com.factly.jobportal.repository.QuotaJobDetailsRepository;
import com.factly.jobportal.service.QuotaJobDetailsService;
import com.factly.jobportal.repository.search.QuotaJobDetailsSearchRepository;
import com.factly.jobportal.service.dto.QuotaJobDetailsDTO;
import com.factly.jobportal.service.mapper.QuotaJobDetailsMapper;
import com.factly.jobportal.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the QuotaJobDetailsResource REST controller.
 *
 * @see QuotaJobDetailsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JobportalApp.class)
public class QuotaJobDetailsResourceIntTest {

    private static final Integer DEFAULT_VACANCY_COUNT = 0;
    private static final Integer UPDATED_VACANCY_COUNT = 1;

    private static final LocalDate DEFAULT_BORN_BEFORE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BORN_BEFORE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_BORN_AFTER = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BORN_AFTER = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private QuotaJobDetailsRepository quotaJobDetailsRepository;

    @Autowired
    private QuotaJobDetailsMapper quotaJobDetailsMapper;

    @Autowired
    private QuotaJobDetailsService quotaJobDetailsService;

    @Autowired
    private QuotaJobDetailsSearchRepository quotaJobDetailsSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restQuotaJobDetailsMockMvc;

    private QuotaJobDetails quotaJobDetails;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        QuotaJobDetailsResource quotaJobDetailsResource = new QuotaJobDetailsResource(quotaJobDetailsService);
        this.restQuotaJobDetailsMockMvc = MockMvcBuilders.standaloneSetup(quotaJobDetailsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QuotaJobDetails createEntity(EntityManager em) {
        QuotaJobDetails quotaJobDetails = new QuotaJobDetails()
            .vacancyCount(DEFAULT_VACANCY_COUNT)
            .bornBefore(DEFAULT_BORN_BEFORE)
            .bornAfter(DEFAULT_BORN_AFTER);
        // Add required entity
        QuotaCategory quotaCategory = QuotaCategoryResourceIntTest.createEntity(em);
        em.persist(quotaCategory);
        em.flush();
        quotaJobDetails.setQuotaCategory(quotaCategory);
        return quotaJobDetails;
    }

    @Before
    public void initTest() {
        quotaJobDetailsSearchRepository.deleteAll();
        quotaJobDetails = createEntity(em);
    }

    @Test
    @Transactional
    public void createQuotaJobDetails() throws Exception {
        int databaseSizeBeforeCreate = quotaJobDetailsRepository.findAll().size();

        // Create the QuotaJobDetails
        QuotaJobDetailsDTO quotaJobDetailsDTO = quotaJobDetailsMapper.toDto(quotaJobDetails);
        restQuotaJobDetailsMockMvc.perform(post("/api/quota-job-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(quotaJobDetailsDTO)))
            .andExpect(status().isCreated());

        // Validate the QuotaJobDetails in the database
        List<QuotaJobDetails> quotaJobDetailsList = quotaJobDetailsRepository.findAll();
        assertThat(quotaJobDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        QuotaJobDetails testQuotaJobDetails = quotaJobDetailsList.get(quotaJobDetailsList.size() - 1);
        assertThat(testQuotaJobDetails.getVacancyCount()).isEqualTo(DEFAULT_VACANCY_COUNT);
        assertThat(testQuotaJobDetails.getBornBefore()).isEqualTo(DEFAULT_BORN_BEFORE);
        assertThat(testQuotaJobDetails.getBornAfter()).isEqualTo(DEFAULT_BORN_AFTER);

        // Validate the QuotaJobDetails in Elasticsearch
        QuotaJobDetails quotaJobDetailsEs = quotaJobDetailsSearchRepository.findOne(testQuotaJobDetails.getId());
        assertThat(quotaJobDetailsEs).isEqualToComparingFieldByField(testQuotaJobDetails);
    }

    @Test
    @Transactional
    public void createQuotaJobDetailsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = quotaJobDetailsRepository.findAll().size();

        // Create the QuotaJobDetails with an existing ID
        quotaJobDetails.setId(1L);
        QuotaJobDetailsDTO quotaJobDetailsDTO = quotaJobDetailsMapper.toDto(quotaJobDetails);

        // An entity with an existing ID cannot be created, so this API call must fail
        restQuotaJobDetailsMockMvc.perform(post("/api/quota-job-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(quotaJobDetailsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<QuotaJobDetails> quotaJobDetailsList = quotaJobDetailsRepository.findAll();
        assertThat(quotaJobDetailsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkVacancyCountIsRequired() throws Exception {
        int databaseSizeBeforeTest = quotaJobDetailsRepository.findAll().size();
        // set the field null
        quotaJobDetails.setVacancyCount(null);

        // Create the QuotaJobDetails, which fails.
        QuotaJobDetailsDTO quotaJobDetailsDTO = quotaJobDetailsMapper.toDto(quotaJobDetails);

        restQuotaJobDetailsMockMvc.perform(post("/api/quota-job-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(quotaJobDetailsDTO)))
            .andExpect(status().isBadRequest());

        List<QuotaJobDetails> quotaJobDetailsList = quotaJobDetailsRepository.findAll();
        assertThat(quotaJobDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBornBeforeIsRequired() throws Exception {
        int databaseSizeBeforeTest = quotaJobDetailsRepository.findAll().size();
        // set the field null
        quotaJobDetails.setBornBefore(null);

        // Create the QuotaJobDetails, which fails.
        QuotaJobDetailsDTO quotaJobDetailsDTO = quotaJobDetailsMapper.toDto(quotaJobDetails);

        restQuotaJobDetailsMockMvc.perform(post("/api/quota-job-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(quotaJobDetailsDTO)))
            .andExpect(status().isBadRequest());

        List<QuotaJobDetails> quotaJobDetailsList = quotaJobDetailsRepository.findAll();
        assertThat(quotaJobDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBornAfterIsRequired() throws Exception {
        int databaseSizeBeforeTest = quotaJobDetailsRepository.findAll().size();
        // set the field null
        quotaJobDetails.setBornAfter(null);

        // Create the QuotaJobDetails, which fails.
        QuotaJobDetailsDTO quotaJobDetailsDTO = quotaJobDetailsMapper.toDto(quotaJobDetails);

        restQuotaJobDetailsMockMvc.perform(post("/api/quota-job-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(quotaJobDetailsDTO)))
            .andExpect(status().isBadRequest());

        List<QuotaJobDetails> quotaJobDetailsList = quotaJobDetailsRepository.findAll();
        assertThat(quotaJobDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllQuotaJobDetails() throws Exception {
        // Initialize the database
        quotaJobDetailsRepository.saveAndFlush(quotaJobDetails);

        // Get all the quotaJobDetailsList
        restQuotaJobDetailsMockMvc.perform(get("/api/quota-job-details?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(quotaJobDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].vacancyCount").value(hasItem(DEFAULT_VACANCY_COUNT)))
            .andExpect(jsonPath("$.[*].bornBefore").value(hasItem(DEFAULT_BORN_BEFORE.toString())))
            .andExpect(jsonPath("$.[*].bornAfter").value(hasItem(DEFAULT_BORN_AFTER.toString())));
    }

    @Test
    @Transactional
    public void getQuotaJobDetails() throws Exception {
        // Initialize the database
        quotaJobDetailsRepository.saveAndFlush(quotaJobDetails);

        // Get the quotaJobDetails
        restQuotaJobDetailsMockMvc.perform(get("/api/quota-job-details/{id}", quotaJobDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(quotaJobDetails.getId().intValue()))
            .andExpect(jsonPath("$.vacancyCount").value(DEFAULT_VACANCY_COUNT))
            .andExpect(jsonPath("$.bornBefore").value(DEFAULT_BORN_BEFORE.toString()))
            .andExpect(jsonPath("$.bornAfter").value(DEFAULT_BORN_AFTER.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingQuotaJobDetails() throws Exception {
        // Get the quotaJobDetails
        restQuotaJobDetailsMockMvc.perform(get("/api/quota-job-details/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateQuotaJobDetails() throws Exception {
        // Initialize the database
        quotaJobDetailsRepository.saveAndFlush(quotaJobDetails);
        quotaJobDetailsSearchRepository.save(quotaJobDetails);
        int databaseSizeBeforeUpdate = quotaJobDetailsRepository.findAll().size();

        // Update the quotaJobDetails
        QuotaJobDetails updatedQuotaJobDetails = quotaJobDetailsRepository.findOne(quotaJobDetails.getId());
        updatedQuotaJobDetails
            .vacancyCount(UPDATED_VACANCY_COUNT)
            .bornBefore(UPDATED_BORN_BEFORE)
            .bornAfter(UPDATED_BORN_AFTER);
        QuotaJobDetailsDTO quotaJobDetailsDTO = quotaJobDetailsMapper.toDto(updatedQuotaJobDetails);

        restQuotaJobDetailsMockMvc.perform(put("/api/quota-job-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(quotaJobDetailsDTO)))
            .andExpect(status().isOk());

        // Validate the QuotaJobDetails in the database
        List<QuotaJobDetails> quotaJobDetailsList = quotaJobDetailsRepository.findAll();
        assertThat(quotaJobDetailsList).hasSize(databaseSizeBeforeUpdate);
        QuotaJobDetails testQuotaJobDetails = quotaJobDetailsList.get(quotaJobDetailsList.size() - 1);
        assertThat(testQuotaJobDetails.getVacancyCount()).isEqualTo(UPDATED_VACANCY_COUNT);
        assertThat(testQuotaJobDetails.getBornBefore()).isEqualTo(UPDATED_BORN_BEFORE);
        assertThat(testQuotaJobDetails.getBornAfter()).isEqualTo(UPDATED_BORN_AFTER);

        // Validate the QuotaJobDetails in Elasticsearch
        QuotaJobDetails quotaJobDetailsEs = quotaJobDetailsSearchRepository.findOne(testQuotaJobDetails.getId());
        assertThat(quotaJobDetailsEs).isEqualToComparingFieldByField(testQuotaJobDetails);
    }

    @Test
    @Transactional
    public void updateNonExistingQuotaJobDetails() throws Exception {
        int databaseSizeBeforeUpdate = quotaJobDetailsRepository.findAll().size();

        // Create the QuotaJobDetails
        QuotaJobDetailsDTO quotaJobDetailsDTO = quotaJobDetailsMapper.toDto(quotaJobDetails);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restQuotaJobDetailsMockMvc.perform(put("/api/quota-job-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(quotaJobDetailsDTO)))
            .andExpect(status().isCreated());

        // Validate the QuotaJobDetails in the database
        List<QuotaJobDetails> quotaJobDetailsList = quotaJobDetailsRepository.findAll();
        assertThat(quotaJobDetailsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteQuotaJobDetails() throws Exception {
        // Initialize the database
        quotaJobDetailsRepository.saveAndFlush(quotaJobDetails);
        quotaJobDetailsSearchRepository.save(quotaJobDetails);
        int databaseSizeBeforeDelete = quotaJobDetailsRepository.findAll().size();

        // Get the quotaJobDetails
        restQuotaJobDetailsMockMvc.perform(delete("/api/quota-job-details/{id}", quotaJobDetails.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean quotaJobDetailsExistsInEs = quotaJobDetailsSearchRepository.exists(quotaJobDetails.getId());
        assertThat(quotaJobDetailsExistsInEs).isFalse();

        // Validate the database is empty
        List<QuotaJobDetails> quotaJobDetailsList = quotaJobDetailsRepository.findAll();
        assertThat(quotaJobDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchQuotaJobDetails() throws Exception {
        // Initialize the database
        quotaJobDetailsRepository.saveAndFlush(quotaJobDetails);
        quotaJobDetailsSearchRepository.save(quotaJobDetails);

        // Search the quotaJobDetails
        restQuotaJobDetailsMockMvc.perform(get("/api/_search/quota-job-details?query=id:" + quotaJobDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(quotaJobDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].vacancyCount").value(hasItem(DEFAULT_VACANCY_COUNT)))
            .andExpect(jsonPath("$.[*].bornBefore").value(hasItem(DEFAULT_BORN_BEFORE.toString())))
            .andExpect(jsonPath("$.[*].bornAfter").value(hasItem(DEFAULT_BORN_AFTER.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(QuotaJobDetails.class);
        QuotaJobDetails quotaJobDetails1 = new QuotaJobDetails();
        quotaJobDetails1.setId(1L);
        QuotaJobDetails quotaJobDetails2 = new QuotaJobDetails();
        quotaJobDetails2.setId(quotaJobDetails1.getId());
        assertThat(quotaJobDetails1).isEqualTo(quotaJobDetails2);
        quotaJobDetails2.setId(2L);
        assertThat(quotaJobDetails1).isNotEqualTo(quotaJobDetails2);
        quotaJobDetails1.setId(null);
        assertThat(quotaJobDetails1).isNotEqualTo(quotaJobDetails2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(QuotaJobDetailsDTO.class);
        QuotaJobDetailsDTO quotaJobDetailsDTO1 = new QuotaJobDetailsDTO();
        quotaJobDetailsDTO1.setId(1L);
        QuotaJobDetailsDTO quotaJobDetailsDTO2 = new QuotaJobDetailsDTO();
        assertThat(quotaJobDetailsDTO1).isNotEqualTo(quotaJobDetailsDTO2);
        quotaJobDetailsDTO2.setId(quotaJobDetailsDTO1.getId());
        assertThat(quotaJobDetailsDTO1).isEqualTo(quotaJobDetailsDTO2);
        quotaJobDetailsDTO2.setId(2L);
        assertThat(quotaJobDetailsDTO1).isNotEqualTo(quotaJobDetailsDTO2);
        quotaJobDetailsDTO1.setId(null);
        assertThat(quotaJobDetailsDTO1).isNotEqualTo(quotaJobDetailsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(quotaJobDetailsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(quotaJobDetailsMapper.fromId(null)).isNull();
    }
}
