package com.factly.jobportal.web.rest;

import com.factly.jobportal.JobportalApp;

import com.factly.jobportal.domain.Eductation;
import com.factly.jobportal.repository.EductationRepository;
import com.factly.jobportal.service.EductationService;
import com.factly.jobportal.repository.search.EductationSearchRepository;
import com.factly.jobportal.service.dto.EductationDTO;
import com.factly.jobportal.service.mapper.EductationMapper;
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
 * Test class for the EductationResource REST controller.
 *
 * @see EductationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JobportalApp.class)
public class EductationResourceIntTest {

    @Autowired
    private EductationRepository eductationRepository;

    @Autowired
    private EductationMapper eductationMapper;

    @Autowired
    private EductationService eductationService;

    @Autowired
    private EductationSearchRepository eductationSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEductationMockMvc;

    private Eductation eductation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EductationResource eductationResource = new EductationResource(eductationService);
        this.restEductationMockMvc = MockMvcBuilders.standaloneSetup(eductationResource)
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
    public static Eductation createEntity(EntityManager em) {
        Eductation eductation = new Eductation();
        return eductation;
    }

    @Before
    public void initTest() {
        eductationSearchRepository.deleteAll();
        eductation = createEntity(em);
    }

    @Test
    @Transactional
    public void createEductation() throws Exception {
        int databaseSizeBeforeCreate = eductationRepository.findAll().size();

        // Create the Eductation
        EductationDTO eductationDTO = eductationMapper.toDto(eductation);
        restEductationMockMvc.perform(post("/api/eductations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eductationDTO)))
            .andExpect(status().isCreated());

        // Validate the Eductation in the database
        List<Eductation> eductationList = eductationRepository.findAll();
        assertThat(eductationList).hasSize(databaseSizeBeforeCreate + 1);
        Eductation testEductation = eductationList.get(eductationList.size() - 1);

        // Validate the Eductation in Elasticsearch
        Eductation eductationEs = eductationSearchRepository.findOne(testEductation.getId());
        assertThat(eductationEs).isEqualToComparingFieldByField(testEductation);
    }

    @Test
    @Transactional
    public void createEductationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = eductationRepository.findAll().size();

        // Create the Eductation with an existing ID
        eductation.setId(1L);
        EductationDTO eductationDTO = eductationMapper.toDto(eductation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEductationMockMvc.perform(post("/api/eductations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eductationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Eductation> eductationList = eductationRepository.findAll();
        assertThat(eductationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllEductations() throws Exception {
        // Initialize the database
        eductationRepository.saveAndFlush(eductation);

        // Get all the eductationList
        restEductationMockMvc.perform(get("/api/eductations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eductation.getId().intValue())));
    }

    @Test
    @Transactional
    public void getEductation() throws Exception {
        // Initialize the database
        eductationRepository.saveAndFlush(eductation);

        // Get the eductation
        restEductationMockMvc.perform(get("/api/eductations/{id}", eductation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(eductation.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingEductation() throws Exception {
        // Get the eductation
        restEductationMockMvc.perform(get("/api/eductations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEductation() throws Exception {
        // Initialize the database
        eductationRepository.saveAndFlush(eductation);
        eductationSearchRepository.save(eductation);
        int databaseSizeBeforeUpdate = eductationRepository.findAll().size();

        // Update the eductation
        Eductation updatedEductation = eductationRepository.findOne(eductation.getId());
        EductationDTO eductationDTO = eductationMapper.toDto(updatedEductation);

        restEductationMockMvc.perform(put("/api/eductations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eductationDTO)))
            .andExpect(status().isOk());

        // Validate the Eductation in the database
        List<Eductation> eductationList = eductationRepository.findAll();
        assertThat(eductationList).hasSize(databaseSizeBeforeUpdate);
        Eductation testEductation = eductationList.get(eductationList.size() - 1);

        // Validate the Eductation in Elasticsearch
        Eductation eductationEs = eductationSearchRepository.findOne(testEductation.getId());
        assertThat(eductationEs).isEqualToComparingFieldByField(testEductation);
    }

    @Test
    @Transactional
    public void updateNonExistingEductation() throws Exception {
        int databaseSizeBeforeUpdate = eductationRepository.findAll().size();

        // Create the Eductation
        EductationDTO eductationDTO = eductationMapper.toDto(eductation);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEductationMockMvc.perform(put("/api/eductations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eductationDTO)))
            .andExpect(status().isCreated());

        // Validate the Eductation in the database
        List<Eductation> eductationList = eductationRepository.findAll();
        assertThat(eductationList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEductation() throws Exception {
        // Initialize the database
        eductationRepository.saveAndFlush(eductation);
        eductationSearchRepository.save(eductation);
        int databaseSizeBeforeDelete = eductationRepository.findAll().size();

        // Get the eductation
        restEductationMockMvc.perform(delete("/api/eductations/{id}", eductation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean eductationExistsInEs = eductationSearchRepository.exists(eductation.getId());
        assertThat(eductationExistsInEs).isFalse();

        // Validate the database is empty
        List<Eductation> eductationList = eductationRepository.findAll();
        assertThat(eductationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchEductation() throws Exception {
        // Initialize the database
        eductationRepository.saveAndFlush(eductation);
        eductationSearchRepository.save(eductation);

        // Search the eductation
        restEductationMockMvc.perform(get("/api/_search/eductations?query=id:" + eductation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eductation.getId().intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Eductation.class);
        Eductation eductation1 = new Eductation();
        eductation1.setId(1L);
        Eductation eductation2 = new Eductation();
        eductation2.setId(eductation1.getId());
        assertThat(eductation1).isEqualTo(eductation2);
        eductation2.setId(2L);
        assertThat(eductation1).isNotEqualTo(eductation2);
        eductation1.setId(null);
        assertThat(eductation1).isNotEqualTo(eductation2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EductationDTO.class);
        EductationDTO eductationDTO1 = new EductationDTO();
        eductationDTO1.setId(1L);
        EductationDTO eductationDTO2 = new EductationDTO();
        assertThat(eductationDTO1).isNotEqualTo(eductationDTO2);
        eductationDTO2.setId(eductationDTO1.getId());
        assertThat(eductationDTO1).isEqualTo(eductationDTO2);
        eductationDTO2.setId(2L);
        assertThat(eductationDTO1).isNotEqualTo(eductationDTO2);
        eductationDTO1.setId(null);
        assertThat(eductationDTO1).isNotEqualTo(eductationDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(eductationMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(eductationMapper.fromId(null)).isNull();
    }
}
