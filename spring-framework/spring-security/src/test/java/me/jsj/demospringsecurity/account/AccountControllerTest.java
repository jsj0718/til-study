package me.jsj.demospringsecurity.account;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AccountControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    AccountService accountService;

    /**
     * 익명의 유저가 index 페이지에 접속한 경우
     */
    @Test
    void index_anonymous() throws Exception {
        mvc.perform(get("/").with(anonymous()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    void index_anonymousV2() throws Exception {
        mvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    /**
     * (username: jsj, roles: USER)인 유저가 로그인 후 index 페이지에 접속한 경우
     * with(user("jsj").roles("USER"))는 모킹한 것
     */
    @Test
    void index_user() throws Exception {
        mvc.perform(get("/").with(user("jsj").roles("USER")))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "jsj", roles = "USER")
    void index_userV2() throws Exception {
        mvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithUser
    void index_userV3() throws Exception {
        mvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    /**
     * (username: jsj, roles: USER)인 유저가 로그인 후 admin 페이지에 접속한 경우 => 403 FORBIDDEN
     */
    @Test
    void admin_user() throws Exception {
        mvc.perform(get("/admin").with(user("jsj").roles("USER")))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "jsj", roles = "ADMIN")
    void admin_userV2() throws Exception {
        mvc.perform(get("/admin"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithAdmin
    void admin_userV3() throws Exception {
        mvc.perform(get("/admin"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    void login_success() throws Exception {
        String username = "jsj";
        String password = "123";
        Account user = createUser(username, password);

        mvc.perform(formLogin().user(user.getUsername()).password(password))
                .andExpect(authenticated());
    }

    @Test
    @Transactional
    void login_fail() throws Exception {
        String username = "jsj";
        String password = "123";
        Account user = createUser(username, password);

        mvc.perform(formLogin().user(user.getUsername()).password("12345"))
                .andExpect(unauthenticated());
    }

    private Account createUser(String username, String password) {
        Account account = new Account(username, password, "USER");
        return accountService.createNewAccount(account);
    }
}