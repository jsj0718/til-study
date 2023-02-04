package me.jsj.demospringsecurity.form;

import me.jsj.demospringsecurity.account.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.test.context.support.WithMockUser;

@SpringBootTest
class SampleServiceTest {

    @Autowired
    SampleService sampleService;

    @Autowired
    AccountService accountService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void dashboardTest() {
/*
        //계정 생성
        Account account = new Account();
        account.setUsername("jsj");
        account.setPassword("123");
        account.setRole("USER");
        accountService.createNewAccount(account);

        //토큰 생성 후 인증 객체 생성
        UserDetails userDetails = accountService.loadUserByUsername("jsj");

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, "123");
        Authentication authentication = authenticationManager.authenticate(token);

        //인증 진행
        SecurityContextHolder.getContext().setAuthentication(authentication);
*/

        //메소드 시큐리티 접근
        sampleService.dashboardV3();
    }
}