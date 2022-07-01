package oauth2web.service;

import oauth2web.Entity.MemberEntity;
import oauth2web.Entity.MemberRepository;
import oauth2web.dto.LoginDto;
import oauth2web.dto.MemberDto;

import oauth2web.dto.Oauth2Dto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.*;


@Service
// DB를 조작하는 역할 수행
public class IndexService implements UserDetailsService, OAuth2UserService<OAuth2UserRequest, OAuth2User> { // 로그인시 권한을 조회하기 위해 상속

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

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException { // 소셜 로그인 회원
       OAuth2UserService oAuth2UserService = new DefaultOAuth2UserService(); // 소셜 로그인 회원
       OAuth2User oAuth2User = oAuth2UserService.loadUser(userRequest); // 소셜 로그인 회원의 정보를 얻어옴

       String registrationId = userRequest.getClientRegistration().getRegistrationId(); // 어느 플랫폼의 회원인지 알기 위해

       Map<String, Object> attributes = oAuth2User.getAttributes(); // 불러온 회원의 정보가 있는 곳
       String nameAttribute =
               userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
       // 회원의 정보가 저장된 객체의 이름 ex : [kakao_account]

        Oauth2Dto oauth2Dto = Oauth2Dto.of(registrationId, attributes, nameAttribute);


        System.out.println(nameAttribute); // 회원의 정보가 저장된 객체의 이름
        System.out.println(registrationId); // 플랫폼의 정보
        System.out.println(attributes.toString()); // 회원의 정보가 있는 곳

        Optional<MemberEntity> Optional = memberRepository.findBymid(oauth2Dto.getMid()); // 로그인 여부에 대한 조사를 위해
        if(! Optional.isPresent()) {// 로그인한적이 없다면
            memberRepository.save(oauth2Dto.toentity()); // 로그인 한적이 없다면 엔티티로 변환하여 DB에 저장
        }
        else {
            // 로그인 한적이 있으면 db업데이트 처리

        }
       return new DefaultOAuth2User( // Oauth2 로그인 유저
               Collections.singleton(new SimpleGrantedAuthority("MEMBER")),// 권한 부여
                attributes, // 불러온 회원의 정보
               nameAttribute); // 회원의 정보가 저장된 객체의 이름
    }
    public String authenticationget() {
        Authentication authentication // 인가된 객체를 불러옴 시큐리티컨택스트 홀더안에 컨택스트안의 인가 호출
                = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal(); // 인가된 클래스안의 객체 호출
        String mid = null;
        if(principal != null) {
            // 인증(로그인)이 되어 있는 상태
           if(principal instanceof UserDetails) { // 일반회원이라면
               mid = ((UserDetails) principal).getUsername(); // 객체에서의 필드호출
           } else if(principal instanceof OAuth2User){ // 소셜회원이라면
               Map<String, Object> attributes = ((OAuth2User) principal).getAttributes();
               // 객체속 회원정보가 저장된 속성들의 값 호출
              if(attributes.get("response") != null) { // 네이버 라면
                  Map<String, Object> map = (Map<String, Object>) attributes.get("response");
                  // 네이버는 회원정보가 저장된 객체를 respone으로 설정했기 때문
                  mid = map.get("email").toString();

              } else { // 카카오라면
                  Map<String, Object> map = (Map<String, Object>) attributes.get("kakao_account");
                  // 카카오는  kakao_account로 객체 이름을 설정
                  mid = map.get("email").toString();
              }
           }
            return mid;
        }else {
            // 인증 (로그인)이 안되어 있는 상태
            return null;
        }
    }



}
