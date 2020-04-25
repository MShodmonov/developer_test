package musobek.shodmonov.developer_test.repositories;

import musobek.shodmonov.developer_test.entity.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AttachmentRepository extends JpaRepository<Attachment, UUID> {
}
