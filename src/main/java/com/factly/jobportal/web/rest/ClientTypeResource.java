package com.factly.jobportal.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.factly.jobportal.service.ClientTypeService;
import com.factly.jobportal.web.rest.util.HeaderUtil;
import com.factly.jobportal.web.rest.util.PaginationUtil;
import com.factly.jobportal.service.dto.ClientTypeDTO;
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
 * REST controller for managing ClientType.
 */
@RestController
@RequestMapping("/api")
public class ClientTypeResource {

    private final Logger log = LoggerFactory.getLogger(ClientTypeResource.class);

    private static final String ENTITY_NAME = "clientType";

    private final ClientTypeService clientTypeService;

    public ClientTypeResource(ClientTypeService clientTypeService) {
        this.clientTypeService = clientTypeService;
    }

    /**
     * POST  /client-types : Create a new clientType.
     *
     * @param clientTypeDTO the clientTypeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new clientTypeDTO, or with status 400 (Bad Request) if the clientType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/client-types")
    @Timed
    public ResponseEntity<ClientTypeDTO> createClientType(@Valid @RequestBody ClientTypeDTO clientTypeDTO) throws URISyntaxException {
        log.debug("REST request to save ClientType : {}", clientTypeDTO);
        if (clientTypeDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new clientType cannot already have an ID")).body(null);
        }
        ClientTypeDTO result = clientTypeService.save(clientTypeDTO);
        return ResponseEntity.created(new URI("/api/client-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /client-types : Updates an existing clientType.
     *
     * @param clientTypeDTO the clientTypeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated clientTypeDTO,
     * or with status 400 (Bad Request) if the clientTypeDTO is not valid,
     * or with status 500 (Internal Server Error) if the clientTypeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/client-types")
    @Timed
    public ResponseEntity<ClientTypeDTO> updateClientType(@Valid @RequestBody ClientTypeDTO clientTypeDTO) throws URISyntaxException {
        log.debug("REST request to update ClientType : {}", clientTypeDTO);
        if (clientTypeDTO.getId() == null) {
            return createClientType(clientTypeDTO);
        }
        ClientTypeDTO result = clientTypeService.save(clientTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, clientTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /client-types : get all the clientTypes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of clientTypes in body
     */
    @GetMapping("/client-types")
    @Timed
    public ResponseEntity<List<ClientTypeDTO>> getAllClientTypes(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of ClientTypes");
        Page<ClientTypeDTO> page = clientTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/client-types");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /client-types/:id : get the "id" clientType.
     *
     * @param id the id of the clientTypeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the clientTypeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/client-types/{id}")
    @Timed
    public ResponseEntity<ClientTypeDTO> getClientType(@PathVariable Long id) {
        log.debug("REST request to get ClientType : {}", id);
        ClientTypeDTO clientTypeDTO = clientTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(clientTypeDTO));
    }

    /**
     * DELETE  /client-types/:id : delete the "id" clientType.
     *
     * @param id the id of the clientTypeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/client-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteClientType(@PathVariable Long id) {
        log.debug("REST request to delete ClientType : {}", id);
        clientTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/client-types?query=:query : search for the clientType corresponding
     * to the query.
     *
     * @param query the query of the clientType search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/client-types")
    @Timed
    public ResponseEntity<List<ClientTypeDTO>> searchClientTypes(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of ClientTypes for query {}", query);
        Page<ClientTypeDTO> page = clientTypeService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/client-types");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
