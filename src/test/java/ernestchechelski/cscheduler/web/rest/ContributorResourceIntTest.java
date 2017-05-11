package ernestchechelski.cscheduler.web.rest;

import ernestchechelski.cscheduler.CSchedulerApp;

import ernestchechelski.cscheduler.domain.Contributor;
import ernestchechelski.cscheduler.domain.User;
import ernestchechelski.cscheduler.repository.ContributorRepository;
import ernestchechelski.cscheduler.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ContributorResource REST controller.
 *
 * @see ContributorResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CSchedulerApp.class)
public class ContributorResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_HASH = "AAAAAAAAAA";
    private static final String UPDATED_HASH = "BBBBBBBBBB";

    @Autowired
    private ContributorRepository contributorRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restContributorMockMvc;

    private Contributor contributor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ContributorResource contributorResource = new ContributorResource(contributorRepository);
        this.restContributorMockMvc = MockMvcBuilders.standaloneSetup(contributorResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Contributor createEntity(EntityManager em) {
        Contributor contributor = new Contributor()
            .name(DEFAULT_NAME)
            .hash(DEFAULT_HASH);
        // Add required entity
        User owner = UserResourceIntTest.createEntity(em);
        em.persist(owner);
        em.flush();
        contributor.setOwner(owner);
        return contributor;
    }

    @Before
    public void initTest() {
        contributor = createEntity(em);
    }

    @Test
    @Transactional
    public void createContributor() throws Exception {
        int databaseSizeBeforeCreate = contributorRepository.findAll().size();

        // Create the Contributor
        restContributorMockMvc.perform(post("/api/contributors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contributor)))
            .andExpect(status().isCreated());

        // Validate the Contributor in the database
        List<Contributor> contributorList = contributorRepository.findAll();
        assertThat(contributorList).hasSize(databaseSizeBeforeCreate + 1);
        Contributor testContributor = contributorList.get(contributorList.size() - 1);
        assertThat(testContributor.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testContributor.getHash()).isEqualTo(DEFAULT_HASH);
    }

    @Test
    @Transactional
    public void createContributorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = contributorRepository.findAll().size();

        // Create the Contributor with an existing ID
        contributor.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restContributorMockMvc.perform(post("/api/contributors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contributor)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Contributor> contributorList = contributorRepository.findAll();
        assertThat(contributorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = contributorRepository.findAll().size();
        // set the field null
        contributor.setName(null);

        // Create the Contributor, which fails.

        restContributorMockMvc.perform(post("/api/contributors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contributor)))
            .andExpect(status().isBadRequest());

        List<Contributor> contributorList = contributorRepository.findAll();
        assertThat(contributorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHashIsRequired() throws Exception {
        int databaseSizeBeforeTest = contributorRepository.findAll().size();
        // set the field null
        contributor.setHash(null);

        // Create the Contributor, which fails.

        restContributorMockMvc.perform(post("/api/contributors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contributor)))
            .andExpect(status().isBadRequest());

        List<Contributor> contributorList = contributorRepository.findAll();
        assertThat(contributorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllContributors() throws Exception {
        // Initialize the database
        contributorRepository.saveAndFlush(contributor);

        // Get all the contributorList
        restContributorMockMvc.perform(get("/api/contributors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contributor.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].hash").value(hasItem(DEFAULT_HASH.toString())));
    }

    @Test
    @Transactional
    public void getContributor() throws Exception {
        // Initialize the database
        contributorRepository.saveAndFlush(contributor);

        // Get the contributor
        restContributorMockMvc.perform(get("/api/contributors/{id}", contributor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(contributor.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.hash").value(DEFAULT_HASH.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingContributor() throws Exception {
        // Get the contributor
        restContributorMockMvc.perform(get("/api/contributors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContributor() throws Exception {
        // Initialize the database
        contributorRepository.saveAndFlush(contributor);
        int databaseSizeBeforeUpdate = contributorRepository.findAll().size();

        // Update the contributor
        Contributor updatedContributor = contributorRepository.findOne(contributor.getId());
        updatedContributor
            .name(UPDATED_NAME)
            .hash(UPDATED_HASH);

        restContributorMockMvc.perform(put("/api/contributors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedContributor)))
            .andExpect(status().isOk());

        // Validate the Contributor in the database
        List<Contributor> contributorList = contributorRepository.findAll();
        assertThat(contributorList).hasSize(databaseSizeBeforeUpdate);
        Contributor testContributor = contributorList.get(contributorList.size() - 1);
        assertThat(testContributor.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testContributor.getHash()).isEqualTo(UPDATED_HASH);
    }

    @Test
    @Transactional
    public void updateNonExistingContributor() throws Exception {
        int databaseSizeBeforeUpdate = contributorRepository.findAll().size();

        // Create the Contributor

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restContributorMockMvc.perform(put("/api/contributors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contributor)))
            .andExpect(status().isCreated());

        // Validate the Contributor in the database
        List<Contributor> contributorList = contributorRepository.findAll();
        assertThat(contributorList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteContributor() throws Exception {
        // Initialize the database
        contributorRepository.saveAndFlush(contributor);
        int databaseSizeBeforeDelete = contributorRepository.findAll().size();

        // Get the contributor
        restContributorMockMvc.perform(delete("/api/contributors/{id}", contributor.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Contributor> contributorList = contributorRepository.findAll();
        assertThat(contributorList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Contributor.class);
        Contributor contributor1 = new Contributor();
        contributor1.setId(1L);
        Contributor contributor2 = new Contributor();
        contributor2.setId(contributor1.getId());
        assertThat(contributor1).isEqualTo(contributor2);
        contributor2.setId(2L);
        assertThat(contributor1).isNotEqualTo(contributor2);
        contributor1.setId(null);
        assertThat(contributor1).isNotEqualTo(contributor2);
    }
}
