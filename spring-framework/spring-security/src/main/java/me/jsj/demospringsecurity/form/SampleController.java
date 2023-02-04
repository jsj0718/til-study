package me.jsj.demospringsecurity.form;

import lombok.RequiredArgsConstructor;
import me.jsj.demospringsecurity.account.Account;
import me.jsj.demospringsecurity.account.AccountContext;
import me.jsj.demospringsecurity.account.AccountRepository;
import me.jsj.demospringsecurity.account.UserAccount;
import me.jsj.demospringsecurity.book.BookRepository;
import me.jsj.demospringsecurity.common.CurrentUser;
import me.jsj.demospringsecurity.common.SecurityLogger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.concurrent.Callable;

@RequiredArgsConstructor
@Controller
public class SampleController {

    private final SampleService sampleService;

    private final AccountRepository accountRepository;

    private final BookRepository bookRepository;

    @GetMapping("/v1")
    public String index(Model model, Principal principal) {
        if (principal == null) {
            model.addAttribute("message", "Hello, Spring Security Index V1");
        } else {
            model.addAttribute("message", "Hello, " + principal.getName());
        }
        return "index";
    }

    @GetMapping("/v2")
    public String indexV2(Model model, @AuthenticationPrincipal UserAccount userAccount) {
        if (userAccount == null) {
            model.addAttribute("message", "Hello, Spring Security Index V2");
        } else {
            model.addAttribute("message", "Hello, " + userAccount.getUsername());
        }
        return "index";
    }

    @GetMapping("/v3")
    public String indexV3(Model model, @AuthenticationPrincipal(expression = "#this == 'anonymousUser' ? null : account") Account account) {
        if (account == null) {
            model.addAttribute("message", "Hello, Spring Security Index V3");
        } else {
            model.addAttribute("message", "Hello, " + account.getUsername());
        }
        return "index";
    }

    @GetMapping("/")
    public String indexV4(Model model, @CurrentUser Account account) {
        if (account == null) {
            model.addAttribute("message", "Hello, Spring Security Index V4");
        } else {
            model.addAttribute("message", "Hello, " + account.getUsername());
        }
        return "index";
    }

    @GetMapping("/info")
    public String info(Model model) {
        model.addAttribute("message", "Info");
        return "info";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, Principal principal) {
        model.addAttribute("message", "Hello, " + principal.getName());
        sampleService.dashboard();
        return "dashboard";
    }

    @GetMapping("/dashboard-v2")
    public String dashboardV2(Model model, Principal principal) {
        model.addAttribute("message", "Hello, " + principal.getName());
        AccountContext.setAccount(accountRepository.findByUsername(principal.getName()));
        sampleService.dashboardV2();
        return "dashboard";
    }

    @GetMapping("/dashboard-v3")
    public String dashboardV3(Model model) {
        String username = sampleService.dashboardV3();
        model.addAttribute("message", "Hello, " + username);
        return "dashboard";
    }

    @GetMapping("/admin")
    public String admin(Model model, Principal principal) {
        model.addAttribute("message", "Hello Admin, " + principal.getName());
        return "admin";
    }

    @GetMapping("/user-v1")
    public String userV1(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        model.addAttribute("message", "Hello User, " + userDetails.getUsername());
        return "user";
    }

    @GetMapping("/user")
    public String userV2(Model model, Principal principal) {
        model.addAttribute("message", "Hello User, " + principal.getName());
        model.addAttribute("books", bookRepository.findCurrentUserBooks());
        return "user";
    }

    @GetMapping("/async-handler")
    @ResponseBody
    public Callable<String> asyncHandler() {
        SecurityLogger.log("MVC");
        return () -> {
            SecurityLogger.log("Callable");
            return "Async Handler";
        };
    }

    @GetMapping("/async-service")
    @ResponseBody
    public String asyncService() {
        SecurityLogger.log("MVC, before async service");
        sampleService.asyncService();
        SecurityLogger.log("MVC, after async service");
        return "Async Service";
    }

}
