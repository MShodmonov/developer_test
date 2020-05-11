package musobek.shodmonov.developer_test.entity.audit;

import lombok.Data;
import lombok.EqualsAndHashCode;
import musobek.shodmonov.developer_test.entity.User;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public abstract class UserDateAuditing extends DateAuditing{

    @CreatedBy
    @ManyToOne(fetch = FetchType.LAZY) //cascade = {CascadeType.REFRESH,CascadeType.MERGE}
    @JoinColumn(updatable = false)
    private User createdBy;

    @LastModifiedBy
    @ManyToOne(fetch = FetchType.LAZY)
    private User updatedBy;
}
