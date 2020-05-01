package musobek.shodmonov.developer_test.repositories;

import musobek.shodmonov.developer_test.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findUsersByUsername(String username);
    Optional<User> findByUsernameOrEmail(String username,String email);
    Optional<User>findByActivationCode(UUID code);
}

