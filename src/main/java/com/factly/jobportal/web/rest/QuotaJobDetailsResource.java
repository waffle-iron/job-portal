package com.factly.jobportal.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.factly.jobportal.service.QuotaJobDetailsService;
import com.factly.jobportal.web.rest.util.HeaderUtil;
import com.factly.jobportal.web.rest.util.PaginationUtil;
import com.factly.jobportal.service.dto.QuotaJobDetailsDTO;
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
 * REST controller for managing QuotaJobDetails.
 */
@RestController
@RequestMapping("/api")
public class QuotaJobDetailsResource {

    private final Logger log = LoggerFactory.getLogger(QuotaJobDetailsResource.class);

    private static final String ENTITY_NAME = "quotaJobDetails";

    private final QuotaJobDetailsService quotaJobDetailsService;

    public QuotaJobDetailsResource(QuotaJobDetailsService quotaJobDetailsService) {
        this.quotaJobDetailsService = quotaJobDetailsService;
    }

    /**
     * POST  /quota-job-details : Create a new quotaJobDetails.
     *
     * @param quotaJobDetailsDTO the quotaJobDetailsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new quotaJobDetailsDTO, or with status 400 (Bad Request) if the quotaJobDetails has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/quota-job-details")
    @Timed
    public ResponseEntity<QuotaJobDetailsDTO> createQuotaJobDetails(@Valid @RequestBody QuotaJobDetailsDTO quotaJobDetailsDTO) throws URISyntaxException {
        log.debug("REST request to save QuotaJobDetails : {}", quotaJobDetailsDTO);
        if (quotaJobDetailsDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new quotaJobDetails cannot already have an ID")).body(null);
        }
        QuotaJobDetailsDTO result = quotaJobDetailsService.save(quotaJobDetailsDTO);
        return ResponseEntity.created(new URI("/api/quota-job-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /quota-job-details : Updates an existing quotaJobDetails.
     *
     * @param quotaJobDetailsDTO the quotaJobDetailsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated quotaJobDetailsDTO,
     * or with status 400 (Bad Request) if the quotaJobDetailsDTO is not valid,
     * or with status 500 (Internal Server Error) if the quotaJobDetailsDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/quota-job-details")
    @Timed
    public ResponseEntity<QuotaJobDetailsDTO> updateQuotaJobDetails(@Valid @RequestBody QuotaJobDetailsDTO quotaJobDetailsDTO) throws URISyntaxException {
        log.debug("REST request to update QuotaJobDetails : {}", quotaJobDetailsDTO);
        if (quotaJobDetailsDTO.getId() == null) {
            return createQuotaJobDetails(quotaJobDetailsDTO);
        }
        QuotaJobDetailsDTO result = quotaJobDetailsService.save(quotaJobDetailsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, quotaJobDetailsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /quota-job-details : get all the quotaJobDetails.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of quotaJobDetails in body
     */
    @GetMapping("/quota-job-details")
    @Timed
    public ResponseEntity<List<QuotaJobDetailsDTO>> getAllQuotaJobDetails(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of QuotaJobDetails");
        Page<QuotaJobDetailsDTO> page = quotaJobDetailsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/quota-job-details");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /quota-job-details/:id : get the "id" quotaJobDetails.
     *
     * @param id the id of the quotaJobDetailsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the quotaJobDetailsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/quota-job-details/{id}")
    @Timed
    public ResponseEntity<QuotaJobDetailsDTO> getQuotaJobDetails(@PathVariable Long id) {
        log.debug("REST request to get QuotaJobDetails : {}", id);
        QuotaJobDetailsDTO quotaJobDetailsDTO = quotaJobDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(quotaJobDetailsDTO));
    }

    /**
     * DELETE  /quota-job-details/:id : delete the "id" quotaJobDetails.
     *
     * @param id the id of the quotaJobDetailsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/quota-job-details/{id}")
    @Timed
    public ResponseEntity<Void> deleteQuotaJobDetails(@PathVariable Long id) {
        log.debug("REST request to delete QuotaJobDetails : {}", id);
        quotaJobDetailsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/quota-job-details?query=:query : search for the quotaJobDetails corresponding
     * to the query.
     *
     * @param query the query of the quotaJobDetails search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/quota-job-details")
    @Timed
    public ResponseEntity<List<QuotaJobDetailsDTO>> searchQuotaJobDetails(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of QuotaJobDetails for query {}", query);
        Page<QuotaJobDetailsDTO> page = quotaJobDetailsService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/quota-job-details");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
