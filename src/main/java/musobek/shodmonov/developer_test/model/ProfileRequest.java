package musobek.shodmonov.developer_test.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import musobek.shodmonov.developer_test.entity.User;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileRequest {

    @Size(max = 32, min = 5, message = "Username must be between 5 and 32 characters.")
    private String username;

    @NotBlank(message = "Enter email address")
    @Email(message = "Enter vaild email address")
    private String email;

    @Size(min = 3, max = 255, message = "Full name must be between 5 and 255 characters.")
    private String fullName;

    @Size(min = 5, max = 16, message = "Password must be between 5 and 16 characters.")
    private String password;

    private String prePassword;

    @AssertTrue(message = "Password not confirmed")
    private boolean isValid()
    {
        return this.password.equals(prePassword);
    }

    @NotNull
    private MultipartFile photo;

    public ProfileRequest(User user) {
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.fullName = user.getFullName();
        this.password = user.getPassword();
    }
}
