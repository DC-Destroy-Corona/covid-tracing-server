package covid.tracing.model;


import covid.tracing.account.epidemiologist.EpidemiologistDTO;
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

    private String gender;

    public Epidemiologist(EpidemiologistDTO.SignUp signUp) {
        this.email = signUp.getEmail();
        this.password = signUp.getPassword();
        this.name = signUp.getName();
        this.phone = signUp.getPhone();
        this.gender = signUp.getGender();
    }
}
