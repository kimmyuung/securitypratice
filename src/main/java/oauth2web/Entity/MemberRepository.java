package oauth2web.Entity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, Integer>{ // DB와  JPA를 연결하여 결과를 출력하는 역할 수행
    Optional<MemberEntity> findBymid(String mid); // 회원의 아이디가 있는지에 대한 여부를 검색
}
