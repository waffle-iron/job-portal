package com.factly.jobportal.web.rest;

import com.factly.jobportal.JobportalApp;

import com.factly.jobportal.domain.SelectionProcedure;
import com.factly.jobportal.repository.SelectionProcedureRepository;
import com.factly.jobportal.service.SelectionProcedureService;
import com.factly.jobportal.repository.search.SelectionProcedureSearchRepository;
import com.factly.jobportal.service.dto.SelectionProcedureDTO;
import com.factly.jobportal.service.mapper.SelectionProcedureMapper;
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
 * Test class for the SelectionProcedureResource REST controller.
 *
 * @see SelectionProcedureResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JobportalApp.class)
public class SelectionProcedureResourceIntTest {

    private static final String DEFAULT_PROCEDURE = "AAAAAAAAAA";
    private static final String UPDATED_PROCEDURE = "BBBBBBBBBB";

    @Autowired
    private SelectionProcedureRepository selectionProcedureRepository;

    @Autowired
    private SelectionProcedureMapper selectionProcedureMapper;

    @Autowired
    private SelectionProcedureService selectionProcedureService;

    @Autowired
    private SelectionProcedureSearchRepository selectionProcedureSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSelectionProcedureMockMvc;

    private SelectionProcedure selectionProcedure;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SelectionProcedureResource selectionProcedureResource = new SelectionProcedureResource(selectionProcedureService);
        this.restSelectionProcedureMockMvc = MockMvcBuilders.standaloneSetup(selectionProcedureResource)
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
    public static SelectionProcedure createEntity(EntityManager em) {
        SelectionProcedure selectionProcedure = new SelectionProcedure()
            .procedure(DEFAULT_PROCEDURE);
        return selectionProcedure;
    }

    @Before
    public void initTest() {
        selectionProcedureSearchRepository.deleteAll();
        selectionProcedure = createEntity(em);
    }

