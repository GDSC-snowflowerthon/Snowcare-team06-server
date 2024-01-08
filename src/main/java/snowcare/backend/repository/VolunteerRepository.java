package snowcare.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import snowcare.backend.domain.Volunteer;

public interface VolunteerRepository extends JpaRepository<Volunteer, Long> {
}
