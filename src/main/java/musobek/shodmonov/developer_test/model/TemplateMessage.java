package musobek.shodmonov.developer_test.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TemplateMessage {
    private Boolean actionSuccess;
    private String message;
}
