package musobek.shodmonov.developer_test.entity.abstractEntityLayer;

import lombok.Data;
import lombok.EqualsAndHashCode;
import musobek.shodmonov.developer_test.entity.audit.UserDateAuditing;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@MappedSuperclass
public abstract class AbstractEntity extends UserDateAuditing {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
}
