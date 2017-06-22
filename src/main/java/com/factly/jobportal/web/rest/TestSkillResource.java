package com.factly.jobportal.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.factly.jobportal.service.TestSkillService;
import com.factly.jobportal.web.rest.util.HeaderUtil;
import com.factly.jobportal.web.rest.util.PaginationUtil;
import com.factly.jobportal.service.dto.TestSkillDTO;
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
 * REST controller for managing TestSkill.
 */
@RestController
@RequestMapping("/api")
public class TestSkillResource {

    private final Logger log = LoggerFactory.getLogger(TestSkillResource.class);

    private static final String ENTITY_NAME = "testSkill";

    private final TestSkillService testSkillService;

    public TestSkillResource(TestSkillService testSkillService) {
        this.testSkillService = testSkillService;
    }

    /**
     * POST  /test-skills : Create a new testSkill.
     *
     * @param testSkillDTO the testSkillDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new testSkillDTO, or with status 400 (Bad Request) if the testSkill has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/test-skills")
    @Timed
    public ResponseEntity<TestSkillDTO> createTestSkill(@Valid @RequestBody TestSkillDTO testSkillDTO) throws URISyntaxException {
        log.debug("REST request to save TestSkill : {}", testSkillDTO);
        if (testSkillDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new testSkill cannot already have an ID")).body(null);
        }
        TestSkillDTO result = testSkillService.save(testSkillDTO);
        return ResponseEntity.created(new URI("/api/test-skills/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /test-skills : Updates an existing testSkill.
     *
     * @param testSkillDTO the testSkillDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated testSkillDTO,
     * or with status 400 (Bad Request) if the testSkillDTO is not valid,
     * or with status 500 (Internal Server Error) if the testSkillDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/test-skills")
    @Timed
    public ResponseEntity<TestSkillDTO> updateTestSkill(@Valid @RequestBody TestSkillDTO testSkillDTO) throws URISyntaxException {
        log.debug("REST request to update TestSkill : {}", testSkillDTO);
        if (testSkillDTO.getId() == null) {
            return createTestSkill(testSkillDTO);
        }
        TestSkillDTO result = testSkillService.save(testSkillDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, testSkillDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /test-skills : get all the testSkills.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of testSkills in body
     */
    @GetMapping("/test-skills")
    @Timed
    public ResponseEntity<List<TestSkillDTO>> getAllTestSkills(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of TestSkills");
        Page<TestSkillDTO> page = testSkillService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/test-skills");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /test-skills/:id : get the "id" testSkill.
     *
     * @param id the id of the testSkillDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the testSkillDTO, or with status 404 (Not Found)
     */
    @GetMapping("/test-skills/{id}")
    @Timed
    public ResponseEntity<TestSkillDTO> getTestSkill(@PathVariable Long id) {
        log.debug("REST request to get TestSkill : {}", id);
        TestSkillDTO testSkillDTO = testSkillService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(testSkillDTO));
    }

    /**
     * DELETE  /test-skills/:id : delete the "id" testSkill.
     *
     * @param id the id of the testSkillDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/test-skills/{id}")
    @Timed
    public ResponseEntity<Void> deleteTestSkill(@PathVariable Long id) {
        log.debug("REST request to delete TestSkill : {}", id);
        testSkillService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/test-skills?query=:query : search for the testSkill corresponding
     * to the query.
     *
     * @param query the query of the testSkill search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/test-skills")
    @Timed
    public ResponseEntity<List<TestSkillDTO>> searchTestSkills(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of TestSkills for query {}", query);
        Page<TestSkillDTO> page = testSkillService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/test-skills");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
