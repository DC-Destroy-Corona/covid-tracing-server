package covid.tracing.epidemiologist;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class EpidemiologistDTO {

    @Data
    public static class SignUp {
        @NotBlank @Email
        private String email;

        @NotBlank @Size(min = 8, max = 50)
        private String password;

        @NotBlank @Size(max = 8)
        private String name;

        @NotBlank @Size(min = 11, max = 11)
        private String phone;
    }
}
