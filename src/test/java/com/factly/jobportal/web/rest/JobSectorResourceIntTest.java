package com.factly.jobportal.web.rest;

import com.factly.jobportal.JobportalApp;

import com.factly.jobportal.domain.JobSector;
import com.factly.jobportal.repository.JobSectorRepository;
import com.factly.jobportal.service.JobSectorService;
import com.factly.jobportal.repository.search.JobSectorSearchRepository;
import com.factly.jobportal.service.dto.JobSectorDTO;
import com.factly.jobportal.service.mapper.JobSectorMapper;
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
 * Test class for the JobSectorResource REST controller.
 *
 * @see JobSectorResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JobportalApp.class)
public class JobSectorResourceIntTest {

    private static final String DEFAULT_SECTOR = "AAAAAAAAAA";
    private static final String UPDATED_SECTOR = "BBBBBBBBBB";

    private static final String DEFAULT_ICON_URL = "AAAAAAAAAA";
    private static final String UPDATED_ICON_URL = "BBBBBBBBBB";

    @Autowired
    private JobSectorRepository jobSectorRepository;

    @Autowired
    private JobSectorMapper jobSectorMapper;

    @Autowired
    private JobSectorService jobSectorService;

    @Autowired
    private JobSectorSearchRepository jobSectorSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restJobSectorMockMvc;

    private JobSector jobSector;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        JobSectorResource jobSectorResource = new JobSectorResource(jobSectorService);
        this.restJobSectorMockMvc = MockMvcBuilders.standaloneSetup(jobSectorResource)
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
    public static JobSector createEntity(EntityManager em) {
        JobSector jobSector = new JobSector()
            .sector(DEFAULT_SECTOR)
            .iconUrl(DEFAULT_ICON_URL);
        return jobSector;
    }

    @Before
    public void initTest() {
        jobSectorSearchRepository.deleteAll();
        jobSector = createEntity(em);
    }

