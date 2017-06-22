package com.factly.jobportal.web.rest;

import com.factly.jobportal.JobportalApp;

import com.factly.jobportal.domain.JobNotification;
import com.factly.jobportal.domain.ClientType;
import com.factly.jobportal.domain.JobSector;
import com.factly.jobportal.domain.JobType;
import com.factly.jobportal.domain.TestSkill;
import com.factly.jobportal.domain.Language;
import com.factly.jobportal.domain.SelectionProcedure;
import com.factly.jobportal.repository.JobNotificationRepository;
import com.factly.jobportal.service.JobNotificationService;
import com.factly.jobportal.repository.search.JobNotificationSearchRepository;
import com.factly.jobportal.service.dto.JobNotificationDTO;
import com.factly.jobportal.service.mapper.JobNotificationMapper;
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
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the JobNotificationResource REST controller.
 *
 * @see JobNotificationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JobportalApp.class)
public class JobNotificationResourceIntTest {

    private static final String DEFAULT_HEADLINE = "AAAAAAAAAA";
    private static final String UPDATED_HEADLINE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_NOTIFICATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_NOTIFICATION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_ORGANIZATION = "AAAAAAAAAA";
    private static final String UPDATED_ORGANIZATION = "BBBBBBBBBB";

    private static final String DEFAULT_JOB_ROLE = "AAAAAAAAAA";
    private static final String UPDATED_JOB_ROLE = "BBBBBBBBBB";

    private static final String DEFAULT_JOB_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_JOB_LOCATION = "BBBBBBBBBB";

    private static final Integer DEFAULT_TOTAL_VACANCY_COUNT = 0;
    private static final Integer UPDATED_TOTAL_VACANCY_COUNT = 1;

    private static final String DEFAULT_ADDITIONAL_QUALIFICATION = "AAAAAAAAAA";
    private static final String UPDATED_ADDITIONAL_QUALIFICATION = "BBBBBBBBBB";

    private static final Integer DEFAULT_WORK_EXPERIENCE = 0;
    private static final Integer UPDATED_WORK_EXPERIENCE = 1;

    private static final Integer DEFAULT_SALARY = 0;
    private static final Integer UPDATED_SALARY = 1;

    private static final LocalDate DEFAULT_APPLICATION_DEADLINE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_APPLICATION_DEADLINE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_NOTIFICATION_LINK = "AAAAAAAAAA";
    private static final String UPDATED_NOTIFICATION_LINK = "BBBBBBBBBB";

    private static final String DEFAULT_APPLICATION_LINK = "AAAAAAAAAA";
    private static final String UPDATED_APPLICATION_LINK = "BBBBBBBBBB";

    private static final String DEFAULT_EXAM_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_EXAM_LOCATION = "BBBBBBBBBB";

    @Autowired
    private JobNotificationRepository jobNotificationRepository;

    @Autowired
    private JobNotificationMapper jobNotificationMapper;

    @Autowired
    private JobNotificationService jobNotificationService;

    @Autowired
    private JobNotificationSearchRepository jobNotificationSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restJobNotificationMockMvc;

