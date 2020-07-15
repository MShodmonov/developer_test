package musobek.shodmonov.developer_test.repositories;

import musobek.shodmonov.developer_test.entity.Contact;
import musobek.shodmonov.developer_test.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ContactRepository extends JpaRepository<Contact, UUID> {
    Page<Contact>findAllByCreatedBy(User user, Pageable pageable);
}
