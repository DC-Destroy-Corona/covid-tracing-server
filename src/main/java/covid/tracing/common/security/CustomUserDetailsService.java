package covid.tracing.common.security;

import covid.tracing.mappers.UserMapper;
import covid.tracing.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.loadUserByEmail(username);
    }

    public UserPrincipal loadUserByEmail(String email) {
        User user = userMapper.findUserWithEmail(email);
        return UserPrincipal.create(user);
    }

    public UserPrincipal loadUserById(Long id) {
        User user = userMapper.findUserWithId(id);
        return UserPrincipal.create(user);
    }
}
