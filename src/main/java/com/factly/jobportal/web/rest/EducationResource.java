package com.factly.jobportal.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.factly.jobportal.service.EducationService;
import com.factly.jobportal.web.rest.util.HeaderUtil;
import com.factly.jobportal.web.rest.util.PaginationUtil;
import com.factly.jobportal.service.dto.EducationDTO;
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
 * REST controller for managing Education.
 */
@RestController
@RequestMapping("/api")
public class EducationResource {

    private final Logger log = LoggerFactory.getLogger(EducationResource.class);

    private static final String ENTITY_NAME = "education";

    private final EducationService educationService;

    public EducationResource(EducationService educationService) {
        this.educationService = educationService;
    }

    /**
     * POST  /educations : Create a new education.
     *
     * @param educationDTO the educationDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new educationDTO, or with status 400 (Bad Request) if the education has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/educations")
    @Timed
    public ResponseEntity<EducationDTO> createEducation(@Valid @RequestBody EducationDTO educationDTO) throws URISyntaxException {
        log.debug("REST request to save Education : {}", educationDTO);
        if (educationDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new education cannot already have an ID")).body(null);
        }
        EducationDTO result = educationService.save(educationDTO);
        return ResponseEntity.created(new URI("/api/educations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /educations : Updates an existing education.
     *
     * @param educationDTO the educationDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated educationDTO,
     * or with status 400 (Bad Request) if the educationDTO is not valid,
     * or with status 500 (Internal Server Error) if the educationDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/educations")
    @Timed
    public ResponseEntity<EducationDTO> updateEducation(@Valid @RequestBody EducationDTO educationDTO) throws URISyntaxException {
        log.debug("REST request to update Education : {}", educationDTO);
        if (educationDTO.getId() == null) {
            return createEducation(educationDTO);
        }
        EducationDTO result = educationService.save(educationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, educationDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /educations : get all the educations.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of educations in body
     */
    @GetMapping("/educations")
    @Timed
    public ResponseEntity<List<EducationDTO>> getAllEducations(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Educations");
        Page<EducationDTO> page = educationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/educations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /educations/:id : get the "id" education.
     *
     * @param id the id of the educationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the educationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/educations/{id}")
    @Timed
    public ResponseEntity<EducationDTO> getEducation(@PathVariable Long id) {
        log.debug("REST request to get Education : {}", id);
        EducationDTO educationDTO = educationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(educationDTO));
    }

    /**
     * DELETE  /educations/:id : delete the "id" education.
     *
     * @param id the id of the educationDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/educations/{id}")
    @Timed
    public ResponseEntity<Void> deleteEducation(@PathVariable Long id) {
        log.debug("REST request to delete Education : {}", id);
        educationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/educations?query=:query : search for the education corresponding
     * to the query.
     *
     * @param query the query of the education search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/educations")
    @Timed
    public ResponseEntity<List<EducationDTO>> searchEducations(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of Educations for query {}", query);
        Page<EducationDTO> page = educationService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/educations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
