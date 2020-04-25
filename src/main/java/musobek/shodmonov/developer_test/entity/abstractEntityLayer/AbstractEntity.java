package musobek.shodmonov.developer_test.entity.abstractEntityLayer;

import lombok.Data;
import lombok.EqualsAndHashCode;
import musobek.shodmonov.developer_test.entity.audit.UserDateAuditing;
import org.hibernate.annotations.GeneratorType;
import org.hibernate.annotations.GenericGenerator;

import org.hibernate.annotations.Type;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@MappedSuperclass
public abstract class AbstractEntity extends UserDateAuditing {

    @Id
    @Type(type = "org.hibernate.type.PostgresUUIDType")
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2",strategy = "org.hibernate.type.PostgresUUIDType")
    private UUID id;
}
