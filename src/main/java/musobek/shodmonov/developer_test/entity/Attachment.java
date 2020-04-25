package musobek.shodmonov.developer_test.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import musobek.shodmonov.developer_test.entity.abstractEntityLayer.AbstractEntity;

import javax.persistence.Entity;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Attachment extends AbstractEntity {
    private String name;
    private String contentType;
    private Long size;
}
