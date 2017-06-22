package com.factly.jobportal.web.rest;

import com.factly.jobportal.JobportalApp;

import com.factly.jobportal.domain.ClientType;
import com.factly.jobportal.repository.ClientTypeRepository;
import com.factly.jobportal.service.ClientTypeService;
import com.factly.jobportal.repository.search.ClientTypeSearchRepository;
import com.factly.jobportal.service.dto.ClientTypeDTO;
import com.factly.jobportal.service.mapper.ClientTypeMapper;
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
 * Test class for the ClientTypeResource REST controller.
 *
 * @see ClientTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JobportalApp.class)
public class ClientTypeResourceIntTest {

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    @Autowired
    private ClientTypeRepository clientTypeRepository;

    @Autowired
    private ClientTypeMapper clientTypeMapper;

    @Autowired
    private ClientTypeService clientTypeService;

    @Autowired
    private ClientTypeSearchRepository clientTypeSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restClientTypeMockMvc;

    private ClientType clientType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ClientTypeResource clientTypeResource = new ClientTypeResource(clientTypeService);
        this.restClientTypeMockMvc = MockMvcBuilders.standaloneSetup(clientTypeResource)
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
    public static ClientType createEntity(EntityManager em) {
        ClientType clientType = new ClientType()
            .type(DEFAULT_TYPE);
        return clientType;
    }

    @Before
    public void initTest() {
        clientTypeSearchRepository.deleteAll();
        clientType = createEntity(em);
    }

    @Test
    @Transactional
    public void createClientType() throws Exception {
        int databaseSizeBeforeCreate = clientTypeRepository.findAll().size();

        // Create the ClientType
        ClientTypeDTO clientTypeDTO = clientTypeMapper.toDto(clientType);
        restClientTypeMockMvc.perform(post("/api/client-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the ClientType in the database
        List<ClientType> clientTypeList = clientTypeRepository.findAll();
        assertThat(clientTypeList).hasSize(databaseSizeBeforeCreate + 1);
        ClientType testClientType = clientTypeList.get(clientTypeList.size() - 1);
        assertThat(testClientType.getType()).isEqualTo(DEFAULT_TYPE);

        // Validate the ClientType in Elasticsearch
        ClientType clientTypeEs = clientTypeSearchRepository.findOne(testClientType.getId());
        assertThat(clientTypeEs).isEqualToComparingFieldByField(testClientType);
    }

    @Test
    @Transactional
    public void createClientTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = clientTypeRepository.findAll().size();

        // Create the ClientType with an existing ID
        clientType.setId(1L);
        ClientTypeDTO clientTypeDTO = clientTypeMapper.toDto(clientType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClientTypeMockMvc.perform(post("/api/client-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ClientType> clientTypeList = clientTypeRepository.findAll();
        assertThat(clientTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientTypeRepository.findAll().size();
        // set the field null
        clientType.setType(null);

        // Create the ClientType, which fails.
        ClientTypeDTO clientTypeDTO = clientTypeMapper.toDto(clientType);

        restClientTypeMockMvc.perform(post("/api/client-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientTypeDTO)))
            .andExpect(status().isBadRequest());

        List<ClientType> clientTypeList = clientTypeRepository.findAll();
        assertThat(clientTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllClientTypes() throws Exception {
        // Initialize the database
        clientTypeRepository.saveAndFlush(clientType);

        // Get all the clientTypeList
        restClientTypeMockMvc.perform(get("/api/client-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clientType.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }

    @Test
    @Transactional
    public void getClientType() throws Exception {
        // Initialize the database
        clientTypeRepository.saveAndFlush(clientType);

        // Get the clientType
        restClientTypeMockMvc.perform(get("/api/client-types/{id}", clientType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(clientType.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingClientType() throws Exception {
        // Get the clientType
        restClientTypeMockMvc.perform(get("/api/client-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClientType() throws Exception {
        // Initialize the database
        clientTypeRepository.saveAndFlush(clientType);
        clientTypeSearchRepository.save(clientType);
        int databaseSizeBeforeUpdate = clientTypeRepository.findAll().size();

        // Update the clientType
        ClientType updatedClientType = clientTypeRepository.findOne(clientType.getId());
        updatedClientType
            .type(UPDATED_TYPE);
        ClientTypeDTO clientTypeDTO = clientTypeMapper.toDto(updatedClientType);

        restClientTypeMockMvc.perform(put("/api/client-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientTypeDTO)))
            .andExpect(status().isOk());

        // Validate the ClientType in the database
        List<ClientType> clientTypeList = clientTypeRepository.findAll();
        assertThat(clientTypeList).hasSize(databaseSizeBeforeUpdate);
        ClientType testClientType = clientTypeList.get(clientTypeList.size() - 1);
        assertThat(testClientType.getType()).isEqualTo(UPDATED_TYPE);

        // Validate the ClientType in Elasticsearch
        ClientType clientTypeEs = clientTypeSearchRepository.findOne(testClientType.getId());
        assertThat(clientTypeEs).isEqualToComparingFieldByField(testClientType);
    }

    @Test
    @Transactional
    public void updateNonExistingClientType() throws Exception {
        int databaseSizeBeforeUpdate = clientTypeRepository.findAll().size();

        // Create the ClientType
        ClientTypeDTO clientTypeDTO = clientTypeMapper.toDto(clientType);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restClientTypeMockMvc.perform(put("/api/client-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the ClientType in the database
        List<ClientType> clientTypeList = clientTypeRepository.findAll();
        assertThat(clientTypeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteClientType() throws Exception {
        // Initialize the database
        clientTypeRepository.saveAndFlush(clientType);
        clientTypeSearchRepository.save(clientType);
        int databaseSizeBeforeDelete = clientTypeRepository.findAll().size();

        // Get the clientType
        restClientTypeMockMvc.perform(delete("/api/client-types/{id}", clientType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean clientTypeExistsInEs = clientTypeSearchRepository.exists(clientType.getId());
        assertThat(clientTypeExistsInEs).isFalse();

        // Validate the database is empty
        List<ClientType> clientTypeList = clientTypeRepository.findAll();
        assertThat(clientTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchClientType() throws Exception {
        // Initialize the database
        clientTypeRepository.saveAndFlush(clientType);
        clientTypeSearchRepository.save(clientType);

        // Search the clientType
        restClientTypeMockMvc.perform(get("/api/_search/client-types?query=id:" + clientType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clientType.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClientType.class);
        ClientType clientType1 = new ClientType();
        clientType1.setId(1L);
        ClientType clientType2 = new ClientType();
        clientType2.setId(clientType1.getId());
        assertThat(clientType1).isEqualTo(clientType2);
        clientType2.setId(2L);
        assertThat(clientType1).isNotEqualTo(clientType2);
        clientType1.setId(null);
        assertThat(clientType1).isNotEqualTo(clientType2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClientTypeDTO.class);
        ClientTypeDTO clientTypeDTO1 = new ClientTypeDTO();
        clientTypeDTO1.setId(1L);
        ClientTypeDTO clientTypeDTO2 = new ClientTypeDTO();
        assertThat(clientTypeDTO1).isNotEqualTo(clientTypeDTO2);
        clientTypeDTO2.setId(clientTypeDTO1.getId());
        assertThat(clientTypeDTO1).isEqualTo(clientTypeDTO2);
        clientTypeDTO2.setId(2L);
        assertThat(clientTypeDTO1).isNotEqualTo(clientTypeDTO2);
        clientTypeDTO1.setId(null);
        assertThat(clientTypeDTO1).isNotEqualTo(clientTypeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(clientTypeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(clientTypeMapper.fromId(null)).isNull();
    }
}
