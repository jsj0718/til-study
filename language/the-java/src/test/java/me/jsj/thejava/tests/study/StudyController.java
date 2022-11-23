package me.jsj.thejava.tests.study;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequiredArgsConstructor
@Controller
public class StudyController {

    @Autowired
    StudyRepository studyRepository;

    @GetMapping("/study/new-form")
    public String newForm(Model model) {
        model.addAttribute("name", "강의 이름");
        model.addAttribute("limit", 0);
        return "study/new-form";
    }

    @ResponseStatus(HttpStatus.PERMANENT_REDIRECT)
    @GetMapping("/study/redirect-new-form")
    public String redirectNewForm() {
        return "redirect:study/new-form";
    }
}
