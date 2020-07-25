package covid.tracing.account;

import covid.tracing.common.datatype.Role;
import covid.tracing.account.epidemiologist.EpidemiologistDTO;
import covid.tracing.mappers.AuthenticationMapper;
import covid.tracing.mappers.EpidemiologistMapper;
import covid.tracing.mappers.UserMapper;
import covid.tracing.model.Authentication;
import covid.tracing.model.Epidemiologist;
import covid.tracing.model.User;
import covid.tracing.account.user.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SignUpService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private AuthenticationMapper authenticationMapper;
    private UserMapper userMapper;
    private EpidemiologistMapper epidemiologistMapper;
    private PasswordEncoder passwordEncoder;


    @Autowired
    public SignUpService(AuthenticationMapper authenticationMapper, UserMapper userMapper, EpidemiologistMapper epidemiologistMapper, PasswordEncoder passwordEncoder) {
        this.authenticationMapper = authenticationMapper;
        this.userMapper = userMapper;
        this.epidemiologistMapper = epidemiologistMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUserAuthentication(String authKey, String email, Role user) {
        // select 햇는데 만약 있다면 -> 원래 있떤 인증 코드 보내기
        // select 햇는데 망냑 없다면 -> insert
        Authentication userAuthentication = authenticationMapper.findUserAuthentication(authKey, email, user);
        if(userAuthentication == null || userAuthentication.getAuthKey().equals(authKey)) {
            authenticationMapper.insertUserAuthentication(authKey, email, user);
        } else {
            authenticationMapper.updateUserAuthentication(authKey, email, user);
        }

    }

    @Transactional(rollbackFor = Exception.class)
    public void registerUser(UserDTO.SignUp signUp) {
        logger.info("process registering new user...");
        signUp.setPassword(passwordEncoder.encode(signUp.getPassword()));
        User user = new User(signUp);
        userMapper.insertUser(user);
        authenticationMapper.deleteUserAuthentication(signUp.getEmail(), Role.USER);
        logger.info("success registering new user(" + signUp.toString() + ")");
    }

    public void registerEpidemiologist(EpidemiologistDTO.SignUp signUp) {
        logger.info("process registering new epidemiologist...");
        signUp.setPassword(passwordEncoder.encode(signUp.getPassword()));
        Epidemiologist epid = new Epidemiologist(signUp);
        epidemiologistMapper.insertEpidemiologist(epid);
        logger.info("success registering new epidemiologist(" + signUp.toString() + ")");
    }
}
