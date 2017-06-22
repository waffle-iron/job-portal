package com.factly.jobportal.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.factly.jobportal.service.EductationService;
import com.factly.jobportal.web.rest.util.HeaderUtil;
import com.factly.jobportal.web.rest.util.PaginationUtil;
import com.factly.jobportal.service.dto.EductationDTO;
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
 * REST controller for managing Eductation.
 */
@RestController
@RequestMapping("/api")
public class EductationResource {

    private final Logger log = LoggerFactory.getLogger(EductationResource.class);

    private static final String ENTITY_NAME = "eductation";

    private final EductationService eductationService;

    public EductationResource(EductationService eductationService) {
        this.eductationService = eductationService;
    }

    /**
     * POST  /eductations : Create a new eductation.
     *
     * @param eductationDTO the eductationDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new eductationDTO, or with status 400 (Bad Request) if the eductation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/eductations")
    @Timed
    public ResponseEntity<EductationDTO> createEductation(@Valid @RequestBody EductationDTO eductationDTO) throws URISyntaxException {
        log.debug("REST request to save Eductation : {}", eductationDTO);
        if (eductationDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new eductation cannot already have an ID")).body(null);
        }
        EductationDTO result = eductationService.save(eductationDTO);
        return ResponseEntity.created(new URI("/api/eductations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /eductations : Updates an existing eductation.
     *
     * @param eductationDTO the eductationDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated eductationDTO,
     * or with status 400 (Bad Request) if the eductationDTO is not valid,
     * or with status 500 (Internal Server Error) if the eductationDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/eductations")
    @Timed
    public ResponseEntity<EductationDTO> updateEductation(@Valid @RequestBody EductationDTO eductationDTO) throws URISyntaxException {
        log.debug("REST request to update Eductation : {}", eductationDTO);
        if (eductationDTO.getId() == null) {
            return createEductation(eductationDTO);
        }
        EductationDTO result = eductationService.save(eductationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, eductationDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /eductations : get all the eductations.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of eductations in body
     */
    @GetMapping("/eductations")
    @Timed
    public ResponseEntity<List<EductationDTO>> getAllEductations(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Eductations");
        Page<EductationDTO> page = eductationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/eductations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /eductations/:id : get the "id" eductation.
     *
     * @param id the id of the eductationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the eductationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/eductations/{id}")
    @Timed
    public ResponseEntity<EductationDTO> getEductation(@PathVariable Long id) {
        log.debug("REST request to get Eductation : {}", id);
        EductationDTO eductationDTO = eductationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(eductationDTO));
    }

    /**
     * DELETE  /eductations/:id : delete the "id" eductation.
     *
     * @param id the id of the eductationDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/eductations/{id}")
    @Timed
    public ResponseEntity<Void> deleteEductation(@PathVariable Long id) {
        log.debug("REST request to delete Eductation : {}", id);
        eductationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/eductations?query=:query : search for the eductation corresponding
     * to the query.
     *
     * @param query the query of the eductation search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/eductations")
    @Timed
    public ResponseEntity<List<EductationDTO>> searchEductations(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of Eductations for query {}", query);
        Page<EductationDTO> page = eductationService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/eductations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
