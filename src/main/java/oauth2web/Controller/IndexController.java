package oauth2web.Controller;

import oauth2web.dto.MemberDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import oauth2web.service.IndexService;

@Controller
public class IndexController {


    @Autowired
    IndexService indexService;

    @GetMapping("/") String main() {return "main";}

    @GetMapping("/member/login") String login() {return "login";}

    @GetMapping("/member/signup")String signup() {return "signup";}

    @PostMapping("/member/signupcontroller") public String signupcontroller(MemberDto memberDto)
    {indexService.signup(memberDto); return "redirect:/";}
}
