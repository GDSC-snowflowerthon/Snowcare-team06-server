package snowcare.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import snowcare.backend.domain.Volunteer;

import java.util.List;

public interface VolunteerRepository extends JpaRepository<Volunteer, Long> {
    @Query(value = "SELECT * FROM volunteer ORDER BY volunteer_id DESC LIMIT 3", nativeQuery = true)
    List<Volunteer> findTop3ByIdDesc();
}
