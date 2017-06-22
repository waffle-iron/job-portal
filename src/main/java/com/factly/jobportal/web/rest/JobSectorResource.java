package com.factly.jobportal.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.factly.jobportal.service.JobSectorService;
import com.factly.jobportal.web.rest.util.HeaderUtil;
import com.factly.jobportal.web.rest.util.PaginationUtil;
import com.factly.jobportal.service.dto.JobSectorDTO;
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
 * REST controller for managing JobSector.
 */
@RestController
@RequestMapping("/api")
public class JobSectorResource {

    private final Logger log = LoggerFactory.getLogger(JobSectorResource.class);

    private static final String ENTITY_NAME = "jobSector";

    private final JobSectorService jobSectorService;

    public JobSectorResource(JobSectorService jobSectorService) {
        this.jobSectorService = jobSectorService;
    }

    /**
     * POST  /job-sectors : Create a new jobSector.
     *
     * @param jobSectorDTO the jobSectorDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new jobSectorDTO, or with status 400 (Bad Request) if the jobSector has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/job-sectors")
    @Timed
    public ResponseEntity<JobSectorDTO> createJobSector(@Valid @RequestBody JobSectorDTO jobSectorDTO) throws URISyntaxException {
        log.debug("REST request to save JobSector : {}", jobSectorDTO);
        if (jobSectorDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new jobSector cannot already have an ID")).body(null);
        }
        JobSectorDTO result = jobSectorService.save(jobSectorDTO);
        return ResponseEntity.created(new URI("/api/job-sectors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /job-sectors : Updates an existing jobSector.
     *
     * @param jobSectorDTO the jobSectorDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated jobSectorDTO,
     * or with status 400 (Bad Request) if the jobSectorDTO is not valid,
     * or with status 500 (Internal Server Error) if the jobSectorDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/job-sectors")
    @Timed
    public ResponseEntity<JobSectorDTO> updateJobSector(@Valid @RequestBody JobSectorDTO jobSectorDTO) throws URISyntaxException {
        log.debug("REST request to update JobSector : {}", jobSectorDTO);
        if (jobSectorDTO.getId() == null) {
            return createJobSector(jobSectorDTO);
        }
        JobSectorDTO result = jobSectorService.save(jobSectorDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, jobSectorDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /job-sectors : get all the jobSectors.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of jobSectors in body
     */
    @GetMapping("/job-sectors")
    @Timed
    public ResponseEntity<List<JobSectorDTO>> getAllJobSectors(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of JobSectors");
        Page<JobSectorDTO> page = jobSectorService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/job-sectors");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /job-sectors/:id : get the "id" jobSector.
     *
     * @param id the id of the jobSectorDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the jobSectorDTO, or with status 404 (Not Found)
     */
    @GetMapping("/job-sectors/{id}")
    @Timed
    public ResponseEntity<JobSectorDTO> getJobSector(@PathVariable Long id) {
        log.debug("REST request to get JobSector : {}", id);
        JobSectorDTO jobSectorDTO = jobSectorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(jobSectorDTO));
    }

    /**
     * DELETE  /job-sectors/:id : delete the "id" jobSector.
     *
     * @param id the id of the jobSectorDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/job-sectors/{id}")
    @Timed
    public ResponseEntity<Void> deleteJobSector(@PathVariable Long id) {
        log.debug("REST request to delete JobSector : {}", id);
        jobSectorService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/job-sectors?query=:query : search for the jobSector corresponding
     * to the query.
     *
     * @param query the query of the jobSector search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/job-sectors")
    @Timed
    public ResponseEntity<List<JobSectorDTO>> searchJobSectors(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of JobSectors for query {}", query);
        Page<JobSectorDTO> page = jobSectorService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/job-sectors");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
