package covid.tracing.common.security;

import covid.tracing.mappers.EpidemiologistMapper;
import covid.tracing.mappers.UserMapper;
import covid.tracing.model.Epidemiologist;
import covid.tracing.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private UserMapper userMapper;
    private EpidemiologistMapper epidemiologistMapper;

    @Autowired
    public CustomUserDetailsService(UserMapper userMapper, EpidemiologistMapper epidemiologistMapper) {
        this.userMapper = userMapper;
        this.epidemiologistMapper = epidemiologistMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.loadUserByEmail(username);
    }

    public UserPrincipal loadUserByEmail(String email) {
        logger.info("load user by email(" + email.toString() + ")");
        logger.info("execute query method) userMapper.findUserWithEmail(email)");
        User user = userMapper.findUserWithEmail(email);
        return UserPrincipal.create(user);
    }

    public UserPrincipal loadEpidemiologistByEmail(String email) {
        logger.info("load epidemiologist by email(" + email.toString() + ")");
        logger.info("execute query method) epidemiologistMapper.findEpidemiologistWithEmail(email)");
        Epidemiologist epidemiologist = epidemiologistMapper.findEpidemiologistWithEmail(email);
        return UserPrincipal.create(epidemiologist);
    }

    public UserPrincipal loadUserById(Long id) {
        logger.info("load user by id");
        logger.info("execute query method) userMapper.findUserWithId(id)");
        User user = userMapper.findUserWithId(id);
        return UserPrincipal.create(user);
    }

    public UserPrincipal loadEpidemiologistById(Long id) {
        logger.info("load user by id");
        logger.info("execute query method) epidemiologistMapper.findEpidemiologistWithId(id)");
        Epidemiologist epidemiologist = epidemiologistMapper.findEpidemiologistWithId(id);
        return UserPrincipal.create(epidemiologist);
    }
}
