package musobek.shodmonov.developer_test.repositories;

import musobek.shodmonov.developer_test.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {
}
