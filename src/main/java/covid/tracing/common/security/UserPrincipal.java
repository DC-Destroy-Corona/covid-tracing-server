package covid.tracing.common.security;

import covid.tracing.model.Epidemiologist;
import covid.tracing.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class UserPrincipal implements UserDetails {

    private Long id;

    private String email;

    private String password;

    private String name;

    public UserPrincipal(Long id, String email, String password, String name) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public static UserPrincipal create(User user) {
        return new UserPrincipal(user.getUserId(), user.getEmail(), user.getPassword(), user.getName());
    }

    public static UserPrincipal create(Epidemiologist epidemiologist) {
        return new UserPrincipal(epidemiologist.getEpidId(), epidemiologist.getEmail(), epidemiologist.getPassword(), epidemiologist.getName());
    }

    public Long getId() {
        return this.id;
    }

    public String getEmail() {
        return this.email;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }



    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
