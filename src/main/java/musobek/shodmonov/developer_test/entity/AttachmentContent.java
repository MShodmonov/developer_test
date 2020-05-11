package musobek.shodmonov.developer_test.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import musobek.shodmonov.developer_test.entity.abstractEntityLayer.AbstractEntity;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttachmentContent extends AbstractEntity {

    @OneToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(nullable = false)
    private Attachment attachment;

    @Lob
    private byte[] content;
}
