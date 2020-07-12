package covid.tracing.user;

import covid.tracing.common.enums.Role;
import covid.tracing.mappers.AuthenticationMapper;
import covid.tracing.mappers.UserMapper;
import covid.tracing.model.Authentication;
import covid.tracing.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class SignUpValidator {

    private AuthenticationMapper authenticationMapper;
    private UserMapper userMapper;

    @Autowired
    public SignUpValidator(AuthenticationMapper authenticationMapper, UserMapper userMapper) {
        this.authenticationMapper = authenticationMapper;
        this.userMapper = userMapper;
    }

    public void validate(String authKey, String email, Role role, Errors errors) {
        Authentication userAuthentication = authenticationMapper.findUserAuthentication(authKey, email, role);
        if (userAuthentication == null) {
            errors.reject("NotValid", "NotFound your authentication number");
//            errors.rejectValue("email", "wrongValue", "message");
        }
    }

    public void validateEmail(String email, Errors errors) {
        User user =  userMapper.findUserWithEmail(email);
        if (user != null) {
            errors.reject("NotValid", "Your email is not valid");
        }
    }
}
