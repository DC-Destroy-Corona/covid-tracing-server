package covid.tracing.user;

import covid.tracing.common.enums.Role;
import covid.tracing.mappers.AuthenticationMapper;
import covid.tracing.mappers.UserMapper;
import covid.tracing.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SignUpService {

    private AuthenticationMapper authenticationMapper;
    private UserMapper userMapper;
    private PasswordEncoder passwordEncoder;


    @Autowired
    public SignUpService(AuthenticationMapper authenticationMapper, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.authenticationMapper = authenticationMapper;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUserAuthentication(String authKey, String email, Role user) {
        authenticationMapper.insertUserAuthentication(authKey, email, user);
    }

    @Transactional(rollbackFor = Exception.class)
    public void registerUser(UserDTO.SignUp signUp) {
        signUp.setPassword(passwordEncoder.encode(signUp.getPassword()));
        User user = new User(signUp);
        userMapper.insertUser(user);
        authenticationMapper.deleteUserAuthentication(signUp.getEmail(), Role.USER);
    }
}
