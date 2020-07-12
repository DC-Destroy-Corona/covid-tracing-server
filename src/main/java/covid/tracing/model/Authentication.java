package covid.tracing.model;


import covid.tracing.common.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
public class Authentication {

    private String authKey;

    private Role role;

    private String email;

    private String password;

    private String name;

    private String phone;

    private String birthdate;

}
