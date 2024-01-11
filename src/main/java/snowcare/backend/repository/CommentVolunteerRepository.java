package snowcare.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import snowcare.backend.domain.CommentVolunteer;

import java.util.List;

public interface CommentVolunteerRepository extends JpaRepository<CommentVolunteer, Long> {
    List<CommentVolunteer> findByUserIdAndVolunteerId(Long userId, Long volunteerId);
}
