package me.jsj.demospringsecurity.account;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/account/{username}/{password}/{role}")
    public Account createAccountByGet(@ModelAttribute Account account) {
        return accountService.createNewAccount(account);
    }
}
