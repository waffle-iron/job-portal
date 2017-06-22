package com.factly.jobportal.web.rest;

import com.factly.jobportal.JobportalApp;

import com.factly.jobportal.domain.TestSkill;
import com.factly.jobportal.repository.TestSkillRepository;
import com.factly.jobportal.service.TestSkillService;
import com.factly.jobportal.repository.search.TestSkillSearchRepository;
import com.factly.jobportal.service.dto.TestSkillDTO;
import com.factly.jobportal.service.mapper.TestSkillMapper;
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
 * Test class for the TestSkillResource REST controller.
 *
 * @see TestSkillResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JobportalApp.class)
public class TestSkillResourceIntTest {

    private static final String DEFAULT_SKILL = "AAAAAAAAAA";
    private static final String UPDATED_SKILL = "BBBBBBBBBB";

    @Autowired
    private TestSkillRepository testSkillRepository;

    @Autowired
    private TestSkillMapper testSkillMapper;

    @Autowired
    private TestSkillService testSkillService;

    @Autowired
    private TestSkillSearchRepository testSkillSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTestSkillMockMvc;

    private TestSkill testSkill;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TestSkillResource testSkillResource = new TestSkillResource(testSkillService);
        this.restTestSkillMockMvc = MockMvcBuilders.standaloneSetup(testSkillResource)
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
    public static TestSkill createEntity(EntityManager em) {
        TestSkill testSkill = new TestSkill()
            .skill(DEFAULT_SKILL);
        return testSkill;
    }

    @Before
    public void initTest() {
        testSkillSearchRepository.deleteAll();
        testSkill = createEntity(em);
    }

