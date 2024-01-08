package snowcare.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import snowcare.backend.domain.User;
public interface UserRepository extends JpaRepository<User, Long> {
}
