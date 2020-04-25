package musobek.shodmonov.developer_test.repositories;

import musobek.shodmonov.developer_test.entity.AttachmentContent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AttachmentContentRepository extends JpaRepository<AttachmentContent, UUID> {
}
