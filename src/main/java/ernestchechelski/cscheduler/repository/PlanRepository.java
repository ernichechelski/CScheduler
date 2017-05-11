package ernestchechelski.cscheduler.repository;

import ernestchechelski.cscheduler.domain.Plan;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Plan entity.
 */
@SuppressWarnings("unused")
public interface PlanRepository extends JpaRepository<Plan,Long> {

    @Query("select plan from Plan plan where plan.owner.login = ?#{principal.username}")
    List<Plan> findByOwnerIsCurrentUser();

}
