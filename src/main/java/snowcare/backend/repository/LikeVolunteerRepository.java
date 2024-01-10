package snowcare.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import snowcare.backend.domain.LikeVolunteer;

import java.util.List;

public interface LikeVolunteerRepository extends JpaRepository<LikeVolunteer, Long> {
    LikeVolunteer findByUserIdAndVolunteerId(Long userId, Long volunteerId);
    List<LikeVolunteer> findByUserId(Long userId);
}
