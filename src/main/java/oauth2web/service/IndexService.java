package oauth2web.service;

import oauth2web.Entity.MemberEntity;
import oauth2web.Entity.MemberRepository;
import oauth2web.dto.LoginDto;
import oauth2web.dto.MemberDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;


@Service
// DB를 조작하는 역할 수행
public class IndexService implements UserDetailsService { // 로그인시 권한을 조회하기 위해 상속

    @Autowired
    MemberRepository memberRepository; // @Autowired : 객체를 자동으로 선언해주는 어노테이션

    @Transactional // 트랜잭션 : 쪼갤 수 없는 일의 단위
    public void signup(MemberDto memberDto) {
        memberRepository.save(memberDto.toentity()); // 멤버 저장소에 멤버DTO를 entity로 변환하여 저장
    }

    @Override
    public UserDetails loadUserByUsername(String mid) throws UsernameNotFoundException {
       MemberEntity memberEntity = memberRepository.findBymid(mid).get();
        // 아이디를 가진 회원 엔티티가 있는지 검색

        List<GrantedAuthority> authorityList = new ArrayList<>();
        //GrantedAuthority : 부여된 인증의 클래스
        //   List<GrantedAuthority> : 부여된 인증들을 모아두기

        authorityList.add(new SimpleGrantedAuthority(memberEntity.getRole().getKey() ) );
        // 조회된 회원의 권한을 찾음

        LoginDto loginDto = LoginDto.builder() // builder를 사용하여 보다 유연성 있는 객체 생성 가능
                .mid(memberEntity.getMid()) // 회원 엔티티의 아이디를 로그인시 로그인된 회원의 아이디로 설정
                .mpassword(memberEntity.getMpassword()) // 회원 엔티티의 비밀번호를 로그인된 회원의 비밀번호로 설정
                .authorities(Collections.unmodifiableSet(new LinkedHashSet<>(authorityList))) // 회원 엔티티의 권한을 로그인된
                // 회원의 권한으로 설정
                .build();

        return loginDto;
    }


}
