package oauth2web.dto;

import lombok.*;
import oauth2web.Entity.MemberEntity;
import oauth2web.Entity.Role;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@AllArgsConstructor // 모든 생성자를 가짐
@NoArgsConstructor // 아무 생성자도 없음
@Getter // 값 호출
@Setter // 값 저장
@ToString // 객체를 문자열로 변환
public class MemberDto {
    private int mno;

    private String mid;
    private String mpassword;
    private Role role; // 권한

    public MemberEntity toentity() { // 멤버 DTO를 엔티티로 변환
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(); // 보안 위해 패스워드 인코딩
        return MemberEntity.builder()
                .mid(this.mid)
                .mpassword(encoder.encode(this.mpassword))
                .role(Role.Member)
                .build();
    }
}
