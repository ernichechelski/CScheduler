package ernestchechelski.cscheduler.web.rest;

import ernestchechelski.cscheduler.CSchedulerApp;

import ernestchechelski.cscheduler.domain.Plan;
import ernestchechelski.cscheduler.repository.PlanRepository;
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
 * Test class for the PlanResource REST controller.
 *
 * @see PlanResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CSchedulerApp.class)
public class PlanResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private PlanRepository planRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPlanMockMvc;

    private Plan plan;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PlanResource planResource = new PlanResource(planRepository);
        this.restPlanMockMvc = MockMvcBuilders.standaloneSetup(planResource)
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
    public static Plan createEntity(EntityManager em) {
        Plan plan = new Plan()
            .name(DEFAULT_NAME);
        return plan;
    }

    @Before
    public void initTest() {
        plan = createEntity(em);
    }

    @Test
    @Transactional
    public void createPlan() throws Exception {
        int databaseSizeBeforeCreate = planRepository.findAll().size();

        // Create the Plan
        restPlanMockMvc.perform(post("/api/plans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(plan)))
            .andExpect(status().isCreated());

        // Validate the Plan in the database
        List<Plan> planList = planRepository.findAll();
        assertThat(planList).hasSize(databaseSizeBeforeCreate + 1);
        Plan testPlan = planList.get(planList.size() - 1);
        assertThat(testPlan.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createPlanWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = planRepository.findAll().size();

        // Create the Plan with an existing ID
        plan.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlanMockMvc.perform(post("/api/plans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(plan)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Plan> planList = planRepository.findAll();
        assertThat(planList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = planRepository.findAll().size();
        // set the field null
        plan.setName(null);

        // Create the Plan, which fails.

        restPlanMockMvc.perform(post("/api/plans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(plan)))
            .andExpect(status().isBadRequest());

        List<Plan> planList = planRepository.findAll();
        assertThat(planList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPlans() throws Exception {
        // Initialize the database
        planRepository.saveAndFlush(plan);

        // Get all the planList
        restPlanMockMvc.perform(get("/api/plans?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(plan.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getPlan() throws Exception {
        // Initialize the database
        planRepository.saveAndFlush(plan);

        // Get the plan
        restPlanMockMvc.perform(get("/api/plans/{id}", plan.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(plan.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPlan() throws Exception {
        // Get the plan
        restPlanMockMvc.perform(get("/api/plans/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePlan() throws Exception {
        // Initialize the database
        planRepository.saveAndFlush(plan);
        int databaseSizeBeforeUpdate = planRepository.findAll().size();

        // Update the plan
        Plan updatedPlan = planRepository.findOne(plan.getId());
        updatedPlan
            .name(UPDATED_NAME);

        restPlanMockMvc.perform(put("/api/plans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPlan)))
            .andExpect(status().isOk());

        // Validate the Plan in the database
        List<Plan> planList = planRepository.findAll();
        assertThat(planList).hasSize(databaseSizeBeforeUpdate);
        Plan testPlan = planList.get(planList.size() - 1);
        assertThat(testPlan.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingPlan() throws Exception {
        int databaseSizeBeforeUpdate = planRepository.findAll().size();

        // Create the Plan

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPlanMockMvc.perform(put("/api/plans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(plan)))
            .andExpect(status().isCreated());

        // Validate the Plan in the database
        List<Plan> planList = planRepository.findAll();
        assertThat(planList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePlan() throws Exception {
        // Initialize the database
        planRepository.saveAndFlush(plan);
        int databaseSizeBeforeDelete = planRepository.findAll().size();

        // Get the plan
        restPlanMockMvc.perform(delete("/api/plans/{id}", plan.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Plan> planList = planRepository.findAll();
        assertThat(planList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Plan.class);
        Plan plan1 = new Plan();
        plan1.setId(1L);
        Plan plan2 = new Plan();
        plan2.setId(plan1.getId());
        assertThat(plan1).isEqualTo(plan2);
        plan2.setId(2L);
        assertThat(plan1).isNotEqualTo(plan2);
        plan1.setId(null);
        assertThat(plan1).isNotEqualTo(plan2);
    }
}
