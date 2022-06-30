package oauth2web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
// 스프링부트를 시작 하는 애플리케이션 java / 프로젝트명 / appiocation 최상단에 위치해야 한다.
public class Application {
    public static void main(String[] args) { //  메인 스레드(코드를 읽어주는 역할)
        SpringApplication.run( Application.class  ); // 내장서버(톰캣) 스프링 시작
    }
}
