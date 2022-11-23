package hello.servlet.web.springmvc.v2;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/springmvc/v2/members")
public class SpringMemberControllerV2 {

    private MemberRepository memberRepository = MemberRepository.getInstance();

    @RequestMapping("/new-form")
    public ModelAndView newForm() {
        return new ModelAndView("views/new-form");
    }

    @RequestMapping("/save")
    public ModelAndView saveMember(HttpServletRequest request) {
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));

        Member member = new Member(username, age);
        memberRepository.save(member);

        ModelAndView modelAndView = new ModelAndView("views/save-result");
        modelAndView.addObject("member", member);
        return modelAndView;
    }


    @RequestMapping
    public ModelAndView findAllMembers() {
        List<Member> members = memberRepository.findAll();
        ModelAndView modelAndView = new ModelAndView("views/members");
        modelAndView.addObject("members", members);
        return modelAndView;
    }

}