    @Test
    @Transactional
    public void createJobSector() throws Exception {
        int databaseSizeBeforeCreate = jobSectorRepository.findAll().size();

        // Create the JobSector
        JobSectorDTO jobSectorDTO = jobSectorMapper.toDto(jobSector);
        restJobSectorMockMvc.perform(post("/api/job-sectors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobSectorDTO)))
            .andExpect(status().isCreated());

        // Validate the JobSector in the database
        List<JobSector> jobSectorList = jobSectorRepository.findAll();
        assertThat(jobSectorList).hasSize(databaseSizeBeforeCreate + 1);
        JobSector testJobSector = jobSectorList.get(jobSectorList.size() - 1);
        assertThat(testJobSector.getSector()).isEqualTo(DEFAULT_SECTOR);
        assertThat(testJobSector.getIconUrl()).isEqualTo(DEFAULT_ICON_URL);

        // Validate the JobSector in Elasticsearch
        JobSector jobSectorEs = jobSectorSearchRepository.findOne(testJobSector.getId());
        assertThat(jobSectorEs).isEqualToComparingFieldByField(testJobSector);
    }

    @Test
    @Transactional
    public void createJobSectorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = jobSectorRepository.findAll().size();

        // Create the JobSector with an existing ID
        jobSector.setId(1L);
        JobSectorDTO jobSectorDTO = jobSectorMapper.toDto(jobSector);

        // An entity with an existing ID cannot be created, so this API call must fail
        restJobSectorMockMvc.perform(post("/api/job-sectors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobSectorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<JobSector> jobSectorList = jobSectorRepository.findAll();
        assertThat(jobSectorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkSectorIsRequired() throws Exception {
        int databaseSizeBeforeTest = jobSectorRepository.findAll().size();
        // set the field null
        jobSector.setSector(null);

        // Create the JobSector, which fails.
        JobSectorDTO jobSectorDTO = jobSectorMapper.toDto(jobSector);

        restJobSectorMockMvc.perform(post("/api/job-sectors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobSectorDTO)))
            .andExpect(status().isBadRequest());

        List<JobSector> jobSectorList = jobSectorRepository.findAll();
        assertThat(jobSectorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllJobSectors() throws Exception {
        // Initialize the database
        jobSectorRepository.saveAndFlush(jobSector);

        // Get all the jobSectorList
        restJobSectorMockMvc.perform(get("/api/job-sectors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jobSector.getId().intValue())))
            .andExpect(jsonPath("$.[*].sector").value(hasItem(DEFAULT_SECTOR.toString())))
            .andExpect(jsonPath("$.[*].iconUrl").value(hasItem(DEFAULT_ICON_URL.toString())));
    }

    @Test
    @Transactional
    public void getJobSector() throws Exception {
        // Initialize the database
        jobSectorRepository.saveAndFlush(jobSector);

        // Get the jobSector
        restJobSectorMockMvc.perform(get("/api/job-sectors/{id}", jobSector.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(jobSector.getId().intValue()))
            .andExpect(jsonPath("$.sector").value(DEFAULT_SECTOR.toString()))
            .andExpect(jsonPath("$.iconUrl").value(DEFAULT_ICON_URL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingJobSector() throws Exception {
        // Get the jobSector
        restJobSectorMockMvc.perform(get("/api/job-sectors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJobSector() throws Exception {
        // Initialize the database
        jobSectorRepository.saveAndFlush(jobSector);
        jobSectorSearchRepository.save(jobSector);
        int databaseSizeBeforeUpdate = jobSectorRepository.findAll().size();

        // Update the jobSector
        JobSector updatedJobSector = jobSectorRepository.findOne(jobSector.getId());
        updatedJobSector
            .sector(UPDATED_SECTOR)
            .iconUrl(UPDATED_ICON_URL);
        JobSectorDTO jobSectorDTO = jobSectorMapper.toDto(updatedJobSector);

        restJobSectorMockMvc.perform(put("/api/job-sectors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobSectorDTO)))
            .andExpect(status().isOk());

        // Validate the JobSector in the database
        List<JobSector> jobSectorList = jobSectorRepository.findAll();
        assertThat(jobSectorList).hasSize(databaseSizeBeforeUpdate);
        JobSector testJobSector = jobSectorList.get(jobSectorList.size() - 1);
        assertThat(testJobSector.getSector()).isEqualTo(UPDATED_SECTOR);
        assertThat(testJobSector.getIconUrl()).isEqualTo(UPDATED_ICON_URL);

        // Validate the JobSector in Elasticsearch
        JobSector jobSectorEs = jobSectorSearchRepository.findOne(testJobSector.getId());
        assertThat(jobSectorEs).isEqualToComparingFieldByField(testJobSector);
    }

    @Test
    @Transactional
    public void updateNonExistingJobSector() throws Exception {
        int databaseSizeBeforeUpdate = jobSectorRepository.findAll().size();

        // Create the JobSector
        JobSectorDTO jobSectorDTO = jobSectorMapper.toDto(jobSector);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restJobSectorMockMvc.perform(put("/api/job-sectors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobSectorDTO)))
            .andExpect(status().isCreated());

        // Validate the JobSector in the database
        List<JobSector> jobSectorList = jobSectorRepository.findAll();
        assertThat(jobSectorList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteJobSector() throws Exception {
        // Initialize the database
        jobSectorRepository.saveAndFlush(jobSector);
        jobSectorSearchRepository.save(jobSector);
        int databaseSizeBeforeDelete = jobSectorRepository.findAll().size();

        // Get the jobSector
        restJobSectorMockMvc.perform(delete("/api/job-sectors/{id}", jobSector.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean jobSectorExistsInEs = jobSectorSearchRepository.exists(jobSector.getId());
        assertThat(jobSectorExistsInEs).isFalse();

        // Validate the database is empty
        List<JobSector> jobSectorList = jobSectorRepository.findAll();
        assertThat(jobSectorList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchJobSector() throws Exception {
        // Initialize the database
        jobSectorRepository.saveAndFlush(jobSector);
        jobSectorSearchRepository.save(jobSector);

        // Search the jobSector
        restJobSectorMockMvc.perform(get("/api/_search/job-sectors?query=id:" + jobSector.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jobSector.getId().intValue())))
            .andExpect(jsonPath("$.[*].sector").value(hasItem(DEFAULT_SECTOR.toString())))
            .andExpect(jsonPath("$.[*].iconUrl").value(hasItem(DEFAULT_ICON_URL.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(JobSector.class);
        JobSector jobSector1 = new JobSector();
        jobSector1.setId(1L);
        JobSector jobSector2 = new JobSector();
        jobSector2.setId(jobSector1.getId());
        assertThat(jobSector1).isEqualTo(jobSector2);
        jobSector2.setId(2L);
        assertThat(jobSector1).isNotEqualTo(jobSector2);
        jobSector1.setId(null);
        assertThat(jobSector1).isNotEqualTo(jobSector2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(JobSectorDTO.class);
        JobSectorDTO jobSectorDTO1 = new JobSectorDTO();
        jobSectorDTO1.setId(1L);
        JobSectorDTO jobSectorDTO2 = new JobSectorDTO();
        assertThat(jobSectorDTO1).isNotEqualTo(jobSectorDTO2);
        jobSectorDTO2.setId(jobSectorDTO1.getId());
        assertThat(jobSectorDTO1).isEqualTo(jobSectorDTO2);
        jobSectorDTO2.setId(2L);
        assertThat(jobSectorDTO1).isNotEqualTo(jobSectorDTO2);
        jobSectorDTO1.setId(null);
        assertThat(jobSectorDTO1).isNotEqualTo(jobSectorDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(jobSectorMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(jobSectorMapper.fromId(null)).isNull();
    }
}
