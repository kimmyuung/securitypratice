package oauth2web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import oauth2web.service.IndexService;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests() // 요청의 시작
                .antMatchers("/**") // /뒤에 어느 문자가 있어도
                .permitAll() // 요청들을 승인
                .and()
                .formLogin() // 로그인 폼 설정
                .loginPage("/member/login") // 로그인 시 사용할 페이지
                .loginProcessingUrl("/member/logincontroller") // 로그인 과정을 처리할 url
                .defaultSuccessUrl("/")// 로그인 성공시 이동할 URL
                .failureUrl("/member/login/error") // 로그인 실패
                .usernameParameter("mid") // user의 name으로 어떤 값(name)을 사용할 것인지
                .passwordParameter("mpassword")//user의 비밀번호로 어떤 값(mpassword)를 사용할 것인지 설정
                .and()
                .logout() // 로그아웃
                .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout")) // 로그아웃이 요청되는 ul
                .logoutSuccessUrl("/") // 로그아웃 성공시 호출
                .invalidateHttpSession(true) // 로그아웃 성공시 세션 초기화
                .and()
                .csrf()// 변조등의 해킹의 위험에 대비하여 데이터를 주고받을 페이지를 설정
                .ignoringAntMatchers("/member/logincontroller") // 매핑된 주소에서의 사용자가 데이터를 보내고 요청하는 것은 가능
                .ignoringAntMatchers("/member/signupcontroller");


    }

    @Autowired
    private IndexService indexService;

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(indexService).passwordEncoder(new BCryptPasswordEncoder() );
        // 패스워드를 BCyrpt로 인코딩
    }
}
