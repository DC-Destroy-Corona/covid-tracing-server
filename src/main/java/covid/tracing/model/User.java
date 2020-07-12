package covid.tracing.model;


import covid.tracing.user.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
public class User {

    private Long userId;

    private String email;

    private String password;

    private String name;

    private String birthdate;

    private String phone;

    public User(UserDTO.SignUp signUp) {
        this.email = signUp.getEmail();
        this.password = signUp.getPassword();
        this.name = signUp.getName();
        this.birthdate = signUp.getBirthdate();
        this.phone = signUp.getPhone();
    }
}
