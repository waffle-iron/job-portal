package com.factly.jobportal.service.impl;

import com.factly.jobportal.service.EducationService;
import com.factly.jobportal.domain.Education;
import com.factly.jobportal.repository.EducationRepository;
import com.factly.jobportal.repository.search.EducationSearchRepository;
import com.factly.jobportal.service.dto.EducationDTO;
import com.factly.jobportal.service.mapper.EducationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Education.
 */
@Service
@Transactional
public class EducationServiceImpl implements EducationService{

    private final Logger log = LoggerFactory.getLogger(EducationServiceImpl.class);

    private final EducationRepository educationRepository;

    private final EducationMapper educationMapper;

    private final EducationSearchRepository educationSearchRepository;

    public EducationServiceImpl(EducationRepository educationRepository, EducationMapper educationMapper, EducationSearchRepository educationSearchRepository) {
        this.educationRepository = educationRepository;
        this.educationMapper = educationMapper;
        this.educationSearchRepository = educationSearchRepository;
    }

    /**
     * Save a education.
     *
     * @param educationDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public EducationDTO save(EducationDTO educationDTO) {
        log.debug("Request to save Education : {}", educationDTO);
        Education education = educationMapper.toEntity(educationDTO);
        education = educationRepository.save(education);
        EducationDTO result = educationMapper.toDto(education);
        educationSearchRepository.save(education);
        return result;
    }

    /**
     *  Get all the educations.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EducationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Educations");
        return educationRepository.findAll(pageable)
            .map(educationMapper::toDto);
    }

    /**
     *  Get one education by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public EducationDTO findOne(Long id) {
        log.debug("Request to get Education : {}", id);
        Education education = educationRepository.findOne(id);
        return educationMapper.toDto(education);
    }

    /**
     *  Delete the  education by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Education : {}", id);
        educationRepository.delete(id);
        educationSearchRepository.delete(id);
    }

    /**
     * Search for the education corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EducationDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Educations for query {}", query);
        Page<Education> result = educationSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(educationMapper::toDto);
    }
}
