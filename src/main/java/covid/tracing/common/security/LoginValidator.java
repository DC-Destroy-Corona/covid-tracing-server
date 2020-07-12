package covid.tracing.common.security;

import covid.tracing.common.enums.Role;
import covid.tracing.mappers.UserMapper;
import covid.tracing.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;


@Component
public class LoginValidator {

    private UserMapper userMapper;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public LoginValidator(UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }


    public void validate(LoginDTO login, Role role, Errors errors) {
        if (role == Role.USER) {
            User user = userMapper.findUserWithEmail(login.getEmail());
            if(user == null) {
                errors.reject("NotFound", "Your email is not founded ");
                return ;
            }
            if(!passwordEncoder.matches(login.getPassword(), user.getPassword())) {
                errors.reject("WrongPassword", "Your password is wrong");
            }
        } else if (role == Role.EPID) {

        }
    }
}
