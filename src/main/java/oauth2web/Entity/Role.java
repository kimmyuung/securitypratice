package oauth2web.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.Entity;

@Getter
@AllArgsConstructor
public enum Role {

    Member("ROLE_MEMBER", "회원"),
    ADMIN("ROLE_ADMIN", "관리자");

    private final String key;
    private final String keyword;
}
