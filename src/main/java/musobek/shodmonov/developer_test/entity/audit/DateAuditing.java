package musobek.shodmonov.developer_test.entity.audit;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.sql.Timestamp;

@Data
@MappedSuperclass
public abstract class DateAuditing {
    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp createdAt;


    @UpdateTimestamp
    private Timestamp updatedAt;
}