    private JobNotification jobNotification;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        JobNotificationResource jobNotificationResource = new JobNotificationResource(jobNotificationService);
        this.restJobNotificationMockMvc = MockMvcBuilders.standaloneSetup(jobNotificationResource)
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
    public static JobNotification createEntity(EntityManager em) {
        JobNotification jobNotification = new JobNotification()
            .headline(DEFAULT_HEADLINE)
            .notificationDate(DEFAULT_NOTIFICATION_DATE)
            .organization(DEFAULT_ORGANIZATION)
            .jobRole(DEFAULT_JOB_ROLE)
            .jobLocation(DEFAULT_JOB_LOCATION)
            .totalVacancyCount(DEFAULT_TOTAL_VACANCY_COUNT)
            .additionalQualification(DEFAULT_ADDITIONAL_QUALIFICATION)
            .workExperience(DEFAULT_WORK_EXPERIENCE)
            .salary(DEFAULT_SALARY)
            .applicationDeadline(DEFAULT_APPLICATION_DEADLINE)
            .description(DEFAULT_DESCRIPTION)
            .notificationLink(DEFAULT_NOTIFICATION_LINK)
            .applicationLink(DEFAULT_APPLICATION_LINK)
            .examLocation(DEFAULT_EXAM_LOCATION);
        // Add required entity
        ClientType clientType = ClientTypeResourceIntTest.createEntity(em);
        em.persist(clientType);
        em.flush();
        jobNotification.setClientType(clientType);
        // Add required entity
        JobSector jobSector = JobSectorResourceIntTest.createEntity(em);
        em.persist(jobSector);
        em.flush();
        jobNotification.setJobSector(jobSector);
        // Add required entity
        JobType jobType = JobTypeResourceIntTest.createEntity(em);
        em.persist(jobType);
        em.flush();
        jobNotification.setJobType(jobType);
        // Add required entity
        TestSkill testSkill = TestSkillResourceIntTest.createEntity(em);
        em.persist(testSkill);
        em.flush();
        jobNotification.getTestSkills().add(testSkill);
        // Add required entity
        Language writtenExamLanguage = LanguageResourceIntTest.createEntity(em);
        em.persist(writtenExamLanguage);
        em.flush();
        jobNotification.getWrittenExamLanguages().add(writtenExamLanguage);
        // Add required entity
        SelectionProcedure selectionProcedure = SelectionProcedureResourceIntTest.createEntity(em);
        em.persist(selectionProcedure);
        em.flush();
        jobNotification.getSelectionProcedures().add(selectionProcedure);
        return jobNotification;
    }

    @Before
    public void initTest() {
        jobNotificationSearchRepository.deleteAll();
        jobNotification = createEntity(em);
    }

