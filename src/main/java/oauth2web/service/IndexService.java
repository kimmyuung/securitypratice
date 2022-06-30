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
public class IndexService implements UserDetailsService {

    @Autowired
    MemberRepository memberRepository;

    @Transactional
    public void signup(MemberDto memberDto) {
        memberRepository.save(memberDto.toentity());
    }

    @Override
    public UserDetails loadUserByUsername(String mid) throws UsernameNotFoundException {
       MemberEntity memberEntity = memberRepository.findBymid(mid).get();

        List<GrantedAuthority> authorityList = new ArrayList<>();
        //GrantedAuthority : 부여된 인증의 클래스
        //   List<GrantedAuthority> : 부여된 인증들을 모아두기
        authorityList.add(new SimpleGrantedAuthority(memberEntity.getRole().getKey() ) );

        LoginDto loginDto = LoginDto.builder()
                .mid(memberEntity.getMid())
                .mpassword(memberEntity.getMpassword())
                .authorities(Collections.unmodifiableSet(new LinkedHashSet<>(authorityList)))
                .build();

        return loginDto;
    }


}
