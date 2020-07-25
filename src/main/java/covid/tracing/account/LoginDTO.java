package covid.tracing.account;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class LoginDTO { // Authentication Request

    @NotBlank @Email
    private String email;

    @NotBlank @Size(min = 8, max = 50)
    private String password;
}
