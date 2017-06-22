package com.factly.jobportal.web.rest;

import com.factly.jobportal.JobportalApp;

import com.factly.jobportal.domain.QuotaCategory;
import com.factly.jobportal.repository.QuotaCategoryRepository;
import com.factly.jobportal.service.QuotaCategoryService;
import com.factly.jobportal.repository.search.QuotaCategorySearchRepository;
import com.factly.jobportal.service.dto.QuotaCategoryDTO;
import com.factly.jobportal.service.mapper.QuotaCategoryMapper;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the QuotaCategoryResource REST controller.
 *
 * @see QuotaCategoryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JobportalApp.class)
public class QuotaCategoryResourceIntTest {

    private static final String DEFAULT_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY = "BBBBBBBBBB";

    @Autowired
    private QuotaCategoryRepository quotaCategoryRepository;

    @Autowired
    private QuotaCategoryMapper quotaCategoryMapper;

    @Autowired
    private QuotaCategoryService quotaCategoryService;

    @Autowired
    private QuotaCategorySearchRepository quotaCategorySearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restQuotaCategoryMockMvc;

    private QuotaCategory quotaCategory;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        QuotaCategoryResource quotaCategoryResource = new QuotaCategoryResource(quotaCategoryService);
        this.restQuotaCategoryMockMvc = MockMvcBuilders.standaloneSetup(quotaCategoryResource)
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
    public static QuotaCategory createEntity(EntityManager em) {
        QuotaCategory quotaCategory = new QuotaCategory()
            .category(DEFAULT_CATEGORY);
        return quotaCategory;
    }

    @Before
    public void initTest() {
        quotaCategorySearchRepository.deleteAll();
        quotaCategory = createEntity(em);
    }

