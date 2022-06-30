package oauth2web.Entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity // 엔티티로 사용
@AllArgsConstructor@NoArgsConstructor // 모든 필드를 가진 생성자와 텅빈 필드를 가진 생성자 생성
@Getter@Setter@ToString // 값 호출, 값 저장, 객체를 문자열로 변환
@Builder // 빌더 사용
public class MemberEntity {
    @Id // pk 값
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int mno;

    private String mid;
    private String mpassword;
    private Role role;
}
