package oauth2web.Controller;

import oauth2web.dto.MemberDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import oauth2web.service.IndexService;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
// 컨트롤러로 사용
public class IndexController {


    @Autowired
    IndexService indexService;
    // 자동 객체 생성

    @GetMapping("/") String main() {return "main";} // 시작하면 요청되는 페이지에 대한 매핑

    @GetMapping("/member/login") String login() {return "login";} // 로그인 페이지로 이동하는 매핑

    @GetMapping("/member/signup")String signup() {return "signup";} // 회원가입 페이지로 이동하는 매핑

    @PostMapping("/member/signupcontroller") public String signupcontroller(MemberDto memberDto)
    // post 메소드로 실행되는 회원가입 실행에 대한 매핑
    {indexService.signup(memberDto); return "redirect:/";}

    @GetMapping("/member/info") // 회원정보 페이지로 이동
    @ResponseBody // 템플릿이 아닌 것을 반환하기 위해 사용 , RestController는 필요 X
    public String getMemberInfo() {return indexService.authenticationget();} // 아이디 반환
}