    @Test
    @Transactional
    public void createTestSkill() throws Exception {
        int databaseSizeBeforeCreate = testSkillRepository.findAll().size();

        // Create the TestSkill
        TestSkillDTO testSkillDTO = testSkillMapper.toDto(testSkill);
        restTestSkillMockMvc.perform(post("/api/test-skills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(testSkillDTO)))
            .andExpect(status().isCreated());

        // Validate the TestSkill in the database
        List<TestSkill> testSkillList = testSkillRepository.findAll();
        assertThat(testSkillList).hasSize(databaseSizeBeforeCreate + 1);
        TestSkill testTestSkill = testSkillList.get(testSkillList.size() - 1);
        assertThat(testTestSkill.getSkill()).isEqualTo(DEFAULT_SKILL);

        // Validate the TestSkill in Elasticsearch
        TestSkill testSkillEs = testSkillSearchRepository.findOne(testTestSkill.getId());
        assertThat(testSkillEs).isEqualToComparingFieldByField(testTestSkill);
    }

    @Test
    @Transactional
    public void createTestSkillWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = testSkillRepository.findAll().size();

        // Create the TestSkill with an existing ID
        testSkill.setId(1L);
        TestSkillDTO testSkillDTO = testSkillMapper.toDto(testSkill);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTestSkillMockMvc.perform(post("/api/test-skills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(testSkillDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<TestSkill> testSkillList = testSkillRepository.findAll();
        assertThat(testSkillList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkSkillIsRequired() throws Exception {
        int databaseSizeBeforeTest = testSkillRepository.findAll().size();
        // set the field null
        testSkill.setSkill(null);

        // Create the TestSkill, which fails.
        TestSkillDTO testSkillDTO = testSkillMapper.toDto(testSkill);

        restTestSkillMockMvc.perform(post("/api/test-skills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(testSkillDTO)))
            .andExpect(status().isBadRequest());

        List<TestSkill> testSkillList = testSkillRepository.findAll();
        assertThat(testSkillList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTestSkills() throws Exception {
        // Initialize the database
        testSkillRepository.saveAndFlush(testSkill);

        // Get all the testSkillList
        restTestSkillMockMvc.perform(get("/api/test-skills?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(testSkill.getId().intValue())))
            .andExpect(jsonPath("$.[*].skill").value(hasItem(DEFAULT_SKILL.toString())));
    }

    @Test
    @Transactional
    public void getTestSkill() throws Exception {
        // Initialize the database
        testSkillRepository.saveAndFlush(testSkill);

        // Get the testSkill
        restTestSkillMockMvc.perform(get("/api/test-skills/{id}", testSkill.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(testSkill.getId().intValue()))
            .andExpect(jsonPath("$.skill").value(DEFAULT_SKILL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTestSkill() throws Exception {
        // Get the testSkill
        restTestSkillMockMvc.perform(get("/api/test-skills/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTestSkill() throws Exception {
        // Initialize the database
        testSkillRepository.saveAndFlush(testSkill);
        testSkillSearchRepository.save(testSkill);
        int databaseSizeBeforeUpdate = testSkillRepository.findAll().size();

        // Update the testSkill
        TestSkill updatedTestSkill = testSkillRepository.findOne(testSkill.getId());
        updatedTestSkill
            .skill(UPDATED_SKILL);
        TestSkillDTO testSkillDTO = testSkillMapper.toDto(updatedTestSkill);

        restTestSkillMockMvc.perform(put("/api/test-skills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(testSkillDTO)))
            .andExpect(status().isOk());

        // Validate the TestSkill in the database
        List<TestSkill> testSkillList = testSkillRepository.findAll();
        assertThat(testSkillList).hasSize(databaseSizeBeforeUpdate);
        TestSkill testTestSkill = testSkillList.get(testSkillList.size() - 1);
        assertThat(testTestSkill.getSkill()).isEqualTo(UPDATED_SKILL);

        // Validate the TestSkill in Elasticsearch
        TestSkill testSkillEs = testSkillSearchRepository.findOne(testTestSkill.getId());
        assertThat(testSkillEs).isEqualToComparingFieldByField(testTestSkill);
    }

    @Test
    @Transactional
    public void updateNonExistingTestSkill() throws Exception {
        int databaseSizeBeforeUpdate = testSkillRepository.findAll().size();

        // Create the TestSkill
        TestSkillDTO testSkillDTO = testSkillMapper.toDto(testSkill);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTestSkillMockMvc.perform(put("/api/test-skills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(testSkillDTO)))
            .andExpect(status().isCreated());

        // Validate the TestSkill in the database
        List<TestSkill> testSkillList = testSkillRepository.findAll();
        assertThat(testSkillList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTestSkill() throws Exception {
        // Initialize the database
        testSkillRepository.saveAndFlush(testSkill);
        testSkillSearchRepository.save(testSkill);
        int databaseSizeBeforeDelete = testSkillRepository.findAll().size();

        // Get the testSkill
        restTestSkillMockMvc.perform(delete("/api/test-skills/{id}", testSkill.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean testSkillExistsInEs = testSkillSearchRepository.exists(testSkill.getId());
        assertThat(testSkillExistsInEs).isFalse();

        // Validate the database is empty
        List<TestSkill> testSkillList = testSkillRepository.findAll();
        assertThat(testSkillList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchTestSkill() throws Exception {
        // Initialize the database
        testSkillRepository.saveAndFlush(testSkill);
        testSkillSearchRepository.save(testSkill);

        // Search the testSkill
        restTestSkillMockMvc.perform(get("/api/_search/test-skills?query=id:" + testSkill.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(testSkill.getId().intValue())))
            .andExpect(jsonPath("$.[*].skill").value(hasItem(DEFAULT_SKILL.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TestSkill.class);
        TestSkill testSkill1 = new TestSkill();
        testSkill1.setId(1L);
        TestSkill testSkill2 = new TestSkill();
        testSkill2.setId(testSkill1.getId());
        assertThat(testSkill1).isEqualTo(testSkill2);
        testSkill2.setId(2L);
        assertThat(testSkill1).isNotEqualTo(testSkill2);
        testSkill1.setId(null);
        assertThat(testSkill1).isNotEqualTo(testSkill2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TestSkillDTO.class);
        TestSkillDTO testSkillDTO1 = new TestSkillDTO();
        testSkillDTO1.setId(1L);
        TestSkillDTO testSkillDTO2 = new TestSkillDTO();
        assertThat(testSkillDTO1).isNotEqualTo(testSkillDTO2);
        testSkillDTO2.setId(testSkillDTO1.getId());
        assertThat(testSkillDTO1).isEqualTo(testSkillDTO2);
        testSkillDTO2.setId(2L);
        assertThat(testSkillDTO1).isNotEqualTo(testSkillDTO2);
        testSkillDTO1.setId(null);
        assertThat(testSkillDTO1).isNotEqualTo(testSkillDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(testSkillMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(testSkillMapper.fromId(null)).isNull();
    }
}
