package oauth2web.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.Entity;

@Getter
@AllArgsConstructor
public enum Role {
// 열거형 객체
    Member("ROLE_MEMBER", "회원"),
    ADMIN("ROLE_ADMIN", "관리자");

    private final String key; // 객체의 키 값
    private final String keyword; // 객체의 키워드 값
}
