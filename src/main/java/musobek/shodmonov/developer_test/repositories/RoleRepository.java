package musobek.shodmonov.developer_test.repositories;

import musobek.shodmonov.developer_test.entity.Role;
import musobek.shodmonov.developer_test.entity.enums.RoleEnumeration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {
    public Optional<Role> findByName(RoleEnumeration name);
}
