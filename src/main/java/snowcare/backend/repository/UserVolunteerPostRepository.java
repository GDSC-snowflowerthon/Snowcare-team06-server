package snowcare.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import snowcare.backend.domain.UserVolunteerPost;

public interface UserVolunteerPostRepository extends JpaRepository<UserVolunteerPost, Long> {
}
