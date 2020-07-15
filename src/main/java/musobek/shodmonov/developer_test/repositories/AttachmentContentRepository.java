package musobek.shodmonov.developer_test.repositories;

import musobek.shodmonov.developer_test.entity.AttachmentContent;
import musobek.shodmonov.developer_test.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AttachmentContentRepository extends JpaRepository<AttachmentContent, UUID> {
    Optional<AttachmentContent>findByCreatedBy(User user);
}
