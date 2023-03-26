package ohanyan.fw.auth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;


public class UserAuthDetails implements UserDetails {

   
    private final String login;
    private final String password;

    private final Collection<? extends GrantedAuthority> authorities;

    public UserAuthDetails(String login, String password,
            Collection<? extends GrantedAuthority> authorities) {
        this.login = login;
        this.password = password;
        this.authorities = authorities;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
