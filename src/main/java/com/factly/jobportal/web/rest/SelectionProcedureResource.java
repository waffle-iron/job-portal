package com.factly.jobportal.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.factly.jobportal.service.SelectionProcedureService;
import com.factly.jobportal.web.rest.util.HeaderUtil;
import com.factly.jobportal.web.rest.util.PaginationUtil;
import com.factly.jobportal.service.dto.SelectionProcedureDTO;
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
 * REST controller for managing SelectionProcedure.
 */
@RestController
@RequestMapping("/api")
public class SelectionProcedureResource {

    private final Logger log = LoggerFactory.getLogger(SelectionProcedureResource.class);

    private static final String ENTITY_NAME = "selectionProcedure";

    private final SelectionProcedureService selectionProcedureService;

    public SelectionProcedureResource(SelectionProcedureService selectionProcedureService) {
        this.selectionProcedureService = selectionProcedureService;
    }

    /**
     * POST  /selection-procedures : Create a new selectionProcedure.
     *
     * @param selectionProcedureDTO the selectionProcedureDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new selectionProcedureDTO, or with status 400 (Bad Request) if the selectionProcedure has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/selection-procedures")
    @Timed
    public ResponseEntity<SelectionProcedureDTO> createSelectionProcedure(@Valid @RequestBody SelectionProcedureDTO selectionProcedureDTO) throws URISyntaxException {
        log.debug("REST request to save SelectionProcedure : {}", selectionProcedureDTO);
        if (selectionProcedureDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new selectionProcedure cannot already have an ID")).body(null);
        }
        SelectionProcedureDTO result = selectionProcedureService.save(selectionProcedureDTO);
        return ResponseEntity.created(new URI("/api/selection-procedures/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /selection-procedures : Updates an existing selectionProcedure.
     *
     * @param selectionProcedureDTO the selectionProcedureDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated selectionProcedureDTO,
     * or with status 400 (Bad Request) if the selectionProcedureDTO is not valid,
     * or with status 500 (Internal Server Error) if the selectionProcedureDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/selection-procedures")
    @Timed
    public ResponseEntity<SelectionProcedureDTO> updateSelectionProcedure(@Valid @RequestBody SelectionProcedureDTO selectionProcedureDTO) throws URISyntaxException {
        log.debug("REST request to update SelectionProcedure : {}", selectionProcedureDTO);
        if (selectionProcedureDTO.getId() == null) {
            return createSelectionProcedure(selectionProcedureDTO);
        }
        SelectionProcedureDTO result = selectionProcedureService.save(selectionProcedureDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, selectionProcedureDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /selection-procedures : get all the selectionProcedures.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of selectionProcedures in body
     */
    @GetMapping("/selection-procedures")
    @Timed
    public ResponseEntity<List<SelectionProcedureDTO>> getAllSelectionProcedures(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of SelectionProcedures");
        Page<SelectionProcedureDTO> page = selectionProcedureService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/selection-procedures");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /selection-procedures/:id : get the "id" selectionProcedure.
     *
     * @param id the id of the selectionProcedureDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the selectionProcedureDTO, or with status 404 (Not Found)
     */
    @GetMapping("/selection-procedures/{id}")
    @Timed
    public ResponseEntity<SelectionProcedureDTO> getSelectionProcedure(@PathVariable Long id) {
        log.debug("REST request to get SelectionProcedure : {}", id);
        SelectionProcedureDTO selectionProcedureDTO = selectionProcedureService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(selectionProcedureDTO));
    }

    /**
     * DELETE  /selection-procedures/:id : delete the "id" selectionProcedure.
     *
     * @param id the id of the selectionProcedureDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/selection-procedures/{id}")
    @Timed
    public ResponseEntity<Void> deleteSelectionProcedure(@PathVariable Long id) {
        log.debug("REST request to delete SelectionProcedure : {}", id);
        selectionProcedureService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/selection-procedures?query=:query : search for the selectionProcedure corresponding
     * to the query.
     *
     * @param query the query of the selectionProcedure search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/selection-procedures")
    @Timed
    public ResponseEntity<List<SelectionProcedureDTO>> searchSelectionProcedures(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of SelectionProcedures for query {}", query);
        Page<SelectionProcedureDTO> page = selectionProcedureService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/selection-procedures");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
