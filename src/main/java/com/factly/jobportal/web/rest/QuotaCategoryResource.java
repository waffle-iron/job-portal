package com.factly.jobportal.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.factly.jobportal.service.QuotaCategoryService;
import com.factly.jobportal.web.rest.util.HeaderUtil;
import com.factly.jobportal.web.rest.util.PaginationUtil;
import com.factly.jobportal.service.dto.QuotaCategoryDTO;
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
 * REST controller for managing QuotaCategory.
 */
@RestController
@RequestMapping("/api")
public class QuotaCategoryResource {

    private final Logger log = LoggerFactory.getLogger(QuotaCategoryResource.class);

    private static final String ENTITY_NAME = "quotaCategory";

    private final QuotaCategoryService quotaCategoryService;

    public QuotaCategoryResource(QuotaCategoryService quotaCategoryService) {
        this.quotaCategoryService = quotaCategoryService;
    }

    /**
     * POST  /quota-categories : Create a new quotaCategory.
     *
     * @param quotaCategoryDTO the quotaCategoryDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new quotaCategoryDTO, or with status 400 (Bad Request) if the quotaCategory has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/quota-categories")
    @Timed
    public ResponseEntity<QuotaCategoryDTO> createQuotaCategory(@Valid @RequestBody QuotaCategoryDTO quotaCategoryDTO) throws URISyntaxException {
        log.debug("REST request to save QuotaCategory : {}", quotaCategoryDTO);
        if (quotaCategoryDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new quotaCategory cannot already have an ID")).body(null);
        }
        QuotaCategoryDTO result = quotaCategoryService.save(quotaCategoryDTO);
        return ResponseEntity.created(new URI("/api/quota-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /quota-categories : Updates an existing quotaCategory.
     *
     * @param quotaCategoryDTO the quotaCategoryDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated quotaCategoryDTO,
     * or with status 400 (Bad Request) if the quotaCategoryDTO is not valid,
     * or with status 500 (Internal Server Error) if the quotaCategoryDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/quota-categories")
    @Timed
    public ResponseEntity<QuotaCategoryDTO> updateQuotaCategory(@Valid @RequestBody QuotaCategoryDTO quotaCategoryDTO) throws URISyntaxException {
        log.debug("REST request to update QuotaCategory : {}", quotaCategoryDTO);
        if (quotaCategoryDTO.getId() == null) {
            return createQuotaCategory(quotaCategoryDTO);
        }
        QuotaCategoryDTO result = quotaCategoryService.save(quotaCategoryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, quotaCategoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /quota-categories : get all the quotaCategories.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of quotaCategories in body
     */
    @GetMapping("/quota-categories")
    @Timed
    public ResponseEntity<List<QuotaCategoryDTO>> getAllQuotaCategories(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of QuotaCategories");
        Page<QuotaCategoryDTO> page = quotaCategoryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/quota-categories");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /quota-categories/:id : get the "id" quotaCategory.
     *
     * @param id the id of the quotaCategoryDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the quotaCategoryDTO, or with status 404 (Not Found)
     */
    @GetMapping("/quota-categories/{id}")
    @Timed
    public ResponseEntity<QuotaCategoryDTO> getQuotaCategory(@PathVariable Long id) {
        log.debug("REST request to get QuotaCategory : {}", id);
        QuotaCategoryDTO quotaCategoryDTO = quotaCategoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(quotaCategoryDTO));
    }

    /**
     * DELETE  /quota-categories/:id : delete the "id" quotaCategory.
     *
     * @param id the id of the quotaCategoryDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/quota-categories/{id}")
    @Timed
    public ResponseEntity<Void> deleteQuotaCategory(@PathVariable Long id) {
        log.debug("REST request to delete QuotaCategory : {}", id);
        quotaCategoryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/quota-categories?query=:query : search for the quotaCategory corresponding
     * to the query.
     *
     * @param query the query of the quotaCategory search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/quota-categories")
    @Timed
    public ResponseEntity<List<QuotaCategoryDTO>> searchQuotaCategories(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of QuotaCategories for query {}", query);
        Page<QuotaCategoryDTO> page = quotaCategoryService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/quota-categories");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
