package oauth2web.dto;

import lombok.*;
import oauth2web.Entity.MemberEntity;
import oauth2web.Entity.Role;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class MemberDto {
    private int mno;

    private String mid;
    private String mpassword;
    private Role role;

    public MemberEntity toentity() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return MemberEntity.builder()
                .mid(this.mid)
                .mpassword(encoder.encode(this.mpassword))
                .role(Role.Member)
                .build();
    }
}
