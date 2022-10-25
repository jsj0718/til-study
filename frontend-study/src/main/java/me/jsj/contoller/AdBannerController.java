package me.jsj.contoller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdBannerController {

    @GetMapping("/ad-banner")
    public String adBanner() {
        return "ad-banner";
    }

}
