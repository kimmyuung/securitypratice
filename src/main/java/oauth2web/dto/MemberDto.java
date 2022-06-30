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

    public MemberEntity toentity() { // 멤버 DTO를 엔티티로 변환
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(); // 보안 위해 패스워드 인코딩
        return MemberEntity.builder()
                .mid(this.mid)
                .mpassword(encoder.encode(this.mpassword))
                .role(Role.Member)
                .build();
    }
}
