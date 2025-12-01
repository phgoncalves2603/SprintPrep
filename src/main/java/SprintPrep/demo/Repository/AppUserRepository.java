package SprintPrep.demo.Repository;

import SprintPrep.demo.Entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    Optional<AppUser> findByGithubId(Long githubId);
}