    @Test
    @Transactional
    public void createSelectionProcedure() throws Exception {
        int databaseSizeBeforeCreate = selectionProcedureRepository.findAll().size();

        // Create the SelectionProcedure
        SelectionProcedureDTO selectionProcedureDTO = selectionProcedureMapper.toDto(selectionProcedure);
        restSelectionProcedureMockMvc.perform(post("/api/selection-procedures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(selectionProcedureDTO)))
            .andExpect(status().isCreated());

        // Validate the SelectionProcedure in the database
        List<SelectionProcedure> selectionProcedureList = selectionProcedureRepository.findAll();
        assertThat(selectionProcedureList).hasSize(databaseSizeBeforeCreate + 1);
        SelectionProcedure testSelectionProcedure = selectionProcedureList.get(selectionProcedureList.size() - 1);
        assertThat(testSelectionProcedure.getProcedure()).isEqualTo(DEFAULT_PROCEDURE);

        // Validate the SelectionProcedure in Elasticsearch
        SelectionProcedure selectionProcedureEs = selectionProcedureSearchRepository.findOne(testSelectionProcedure.getId());
        assertThat(selectionProcedureEs).isEqualToComparingFieldByField(testSelectionProcedure);
    }

    @Test
    @Transactional
    public void createSelectionProcedureWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = selectionProcedureRepository.findAll().size();

        // Create the SelectionProcedure with an existing ID
        selectionProcedure.setId(1L);
        SelectionProcedureDTO selectionProcedureDTO = selectionProcedureMapper.toDto(selectionProcedure);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSelectionProcedureMockMvc.perform(post("/api/selection-procedures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(selectionProcedureDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<SelectionProcedure> selectionProcedureList = selectionProcedureRepository.findAll();
        assertThat(selectionProcedureList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkProcedureIsRequired() throws Exception {
        int databaseSizeBeforeTest = selectionProcedureRepository.findAll().size();
        // set the field null
        selectionProcedure.setProcedure(null);

        // Create the SelectionProcedure, which fails.
        SelectionProcedureDTO selectionProcedureDTO = selectionProcedureMapper.toDto(selectionProcedure);

        restSelectionProcedureMockMvc.perform(post("/api/selection-procedures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(selectionProcedureDTO)))
            .andExpect(status().isBadRequest());

        List<SelectionProcedure> selectionProcedureList = selectionProcedureRepository.findAll();
        assertThat(selectionProcedureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSelectionProcedures() throws Exception {
        // Initialize the database
        selectionProcedureRepository.saveAndFlush(selectionProcedure);

        // Get all the selectionProcedureList
        restSelectionProcedureMockMvc.perform(get("/api/selection-procedures?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(selectionProcedure.getId().intValue())))
            .andExpect(jsonPath("$.[*].procedure").value(hasItem(DEFAULT_PROCEDURE.toString())));
    }

    @Test
    @Transactional
    public void getSelectionProcedure() throws Exception {
        // Initialize the database
        selectionProcedureRepository.saveAndFlush(selectionProcedure);

        // Get the selectionProcedure
        restSelectionProcedureMockMvc.perform(get("/api/selection-procedures/{id}", selectionProcedure.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(selectionProcedure.getId().intValue()))
            .andExpect(jsonPath("$.procedure").value(DEFAULT_PROCEDURE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSelectionProcedure() throws Exception {
        // Get the selectionProcedure
        restSelectionProcedureMockMvc.perform(get("/api/selection-procedures/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSelectionProcedure() throws Exception {
        // Initialize the database
        selectionProcedureRepository.saveAndFlush(selectionProcedure);
        selectionProcedureSearchRepository.save(selectionProcedure);
        int databaseSizeBeforeUpdate = selectionProcedureRepository.findAll().size();

        // Update the selectionProcedure
        SelectionProcedure updatedSelectionProcedure = selectionProcedureRepository.findOne(selectionProcedure.getId());
        updatedSelectionProcedure
            .procedure(UPDATED_PROCEDURE);
        SelectionProcedureDTO selectionProcedureDTO = selectionProcedureMapper.toDto(updatedSelectionProcedure);

        restSelectionProcedureMockMvc.perform(put("/api/selection-procedures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(selectionProcedureDTO)))
            .andExpect(status().isOk());

        // Validate the SelectionProcedure in the database
        List<SelectionProcedure> selectionProcedureList = selectionProcedureRepository.findAll();
        assertThat(selectionProcedureList).hasSize(databaseSizeBeforeUpdate);
        SelectionProcedure testSelectionProcedure = selectionProcedureList.get(selectionProcedureList.size() - 1);
        assertThat(testSelectionProcedure.getProcedure()).isEqualTo(UPDATED_PROCEDURE);

        // Validate the SelectionProcedure in Elasticsearch
        SelectionProcedure selectionProcedureEs = selectionProcedureSearchRepository.findOne(testSelectionProcedure.getId());
        assertThat(selectionProcedureEs).isEqualToComparingFieldByField(testSelectionProcedure);
    }

    @Test
    @Transactional
    public void updateNonExistingSelectionProcedure() throws Exception {
        int databaseSizeBeforeUpdate = selectionProcedureRepository.findAll().size();

        // Create the SelectionProcedure
        SelectionProcedureDTO selectionProcedureDTO = selectionProcedureMapper.toDto(selectionProcedure);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSelectionProcedureMockMvc.perform(put("/api/selection-procedures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(selectionProcedureDTO)))
            .andExpect(status().isCreated());

        // Validate the SelectionProcedure in the database
        List<SelectionProcedure> selectionProcedureList = selectionProcedureRepository.findAll();
        assertThat(selectionProcedureList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSelectionProcedure() throws Exception {
        // Initialize the database
        selectionProcedureRepository.saveAndFlush(selectionProcedure);
        selectionProcedureSearchRepository.save(selectionProcedure);
        int databaseSizeBeforeDelete = selectionProcedureRepository.findAll().size();

        // Get the selectionProcedure
        restSelectionProcedureMockMvc.perform(delete("/api/selection-procedures/{id}", selectionProcedure.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean selectionProcedureExistsInEs = selectionProcedureSearchRepository.exists(selectionProcedure.getId());
        assertThat(selectionProcedureExistsInEs).isFalse();

        // Validate the database is empty
        List<SelectionProcedure> selectionProcedureList = selectionProcedureRepository.findAll();
        assertThat(selectionProcedureList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchSelectionProcedure() throws Exception {
        // Initialize the database
        selectionProcedureRepository.saveAndFlush(selectionProcedure);
        selectionProcedureSearchRepository.save(selectionProcedure);

        // Search the selectionProcedure
        restSelectionProcedureMockMvc.perform(get("/api/_search/selection-procedures?query=id:" + selectionProcedure.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(selectionProcedure.getId().intValue())))
            .andExpect(jsonPath("$.[*].procedure").value(hasItem(DEFAULT_PROCEDURE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SelectionProcedure.class);
        SelectionProcedure selectionProcedure1 = new SelectionProcedure();
        selectionProcedure1.setId(1L);
        SelectionProcedure selectionProcedure2 = new SelectionProcedure();
        selectionProcedure2.setId(selectionProcedure1.getId());
        assertThat(selectionProcedure1).isEqualTo(selectionProcedure2);
        selectionProcedure2.setId(2L);
        assertThat(selectionProcedure1).isNotEqualTo(selectionProcedure2);
        selectionProcedure1.setId(null);
        assertThat(selectionProcedure1).isNotEqualTo(selectionProcedure2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SelectionProcedureDTO.class);
        SelectionProcedureDTO selectionProcedureDTO1 = new SelectionProcedureDTO();
        selectionProcedureDTO1.setId(1L);
        SelectionProcedureDTO selectionProcedureDTO2 = new SelectionProcedureDTO();
        assertThat(selectionProcedureDTO1).isNotEqualTo(selectionProcedureDTO2);
        selectionProcedureDTO2.setId(selectionProcedureDTO1.getId());
        assertThat(selectionProcedureDTO1).isEqualTo(selectionProcedureDTO2);
        selectionProcedureDTO2.setId(2L);
        assertThat(selectionProcedureDTO1).isNotEqualTo(selectionProcedureDTO2);
        selectionProcedureDTO1.setId(null);
        assertThat(selectionProcedureDTO1).isNotEqualTo(selectionProcedureDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(selectionProcedureMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(selectionProcedureMapper.fromId(null)).isNull();
    }
}
