package me.jsj.demospringsecurity.form;

import me.jsj.demospringsecurity.account.Account;
import me.jsj.demospringsecurity.account.AccountContext;
import me.jsj.demospringsecurity.common.SecurityLogger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class SampleService {

    public void dashboard() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal(); //사용자 정보
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities(); //권한 정보
        Object credentials = authentication.getCredentials();
        boolean authenticated = authentication.isAuthenticated();
    }

    public void dashboardV2() {
        Account account = AccountContext.getAccount();
        System.out.println(">>>>> Account Name: " + account.getUsername());
    }

    @Secured("ROLE_USER")
    public String dashboardV3() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        System.out.println(">>>>> username: " + userDetails.getUsername());
        return userDetails.getUsername();
    }

    /**
     * @Asunc
     * - 별도의 쓰레드 생성 후 비동기적으로 처리
     */
    @Async
    public void asyncService() {
        SecurityLogger.log("AsyncService");
        System.out.println("Async service is called.");
    }
}
