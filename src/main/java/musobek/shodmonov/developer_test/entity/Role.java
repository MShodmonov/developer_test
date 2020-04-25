package musobek.shodmonov.developer_test.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import musobek.shodmonov.developer_test.entity.abstractEntityLayer.AbstractEntity;
import musobek.shodmonov.developer_test.entity.enums.RoleEnumeration;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Role extends AbstractEntity {

    @Enumerated(EnumType.STRING)
    private RoleEnumeration name;
}
