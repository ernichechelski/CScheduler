package ernestchechelski.cscheduler.repository;

import ernestchechelski.cscheduler.domain.Contributor;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Contributor entity.
 */
@SuppressWarnings("unused")
public interface ContributorRepository extends JpaRepository<Contributor,Long> {

    @Query("select contributor from Contributor contributor where contributor.owner.login = ?#{principal.username}")
    List<Contributor> findByOwnerIsCurrentUser();

}
