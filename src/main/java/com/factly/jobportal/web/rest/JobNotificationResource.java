package com.factly.jobportal.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.factly.jobportal.service.JobNotificationService;
import com.factly.jobportal.service.dto.JobListDTO;
import com.factly.jobportal.web.rest.util.HeaderUtil;
import com.factly.jobportal.web.rest.util.PaginationUtil;
import com.factly.jobportal.service.dto.JobNotificationDTO;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing JobNotification.
 */
@RestController
@RequestMapping("/api")
public class JobNotificationResource {

    private final Logger log = LoggerFactory.getLogger(JobNotificationResource.class);

    private static final String ENTITY_NAME = "jobNotification";

    private final JobNotificationService jobNotificationService;

    public JobNotificationResource(JobNotificationService jobNotificationService) {
        this.jobNotificationService = jobNotificationService;
    }

    /**
     * POST  /job-notifications : Create a new jobNotification.
     *
     * @param jobNotificationDTO the jobNotificationDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new jobNotificationDTO, or with status 400 (Bad Request) if the jobNotification has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/job-notifications")
    @Timed
    public ResponseEntity<JobNotificationDTO> createJobNotification(@Valid @RequestBody JobNotificationDTO jobNotificationDTO) throws URISyntaxException {
        log.debug("REST request to save JobNotification : {}", jobNotificationDTO);
        if (jobNotificationDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new jobNotification cannot already have an ID")).body(null);
        }
        JobNotificationDTO result = jobNotificationService.save(jobNotificationDTO);
        return ResponseEntity.created(new URI("/api/job-notifications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /job-notifications : Updates an existing jobNotification.
     *
     * @param jobNotificationDTO the jobNotificationDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated jobNotificationDTO,
     * or with status 400 (Bad Request) if the jobNotificationDTO is not valid,
     * or with status 500 (Internal Server Error) if the jobNotificationDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/job-notifications")
    @Timed
    public ResponseEntity<JobNotificationDTO> updateJobNotification(@Valid @RequestBody JobNotificationDTO jobNotificationDTO) throws URISyntaxException {
        log.debug("REST request to update JobNotification : {}", jobNotificationDTO);
        if (jobNotificationDTO.getId() == null) {
            return createJobNotification(jobNotificationDTO);
        }
        JobNotificationDTO result = jobNotificationService.save(jobNotificationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, jobNotificationDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /job-notifications : get all the jobNotifications.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of jobNotifications in body
     */
    @GetMapping("/job-notifications")
    @Timed
    public ResponseEntity<List<JobNotificationDTO>> getAllJobNotifications(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of JobNotifications");
        Page<JobNotificationDTO> page = jobNotificationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/job-notifications");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /job-notifications/:id : get the "id" jobNotification.
     *
     * @param id the id of the jobNotificationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the jobNotificationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/job-notifications/{id}")
    @Timed
    public ResponseEntity<JobNotificationDTO> getJobNotification(@PathVariable Long id) {
        log.debug("REST request to get JobNotification : {}", id);
        JobNotificationDTO jobNotificationDTO = jobNotificationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(jobNotificationDTO));
    }

    /**
     * DELETE  /job-notifications/:id : delete the "id" jobNotification.
     *
     * @param id the id of the jobNotificationDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/job-notifications/{id}")
    @Timed
    public ResponseEntity<Void> deleteJobNotification(@PathVariable Long id) {
        log.debug("REST request to delete JobNotification : {}", id);
        jobNotificationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/job-notifications?query=:query : search for the jobNotification corresponding
     * to the query.
     *
     * @param query the query of the jobNotification search
     * @param pageable the pagination information
     * @return the result of the search
     */
//    @GetMapping("/_search/job-notifications")
//    @Timed
//    public ResponseEntity<List<JobNotificationDTO>> searchJobNotifications(@RequestParam String query, @ApiParam Pageable pageable) {
//        log.debug("REST request to search for a page of JobNotifications for query {}", query);
//        JobListDTO jobListDTO = jobNotificationService.search(query, pageable);
//        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/job-notifications");
//        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
//    }

}
