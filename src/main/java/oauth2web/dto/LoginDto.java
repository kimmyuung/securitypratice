package oauth2web.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Builder
@Getter
public class LoginDto implements UserDetails {
    private String mid;
    private String mpassword;

    private Set<GrantedAuthority> authorities;

    public LoginDto( String mid, String mpassword, Collection<? extends GrantedAuthority> authorities) {
        this.mid = mid;
        this.mpassword = mpassword;
        this.authorities = Collections.unmodifiableSet(new LinkedHashSet<>(authorities) );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
       return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.mpassword;
    }

    @Override
    public String getUsername() {
        return this.mid;
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
