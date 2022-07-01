package oauth2web.dto;


import lombok.*;
import oauth2web.Entity.MemberEntity;
import oauth2web.Entity.Role;

import java.util.Map;

@Getter@Setter@ToString
@AllArgsConstructor@NoArgsConstructor@Builder
public class Oauth2Dto {
    private String mid;
    private String registrationId;
    private Map<String, Object> attributes;
    private String nameAttributekey;

    public static Oauth2Dto of(String registrationId, Map<String, Object> attributes, String nameAttributekey) {
        if(registrationId.equals("naver")) { // 플랫폼이 네이버라면
            return ofnaver(registrationId, attributes, nameAttributekey);
            // 인수 : 플랫폼 정보, 회원 정보, 회원정보가 저장된 객체의 이름
        }
        else if(registrationId.equals("kakao")) { // 플랫폼이 카카오라면
            return ofkakao(registrationId, attributes, nameAttributekey);
        }
        else { // 플랫폼이 검색되지 않는다면
            return null;
        }

    }

    public static Oauth2Dto ofnaver(String registrationId, Map<String, Object> attributes, String nameAttributekey) {
                                // 인수 : 플랫폼의 아이디,
        Map<String, Object> response = (Map<String, Object>)  attributes.get(nameAttributekey); // 불러온 회원의 정보가 저장된 곳
        return Oauth2Dto.builder() // 아이디만 빌더로 사용하는 이유는 나머지는 loaduser에서 return 시 실행하기 때문
                .mid((String) response.get("email") ) // 이메일을 아이디로 사용
                .build();
    }

    public  static Oauth2Dto ofkakao(String registrationId, Map<String, Object> attributes, String nameAttributekey) {
        Map<String, Object> kakao_account = (Map<String, Object>) attributes.get(nameAttributekey);
        return Oauth2Dto.builder()
                .mid((String) kakao_account.get("email") ) // 이메일을 아이디로 사용
                .build();
    }
    public MemberEntity toentity() { // db에 저장하기 위해 소셜 로그인된 회원을 회원 엔티티로 변환
        return MemberEntity.builder()
                .mid(this.mid)
                .role(Role.Member)
                .build();
    }
}
