package covid.tracing.model;


import covid.tracing.epidemiologist.EpidemiologistDTO;
import covid.tracing.user.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
public class Epidemiologist {

    private Long epidId;

    private String email;

    private String password;

    private String name;

    private String phone;

    public Epidemiologist(EpidemiologistDTO.SignUp signUp) {
        this.email = signUp.getEmail();
        this.password = signUp.getPassword();
        this.name = signUp.getName();
        this.phone = signUp.getPhone();
    }
}
