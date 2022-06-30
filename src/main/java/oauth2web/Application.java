package oauth2web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
    public static void main(String[] args) { //  메인 스레드(코드를 읽어주는 역할)
        SpringApplication.run( Application.class  ); // 내장서버(톰캣) 스프링 시작
    }
}
