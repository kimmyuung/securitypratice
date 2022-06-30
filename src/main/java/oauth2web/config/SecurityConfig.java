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
                .authorizeRequests()
                .antMatchers("/**").permitAll()
                .and()
                .formLogin()
                .loginPage("/member/login")
                .loginProcessingUrl("/member/logincontroller")
                .defaultSuccessUrl("/")// 로그인 성공시 이동할 URL
                .failureUrl("/member/login/error")
                .usernameParameter("mid")
                .passwordParameter("mpassword")
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .and()
                .csrf()
                .ignoringAntMatchers("/member/logincontroller")
                .ignoringAntMatchers("/member/signupcontroller");


    }

    @Autowired
    private IndexService indexService;

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(indexService).passwordEncoder(new BCryptPasswordEncoder() );
    }
}
