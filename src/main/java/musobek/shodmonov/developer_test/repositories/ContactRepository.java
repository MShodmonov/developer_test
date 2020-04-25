package musobek.shodmonov.developer_test.repositories;

import musobek.shodmonov.developer_test.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ContactRepository extends JpaRepository<Contact, UUID> {
}
