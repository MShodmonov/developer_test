package musobek.shodmonov.developer_test.model;

import com.sun.istack.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactRequest {

    private String name;

    @NotBlank(message = "Phone number is required.")
    private String phoneNumber;

    @Email(message = "Invalid email address")
    @Nullable
    private String email;
}
