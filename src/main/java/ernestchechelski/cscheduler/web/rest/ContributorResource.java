package ernestchechelski.cscheduler.web.rest;

import com.codahale.metrics.annotation.Timed;
import ernestchechelski.cscheduler.domain.Contributor;

import ernestchechelski.cscheduler.repository.ContributorRepository;
import ernestchechelski.cscheduler.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Contributor.
 */
@RestController
@RequestMapping("/api")
public class ContributorResource {

    private final Logger log = LoggerFactory.getLogger(ContributorResource.class);

    private static final String ENTITY_NAME = "contributor";
        
    private final ContributorRepository contributorRepository;

    public ContributorResource(ContributorRepository contributorRepository) {
        this.contributorRepository = contributorRepository;
    }

    /**
     * POST  /contributors : Create a new contributor.
     *
     * @param contributor the contributor to create
     * @return the ResponseEntity with status 201 (Created) and with body the new contributor, or with status 400 (Bad Request) if the contributor has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/contributors")
    @Timed
    public ResponseEntity<Contributor> createContributor(@Valid @RequestBody Contributor contributor) throws URISyntaxException {
        log.debug("REST request to save Contributor : {}", contributor);
        if (contributor.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new contributor cannot already have an ID")).body(null);
        }
        Contributor result = contributorRepository.save(contributor);
        return ResponseEntity.created(new URI("/api/contributors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /contributors : Updates an existing contributor.
     *
     * @param contributor the contributor to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated contributor,
     * or with status 400 (Bad Request) if the contributor is not valid,
     * or with status 500 (Internal Server Error) if the contributor couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/contributors")
    @Timed
    public ResponseEntity<Contributor> updateContributor(@Valid @RequestBody Contributor contributor) throws URISyntaxException {
        log.debug("REST request to update Contributor : {}", contributor);
        if (contributor.getId() == null) {
            return createContributor(contributor);
        }
        Contributor result = contributorRepository.save(contributor);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, contributor.getId().toString()))
            .body(result);
    }

    /**
     * GET  /contributors : get all the contributors.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of contributors in body
     */
    @GetMapping("/contributors")
    @Timed
    public List<Contributor> getAllContributors() {
        log.debug("REST request to get all Contributors");
        List<Contributor> contributors = contributorRepository.findAll();
        return contributors;
    }

    /**
     * GET  /contributors/:id : get the "id" contributor.
     *
     * @param id the id of the contributor to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the contributor, or with status 404 (Not Found)
     */
    @GetMapping("/contributors/{id}")
    @Timed
    public ResponseEntity<Contributor> getContributor(@PathVariable Long id) {
        log.debug("REST request to get Contributor : {}", id);
        Contributor contributor = contributorRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(contributor));
    }

    /**
     * DELETE  /contributors/:id : delete the "id" contributor.
     *
     * @param id the id of the contributor to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/contributors/{id}")
    @Timed
    public ResponseEntity<Void> deleteContributor(@PathVariable Long id) {
        log.debug("REST request to delete Contributor : {}", id);
        contributorRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
