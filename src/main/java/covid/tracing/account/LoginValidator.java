package covid.tracing.account;

import covid.tracing.common.datatype.Role;
import covid.tracing.mappers.EpidemiologistMapper;
import covid.tracing.mappers.UserMapper;
import covid.tracing.model.Epidemiologist;
import covid.tracing.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;


@Component
public class LoginValidator {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private UserMapper userMapper;
    private EpidemiologistMapper epidemiologistMapper;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public LoginValidator(UserMapper userMapper, EpidemiologistMapper epidemiologistMapper, PasswordEncoder passwordEncoder) {
        this.userMapper = userMapper;
        this.epidemiologistMapper = epidemiologistMapper;
        this.passwordEncoder = passwordEncoder;
    }


    public void validate(LoginDTO login, Role role, Errors errors) {
        if (Role.USER.equals(role)) {
            logger.info("validating login form as user : " + login.toString());
            User user = userMapper.findUserWithEmail(login.getEmail());
            if(user == null) {
                errors.reject("NotFound", "Your email is not found");
                return ;
            }
            if(!passwordEncoder.matches(login.getPassword(), user.getPassword())) {
                errors.reject("Wrong", "Your password is wrong");
            }
        } else if (Role.EPID.equals(role)) {
            logger.info("validating login form as epid : " + login.toString());
            if(errors.hasErrors()) {
                logger.info("data is Invalid(" + login.toString() + ")");
                return ;
            }
            Epidemiologist epid = epidemiologistMapper.findEpidemiologistWithEmail(login.getEmail());
            if(epid == null) {
                logger.info("email is Invalid(" + login.getEmail() + ") because of not existing email");
                errors.rejectValue("email", "invalid.email", "존재하지 않는 계정입니다..");
                return;
            }
            if(!passwordEncoder.matches(login.getPassword(), epid.getPassword())) {
                logger.info("email is Invalid because of wrong password");
                errors.rejectValue("password", "invalid.password", "잘못된 비밀번호 입니다.");
            }
        }
    }
}
