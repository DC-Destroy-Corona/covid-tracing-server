package covid.tracing.common.validator;

import covid.tracing.common.enums.Role;
import covid.tracing.epidemiologist.EpidemiologistDTO;
import covid.tracing.mappers.AuthenticationMapper;
import covid.tracing.mappers.EpidemiologistMapper;
import covid.tracing.mappers.UserMapper;
import covid.tracing.model.Authentication;
import covid.tracing.model.Epidemiologist;
import covid.tracing.model.User;
import covid.tracing.user.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;

import javax.validation.Valid;

@Component
public class SignUpValidator {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private AuthenticationMapper authenticationMapper;
    private EpidemiologistMapper epidemiologistMapper;
    private UserMapper userMapper;

    @Autowired
    public SignUpValidator(AuthenticationMapper authenticationMapper, EpidemiologistMapper epidemiologistMapper, UserMapper userMapper) {
        this.authenticationMapper = authenticationMapper;
        this.epidemiologistMapper = epidemiologistMapper;
        this.userMapper = userMapper;
    }

    public void validate(String authKey, String email, Role role, Errors errors) {
        Authentication userAuthentication = authenticationMapper.findUserAuthentication(authKey, email, role);
        if (userAuthentication == null) {
            errors.reject("NotFound", "NotFound your authentication number");
            errors.rejectValue("email", "wrongValue", "message");
        }
    }

    public void validate(String email, Errors errors) {
        logger.info("validating email(" + email.toString() + ")");
        User user =  userMapper.findUserWithEmail(email);
        if (user != null) {
            logger.info("email is not valid(" + email.toString() + ")");
            errors.reject("NotValid", "Your email is not valid");
        }
    }

    public void validate(EpidemiologistDTO.SignUp signUp, Errors errors) {
        logger.info("validating sign-up form as epid : " + signUp.toString());

        if(errors.hasErrors()) {
            logger.info("data is Invalid(" + signUp.toString() + ")");
            return ;
        }

        Epidemiologist epid = epidemiologistMapper.findEpidemiologistWithEmail(signUp.getEmail());
        if (epid != null) {
            logger.info("email is Invalid(" + signUp.getEmail() + ") because of already existing email");
            errors.rejectValue("email", "invalid.email", "이미 가입된 이메일 입니다.");
        }
    }

    public void validate(UserDTO.SignUp signUp, Errors errors) {
        // TODO : 사용자 회원가입 이메일 검증
    }
}
