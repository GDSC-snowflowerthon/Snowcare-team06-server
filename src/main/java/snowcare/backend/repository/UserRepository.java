package snowcare.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import snowcare.backend.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Boolean existsByNickname(String nickname);


    User findByEmail(String email);
    List<User> findByWeatherAlarmIsTrue();
}
