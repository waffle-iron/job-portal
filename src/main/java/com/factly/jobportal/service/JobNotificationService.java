package com.factly.jobportal.service;

import com.factly.jobportal.service.dto.JobNotificationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing JobNotification.
 */
public interface JobNotificationService {

    /**
     * Save a jobNotification.
     *
     * @param jobNotificationDTO the entity to save
     * @return the persisted entity
     */
    JobNotificationDTO save(JobNotificationDTO jobNotificationDTO);

    /**
     *  Get all the jobNotifications.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<JobNotificationDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" jobNotification.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    JobNotificationDTO findOne(Long id);

    /**
     *  Delete the "id" jobNotification.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the jobNotification corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<JobNotificationDTO> search(String query, Pageable pageable);
}