package musobek.shodmonov.developer_test.model;

import lombok.Data;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class RegisterRequest {

    @Size(max = 32,min = 5, message = "Username must be between 5 and 32 characters")
    private String username;

    @Email(message = "Please enter a valid email")
    @NotBlank(message = "Enter email address")
    private String email;
    @Size(max = 255, min = 3,message = "Full name must be between 3 and 255 characters")
    private String fullName;
    @Size(max = 16, min = 3,message = "Password must be between 3 and 255 characters")
    private String password;

    private String confirmPassword;
    @AssertTrue(message = "Not match")
    public boolean isvalid()
    {
        return this.password.equals(confirmPassword);
    }
}
