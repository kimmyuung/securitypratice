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

    private Set<GrantedAuthority> authorities; // 부여된 인증들의 권한

    public LoginDto( String mid, String mpassword, Collection<? extends GrantedAuthority> authorities) {
        this.mid = mid;                             // 제네릭(아무것에서나)으로부터 상속받은 부여된 권한들을 모아놓음
        this.mpassword = mpassword;
        this.authorities = Collections.unmodifiableSet(new LinkedHashSet<>(authorities) );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
       return this.authorities;
    }
    // 권한 반환
    @Override
    public String getPassword() {
        return this.mpassword;
    }
    // 패스워드 반환
    @Override
    public String getUsername() {
        return this.mid;
    }
    // 아이디 반환
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    // 계정 인증 만료 여부 확인 [ true : 만료되지 않음 ]
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    // 계정 잠겨 있는지 확인 [ true : 잠겨있지 않음 ]
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    // 계정 패스워드가 만료 여부 확인 [ true : 만료 되지않음 ]
    @Override
    public boolean isEnabled() {
        return true;
    }
    // 계정 사용 가능한 여부 확인 [ true : 사용가능 ]
}