    @Test
    @Transactional
    public void createQuotaCategory() throws Exception {
        int databaseSizeBeforeCreate = quotaCategoryRepository.findAll().size();

        // Create the QuotaCategory
        QuotaCategoryDTO quotaCategoryDTO = quotaCategoryMapper.toDto(quotaCategory);
        restQuotaCategoryMockMvc.perform(post("/api/quota-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(quotaCategoryDTO)))
            .andExpect(status().isCreated());

        // Validate the QuotaCategory in the database
        List<QuotaCategory> quotaCategoryList = quotaCategoryRepository.findAll();
        assertThat(quotaCategoryList).hasSize(databaseSizeBeforeCreate + 1);
        QuotaCategory testQuotaCategory = quotaCategoryList.get(quotaCategoryList.size() - 1);
        assertThat(testQuotaCategory.getCategory()).isEqualTo(DEFAULT_CATEGORY);

        // Validate the QuotaCategory in Elasticsearch
        QuotaCategory quotaCategoryEs = quotaCategorySearchRepository.findOne(testQuotaCategory.getId());
        assertThat(quotaCategoryEs).isEqualToComparingFieldByField(testQuotaCategory);
    }

    @Test
    @Transactional
    public void createQuotaCategoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = quotaCategoryRepository.findAll().size();

        // Create the QuotaCategory with an existing ID
        quotaCategory.setId(1L);
        QuotaCategoryDTO quotaCategoryDTO = quotaCategoryMapper.toDto(quotaCategory);

        // An entity with an existing ID cannot be created, so this API call must fail
        restQuotaCategoryMockMvc.perform(post("/api/quota-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(quotaCategoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<QuotaCategory> quotaCategoryList = quotaCategoryRepository.findAll();
        assertThat(quotaCategoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCategoryIsRequired() throws Exception {
        int databaseSizeBeforeTest = quotaCategoryRepository.findAll().size();
        // set the field null
        quotaCategory.setCategory(null);

        // Create the QuotaCategory, which fails.
        QuotaCategoryDTO quotaCategoryDTO = quotaCategoryMapper.toDto(quotaCategory);

        restQuotaCategoryMockMvc.perform(post("/api/quota-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(quotaCategoryDTO)))
            .andExpect(status().isBadRequest());

        List<QuotaCategory> quotaCategoryList = quotaCategoryRepository.findAll();
        assertThat(quotaCategoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllQuotaCategories() throws Exception {
        // Initialize the database
        quotaCategoryRepository.saveAndFlush(quotaCategory);

        // Get all the quotaCategoryList
        restQuotaCategoryMockMvc.perform(get("/api/quota-categories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(quotaCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY.toString())));
    }

    @Test
    @Transactional
    public void getQuotaCategory() throws Exception {
        // Initialize the database
        quotaCategoryRepository.saveAndFlush(quotaCategory);

        // Get the quotaCategory
        restQuotaCategoryMockMvc.perform(get("/api/quota-categories/{id}", quotaCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(quotaCategory.getId().intValue()))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingQuotaCategory() throws Exception {
        // Get the quotaCategory
        restQuotaCategoryMockMvc.perform(get("/api/quota-categories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateQuotaCategory() throws Exception {
        // Initialize the database
        quotaCategoryRepository.saveAndFlush(quotaCategory);
        quotaCategorySearchRepository.save(quotaCategory);
        int databaseSizeBeforeUpdate = quotaCategoryRepository.findAll().size();

        // Update the quotaCategory
        QuotaCategory updatedQuotaCategory = quotaCategoryRepository.findOne(quotaCategory.getId());
        updatedQuotaCategory
            .category(UPDATED_CATEGORY);
        QuotaCategoryDTO quotaCategoryDTO = quotaCategoryMapper.toDto(updatedQuotaCategory);

        restQuotaCategoryMockMvc.perform(put("/api/quota-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(quotaCategoryDTO)))
            .andExpect(status().isOk());

        // Validate the QuotaCategory in the database
        List<QuotaCategory> quotaCategoryList = quotaCategoryRepository.findAll();
        assertThat(quotaCategoryList).hasSize(databaseSizeBeforeUpdate);
        QuotaCategory testQuotaCategory = quotaCategoryList.get(quotaCategoryList.size() - 1);
        assertThat(testQuotaCategory.getCategory()).isEqualTo(UPDATED_CATEGORY);

        // Validate the QuotaCategory in Elasticsearch
        QuotaCategory quotaCategoryEs = quotaCategorySearchRepository.findOne(testQuotaCategory.getId());
        assertThat(quotaCategoryEs).isEqualToComparingFieldByField(testQuotaCategory);
    }

    @Test
    @Transactional
    public void updateNonExistingQuotaCategory() throws Exception {
        int databaseSizeBeforeUpdate = quotaCategoryRepository.findAll().size();

        // Create the QuotaCategory
        QuotaCategoryDTO quotaCategoryDTO = quotaCategoryMapper.toDto(quotaCategory);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restQuotaCategoryMockMvc.perform(put("/api/quota-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(quotaCategoryDTO)))
            .andExpect(status().isCreated());

        // Validate the QuotaCategory in the database
        List<QuotaCategory> quotaCategoryList = quotaCategoryRepository.findAll();
        assertThat(quotaCategoryList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteQuotaCategory() throws Exception {
        // Initialize the database
        quotaCategoryRepository.saveAndFlush(quotaCategory);
        quotaCategorySearchRepository.save(quotaCategory);
        int databaseSizeBeforeDelete = quotaCategoryRepository.findAll().size();

        // Get the quotaCategory
        restQuotaCategoryMockMvc.perform(delete("/api/quota-categories/{id}", quotaCategory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean quotaCategoryExistsInEs = quotaCategorySearchRepository.exists(quotaCategory.getId());
        assertThat(quotaCategoryExistsInEs).isFalse();

        // Validate the database is empty
        List<QuotaCategory> quotaCategoryList = quotaCategoryRepository.findAll();
        assertThat(quotaCategoryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchQuotaCategory() throws Exception {
        // Initialize the database
        quotaCategoryRepository.saveAndFlush(quotaCategory);
        quotaCategorySearchRepository.save(quotaCategory);

        // Search the quotaCategory
        restQuotaCategoryMockMvc.perform(get("/api/_search/quota-categories?query=id:" + quotaCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(quotaCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(QuotaCategory.class);
        QuotaCategory quotaCategory1 = new QuotaCategory();
        quotaCategory1.setId(1L);
        QuotaCategory quotaCategory2 = new QuotaCategory();
        quotaCategory2.setId(quotaCategory1.getId());
        assertThat(quotaCategory1).isEqualTo(quotaCategory2);
        quotaCategory2.setId(2L);
        assertThat(quotaCategory1).isNotEqualTo(quotaCategory2);
        quotaCategory1.setId(null);
        assertThat(quotaCategory1).isNotEqualTo(quotaCategory2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(QuotaCategoryDTO.class);
        QuotaCategoryDTO quotaCategoryDTO1 = new QuotaCategoryDTO();
        quotaCategoryDTO1.setId(1L);
        QuotaCategoryDTO quotaCategoryDTO2 = new QuotaCategoryDTO();
        assertThat(quotaCategoryDTO1).isNotEqualTo(quotaCategoryDTO2);
        quotaCategoryDTO2.setId(quotaCategoryDTO1.getId());
        assertThat(quotaCategoryDTO1).isEqualTo(quotaCategoryDTO2);
        quotaCategoryDTO2.setId(2L);
        assertThat(quotaCategoryDTO1).isNotEqualTo(quotaCategoryDTO2);
        quotaCategoryDTO1.setId(null);
        assertThat(quotaCategoryDTO1).isNotEqualTo(quotaCategoryDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(quotaCategoryMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(quotaCategoryMapper.fromId(null)).isNull();
    }
}