    @Test
    @Transactional
    public void createJobNotification() throws Exception {
        int databaseSizeBeforeCreate = jobNotificationRepository.findAll().size();

        // Create the JobNotification
        JobNotificationDTO jobNotificationDTO = jobNotificationMapper.toDto(jobNotification);
        restJobNotificationMockMvc.perform(post("/api/job-notifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobNotificationDTO)))
            .andExpect(status().isCreated());

        // Validate the JobNotification in the database
        List<JobNotification> jobNotificationList = jobNotificationRepository.findAll();
        assertThat(jobNotificationList).hasSize(databaseSizeBeforeCreate + 1);
        JobNotification testJobNotification = jobNotificationList.get(jobNotificationList.size() - 1);
        assertThat(testJobNotification.getHeadline()).isEqualTo(DEFAULT_HEADLINE);
        assertThat(testJobNotification.getNotificationDate()).isEqualTo(DEFAULT_NOTIFICATION_DATE);
        assertThat(testJobNotification.getOrganization()).isEqualTo(DEFAULT_ORGANIZATION);
        assertThat(testJobNotification.getJobRole()).isEqualTo(DEFAULT_JOB_ROLE);
        assertThat(testJobNotification.getJobLocation()).isEqualTo(DEFAULT_JOB_LOCATION);
        assertThat(testJobNotification.getTotalVacancyCount()).isEqualTo(DEFAULT_TOTAL_VACANCY_COUNT);
        assertThat(testJobNotification.getAdditionalQualification()).isEqualTo(DEFAULT_ADDITIONAL_QUALIFICATION);
        assertThat(testJobNotification.getWorkExperience()).isEqualTo(DEFAULT_WORK_EXPERIENCE);
        assertThat(testJobNotification.getSalary()).isEqualTo(DEFAULT_SALARY);
        assertThat(testJobNotification.getApplicationDeadline()).isEqualTo(DEFAULT_APPLICATION_DEADLINE);
        assertThat(testJobNotification.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testJobNotification.getNotificationLink()).isEqualTo(DEFAULT_NOTIFICATION_LINK);
        assertThat(testJobNotification.getApplicationLink()).isEqualTo(DEFAULT_APPLICATION_LINK);
        assertThat(testJobNotification.getExamLocation()).isEqualTo(DEFAULT_EXAM_LOCATION);

        // Validate the JobNotification in Elasticsearch
        JobNotification jobNotificationEs = jobNotificationSearchRepository.findOne(testJobNotification.getId());
        assertThat(jobNotificationEs).isEqualToComparingFieldByField(testJobNotification);
    }

    @Test
    @Transactional
    public void createJobNotificationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = jobNotificationRepository.findAll().size();

        // Create the JobNotification with an existing ID
        jobNotification.setId(1L);
        JobNotificationDTO jobNotificationDTO = jobNotificationMapper.toDto(jobNotification);

        // An entity with an existing ID cannot be created, so this API call must fail
        restJobNotificationMockMvc.perform(post("/api/job-notifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobNotificationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<JobNotification> jobNotificationList = jobNotificationRepository.findAll();
        assertThat(jobNotificationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkHeadlineIsRequired() throws Exception {
        int databaseSizeBeforeTest = jobNotificationRepository.findAll().size();
        // set the field null
        jobNotification.setHeadline(null);

        // Create the JobNotification, which fails.
        JobNotificationDTO jobNotificationDTO = jobNotificationMapper.toDto(jobNotification);

        restJobNotificationMockMvc.perform(post("/api/job-notifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobNotificationDTO)))
            .andExpect(status().isBadRequest());

        List<JobNotification> jobNotificationList = jobNotificationRepository.findAll();
        assertThat(jobNotificationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNotificationDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = jobNotificationRepository.findAll().size();
        // set the field null
        jobNotification.setNotificationDate(null);

        // Create the JobNotification, which fails.
        JobNotificationDTO jobNotificationDTO = jobNotificationMapper.toDto(jobNotification);

        restJobNotificationMockMvc.perform(post("/api/job-notifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobNotificationDTO)))
            .andExpect(status().isBadRequest());

        List<JobNotification> jobNotificationList = jobNotificationRepository.findAll();
        assertThat(jobNotificationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOrganizationIsRequired() throws Exception {
        int databaseSizeBeforeTest = jobNotificationRepository.findAll().size();
        // set the field null
        jobNotification.setOrganization(null);

        // Create the JobNotification, which fails.
        JobNotificationDTO jobNotificationDTO = jobNotificationMapper.toDto(jobNotification);

        restJobNotificationMockMvc.perform(post("/api/job-notifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobNotificationDTO)))
            .andExpect(status().isBadRequest());

        List<JobNotification> jobNotificationList = jobNotificationRepository.findAll();
        assertThat(jobNotificationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkJobRoleIsRequired() throws Exception {
        int databaseSizeBeforeTest = jobNotificationRepository.findAll().size();
        // set the field null
        jobNotification.setJobRole(null);

        // Create the JobNotification, which fails.
        JobNotificationDTO jobNotificationDTO = jobNotificationMapper.toDto(jobNotification);

        restJobNotificationMockMvc.perform(post("/api/job-notifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobNotificationDTO)))
            .andExpect(status().isBadRequest());

        List<JobNotification> jobNotificationList = jobNotificationRepository.findAll();
        assertThat(jobNotificationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTotalVacancyCountIsRequired() throws Exception {
        int databaseSizeBeforeTest = jobNotificationRepository.findAll().size();
        // set the field null
        jobNotification.setTotalVacancyCount(null);

        // Create the JobNotification, which fails.
        JobNotificationDTO jobNotificationDTO = jobNotificationMapper.toDto(jobNotification);

        restJobNotificationMockMvc.perform(post("/api/job-notifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobNotificationDTO)))
            .andExpect(status().isBadRequest());

        List<JobNotification> jobNotificationList = jobNotificationRepository.findAll();
        assertThat(jobNotificationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkApplicationDeadlineIsRequired() throws Exception {
        int databaseSizeBeforeTest = jobNotificationRepository.findAll().size();
        // set the field null
        jobNotification.setApplicationDeadline(null);

        // Create the JobNotification, which fails.
        JobNotificationDTO jobNotificationDTO = jobNotificationMapper.toDto(jobNotification);

        restJobNotificationMockMvc.perform(post("/api/job-notifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobNotificationDTO)))
            .andExpect(status().isBadRequest());

        List<JobNotification> jobNotificationList = jobNotificationRepository.findAll();
        assertThat(jobNotificationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = jobNotificationRepository.findAll().size();
        // set the field null
        jobNotification.setDescription(null);

        // Create the JobNotification, which fails.
        JobNotificationDTO jobNotificationDTO = jobNotificationMapper.toDto(jobNotification);

        restJobNotificationMockMvc.perform(post("/api/job-notifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobNotificationDTO)))
            .andExpect(status().isBadRequest());

        List<JobNotification> jobNotificationList = jobNotificationRepository.findAll();
        assertThat(jobNotificationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNotificationLinkIsRequired() throws Exception {
        int databaseSizeBeforeTest = jobNotificationRepository.findAll().size();
        // set the field null
        jobNotification.setNotificationLink(null);

        // Create the JobNotification, which fails.
        JobNotificationDTO jobNotificationDTO = jobNotificationMapper.toDto(jobNotification);

        restJobNotificationMockMvc.perform(post("/api/job-notifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobNotificationDTO)))
            .andExpect(status().isBadRequest());

        List<JobNotification> jobNotificationList = jobNotificationRepository.findAll();
        assertThat(jobNotificationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllJobNotifications() throws Exception {
        // Initialize the database
        jobNotificationRepository.saveAndFlush(jobNotification);

        // Get all the jobNotificationList
        restJobNotificationMockMvc.perform(get("/api/job-notifications?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jobNotification.getId().intValue())))
            .andExpect(jsonPath("$.[*].headline").value(hasItem(DEFAULT_HEADLINE.toString())))
            .andExpect(jsonPath("$.[*].notificationDate").value(hasItem(DEFAULT_NOTIFICATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].organization").value(hasItem(DEFAULT_ORGANIZATION.toString())))
            .andExpect(jsonPath("$.[*].jobRole").value(hasItem(DEFAULT_JOB_ROLE.toString())))
            .andExpect(jsonPath("$.[*].jobLocation").value(hasItem(DEFAULT_JOB_LOCATION.toString())))
            .andExpect(jsonPath("$.[*].totalVacancyCount").value(hasItem(DEFAULT_TOTAL_VACANCY_COUNT)))
            .andExpect(jsonPath("$.[*].additionalQualification").value(hasItem(DEFAULT_ADDITIONAL_QUALIFICATION.toString())))
            .andExpect(jsonPath("$.[*].workExperience").value(hasItem(DEFAULT_WORK_EXPERIENCE)))
            .andExpect(jsonPath("$.[*].salary").value(hasItem(DEFAULT_SALARY)))
            .andExpect(jsonPath("$.[*].applicationDeadline").value(hasItem(DEFAULT_APPLICATION_DEADLINE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].notificationLink").value(hasItem(DEFAULT_NOTIFICATION_LINK.toString())))
            .andExpect(jsonPath("$.[*].applicationLink").value(hasItem(DEFAULT_APPLICATION_LINK.toString())))
            .andExpect(jsonPath("$.[*].examLocation").value(hasItem(DEFAULT_EXAM_LOCATION.toString())));
    }

    @Test
    @Transactional
    public void getJobNotification() throws Exception {
        // Initialize the database
        jobNotificationRepository.saveAndFlush(jobNotification);

        // Get the jobNotification
        restJobNotificationMockMvc.perform(get("/api/job-notifications/{id}", jobNotification.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(jobNotification.getId().intValue()))
            .andExpect(jsonPath("$.headline").value(DEFAULT_HEADLINE.toString()))
            .andExpect(jsonPath("$.notificationDate").value(DEFAULT_NOTIFICATION_DATE.toString()))
            .andExpect(jsonPath("$.organization").value(DEFAULT_ORGANIZATION.toString()))
            .andExpect(jsonPath("$.jobRole").value(DEFAULT_JOB_ROLE.toString()))
            .andExpect(jsonPath("$.jobLocation").value(DEFAULT_JOB_LOCATION.toString()))
            .andExpect(jsonPath("$.totalVacancyCount").value(DEFAULT_TOTAL_VACANCY_COUNT))
            .andExpect(jsonPath("$.additionalQualification").value(DEFAULT_ADDITIONAL_QUALIFICATION.toString()))
            .andExpect(jsonPath("$.workExperience").value(DEFAULT_WORK_EXPERIENCE))
            .andExpect(jsonPath("$.salary").value(DEFAULT_SALARY))
            .andExpect(jsonPath("$.applicationDeadline").value(DEFAULT_APPLICATION_DEADLINE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.notificationLink").value(DEFAULT_NOTIFICATION_LINK.toString()))
            .andExpect(jsonPath("$.applicationLink").value(DEFAULT_APPLICATION_LINK.toString()))
            .andExpect(jsonPath("$.examLocation").value(DEFAULT_EXAM_LOCATION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingJobNotification() throws Exception {
        // Get the jobNotification
        restJobNotificationMockMvc.perform(get("/api/job-notifications/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJobNotification() throws Exception {
        // Initialize the database
        jobNotificationRepository.saveAndFlush(jobNotification);
        jobNotificationSearchRepository.save(jobNotification);
        int databaseSizeBeforeUpdate = jobNotificationRepository.findAll().size();

        // Update the jobNotification
        JobNotification updatedJobNotification = jobNotificationRepository.findOne(jobNotification.getId());
        updatedJobNotification
            .headline(UPDATED_HEADLINE)
            .notificationDate(UPDATED_NOTIFICATION_DATE)
            .organization(UPDATED_ORGANIZATION)
            .jobRole(UPDATED_JOB_ROLE)
            .jobLocation(UPDATED_JOB_LOCATION)
            .totalVacancyCount(UPDATED_TOTAL_VACANCY_COUNT)
            .additionalQualification(UPDATED_ADDITIONAL_QUALIFICATION)
            .workExperience(UPDATED_WORK_EXPERIENCE)
            .salary(UPDATED_SALARY)
            .applicationDeadline(UPDATED_APPLICATION_DEADLINE)
            .description(UPDATED_DESCRIPTION)
            .notificationLink(UPDATED_NOTIFICATION_LINK)
            .applicationLink(UPDATED_APPLICATION_LINK)
            .examLocation(UPDATED_EXAM_LOCATION);
        JobNotificationDTO jobNotificationDTO = jobNotificationMapper.toDto(updatedJobNotification);

        restJobNotificationMockMvc.perform(put("/api/job-notifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobNotificationDTO)))
            .andExpect(status().isOk());

        // Validate the JobNotification in the database
        List<JobNotification> jobNotificationList = jobNotificationRepository.findAll();
        assertThat(jobNotificationList).hasSize(databaseSizeBeforeUpdate);
        JobNotification testJobNotification = jobNotificationList.get(jobNotificationList.size() - 1);
        assertThat(testJobNotification.getHeadline()).isEqualTo(UPDATED_HEADLINE);
        assertThat(testJobNotification.getNotificationDate()).isEqualTo(UPDATED_NOTIFICATION_DATE);
        assertThat(testJobNotification.getOrganization()).isEqualTo(UPDATED_ORGANIZATION);
        assertThat(testJobNotification.getJobRole()).isEqualTo(UPDATED_JOB_ROLE);
        assertThat(testJobNotification.getJobLocation()).isEqualTo(UPDATED_JOB_LOCATION);
        assertThat(testJobNotification.getTotalVacancyCount()).isEqualTo(UPDATED_TOTAL_VACANCY_COUNT);
        assertThat(testJobNotification.getAdditionalQualification()).isEqualTo(UPDATED_ADDITIONAL_QUALIFICATION);
        assertThat(testJobNotification.getWorkExperience()).isEqualTo(UPDATED_WORK_EXPERIENCE);
        assertThat(testJobNotification.getSalary()).isEqualTo(UPDATED_SALARY);
        assertThat(testJobNotification.getApplicationDeadline()).isEqualTo(UPDATED_APPLICATION_DEADLINE);
        assertThat(testJobNotification.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testJobNotification.getNotificationLink()).isEqualTo(UPDATED_NOTIFICATION_LINK);
        assertThat(testJobNotification.getApplicationLink()).isEqualTo(UPDATED_APPLICATION_LINK);
        assertThat(testJobNotification.getExamLocation()).isEqualTo(UPDATED_EXAM_LOCATION);

        // Validate the JobNotification in Elasticsearch
        JobNotification jobNotificationEs = jobNotificationSearchRepository.findOne(testJobNotification.getId());
        assertThat(jobNotificationEs).isEqualToComparingFieldByField(testJobNotification);
    }

    @Test
    @Transactional
    public void updateNonExistingJobNotification() throws Exception {
        int databaseSizeBeforeUpdate = jobNotificationRepository.findAll().size();

        // Create the JobNotification
        JobNotificationDTO jobNotificationDTO = jobNotificationMapper.toDto(jobNotification);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restJobNotificationMockMvc.perform(put("/api/job-notifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobNotificationDTO)))
            .andExpect(status().isCreated());

        // Validate the JobNotification in the database
        List<JobNotification> jobNotificationList = jobNotificationRepository.findAll();
        assertThat(jobNotificationList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteJobNotification() throws Exception {
        // Initialize the database
        jobNotificationRepository.saveAndFlush(jobNotification);
        jobNotificationSearchRepository.save(jobNotification);
        int databaseSizeBeforeDelete = jobNotificationRepository.findAll().size();

        // Get the jobNotification
        restJobNotificationMockMvc.perform(delete("/api/job-notifications/{id}", jobNotification.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean jobNotificationExistsInEs = jobNotificationSearchRepository.exists(jobNotification.getId());
        assertThat(jobNotificationExistsInEs).isFalse();

        // Validate the database is empty
        List<JobNotification> jobNotificationList = jobNotificationRepository.findAll();
        assertThat(jobNotificationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchJobNotification() throws Exception {
        // Initialize the database
        jobNotificationRepository.saveAndFlush(jobNotification);
        jobNotificationSearchRepository.save(jobNotification);

        // Search the jobNotification
        restJobNotificationMockMvc.perform(get("/api/_search/job-notifications?query=id:" + jobNotification.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jobNotification.getId().intValue())))
            .andExpect(jsonPath("$.[*].headline").value(hasItem(DEFAULT_HEADLINE.toString())))
            .andExpect(jsonPath("$.[*].notificationDate").value(hasItem(DEFAULT_NOTIFICATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].organization").value(hasItem(DEFAULT_ORGANIZATION.toString())))
            .andExpect(jsonPath("$.[*].jobRole").value(hasItem(DEFAULT_JOB_ROLE.toString())))
            .andExpect(jsonPath("$.[*].jobLocation").value(hasItem(DEFAULT_JOB_LOCATION.toString())))
            .andExpect(jsonPath("$.[*].totalVacancyCount").value(hasItem(DEFAULT_TOTAL_VACANCY_COUNT)))
            .andExpect(jsonPath("$.[*].additionalQualification").value(hasItem(DEFAULT_ADDITIONAL_QUALIFICATION.toString())))
            .andExpect(jsonPath("$.[*].workExperience").value(hasItem(DEFAULT_WORK_EXPERIENCE)))
            .andExpect(jsonPath("$.[*].salary").value(hasItem(DEFAULT_SALARY)))
            .andExpect(jsonPath("$.[*].applicationDeadline").value(hasItem(DEFAULT_APPLICATION_DEADLINE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].notificationLink").value(hasItem(DEFAULT_NOTIFICATION_LINK.toString())))
            .andExpect(jsonPath("$.[*].applicationLink").value(hasItem(DEFAULT_APPLICATION_LINK.toString())))
            .andExpect(jsonPath("$.[*].examLocation").value(hasItem(DEFAULT_EXAM_LOCATION.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(JobNotification.class);
        JobNotification jobNotification1 = new JobNotification();
        jobNotification1.setId(1L);
        JobNotification jobNotification2 = new JobNotification();
        jobNotification2.setId(jobNotification1.getId());
        assertThat(jobNotification1).isEqualTo(jobNotification2);
        jobNotification2.setId(2L);
        assertThat(jobNotification1).isNotEqualTo(jobNotification2);
        jobNotification1.setId(null);
        assertThat(jobNotification1).isNotEqualTo(jobNotification2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(JobNotificationDTO.class);
        JobNotificationDTO jobNotificationDTO1 = new JobNotificationDTO();
        jobNotificationDTO1.setId(1L);
        JobNotificationDTO jobNotificationDTO2 = new JobNotificationDTO();
        assertThat(jobNotificationDTO1).isNotEqualTo(jobNotificationDTO2);
        jobNotificationDTO2.setId(jobNotificationDTO1.getId());
        assertThat(jobNotificationDTO1).isEqualTo(jobNotificationDTO2);
        jobNotificationDTO2.setId(2L);
        assertThat(jobNotificationDTO1).isNotEqualTo(jobNotificationDTO2);
        jobNotificationDTO1.setId(null);
        assertThat(jobNotificationDTO1).isNotEqualTo(jobNotificationDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(jobNotificationMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(jobNotificationMapper.fromId(null)).isNull();
    }
}
