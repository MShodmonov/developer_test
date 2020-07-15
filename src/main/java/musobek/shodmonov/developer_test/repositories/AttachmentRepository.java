package musobek.shodmonov.developer_test.repositories;

import musobek.shodmonov.developer_test.entity.Attachment;
import musobek.shodmonov.developer_test.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AttachmentRepository extends JpaRepository<Attachment, UUID> {
    Optional<Attachment> findByCreatedBy(User user);
}
