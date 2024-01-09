package snowcare.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import snowcare.backend.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
