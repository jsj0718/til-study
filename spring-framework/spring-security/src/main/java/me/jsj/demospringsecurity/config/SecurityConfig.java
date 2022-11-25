package me.jsj.demospringsecurity.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;

@Configuration
//@EnableWebSecurity //Spring Boot에서 자동으로 추가
@Order(Ordered.LOWEST_PRECEDENCE - 1)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

/*
    public AccessDecisionManager accessDecisionManager() {
        // Manager > Voters > Handler 구조
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_USER");

        DefaultWebSecurityExpressionHandler handler = new DefaultWebSecurityExpressionHandler();
        handler.setRoleHierarchy(roleHierarchy);

        WebExpressionVoter webExpressionVoter = new WebExpressionVoter();
        webExpressionVoter.setExpressionHandler(handler);

        List<AccessDecisionVoter<? extends Object>> voters = Arrays.asList(webExpressionVoter);
        return new AffirmativeBased(voters);
    }
*/

    public SecurityExpressionHandler expressionHandler() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_USER");

        DefaultWebSecurityExpressionHandler handler = new DefaultWebSecurityExpressionHandler();
        handler.setRoleHierarchy(roleHierarchy);

        return handler;
    }

    /**
     * 정적 자원 허용 => 필터 X
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    /**
     * 동적 자원 조건별 허용 => 필터 O
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .mvcMatchers("/", "/info", "/account/**", "/signup").permitAll()
                .mvcMatchers("/admin").hasRole("ADMIN")
                .mvcMatchers("/user").hasRole("USER")
                .anyRequest().authenticated()
//                .accessDecisionManager(accessDecisionManager());
                .expressionHandler(expressionHandler());
        http.formLogin()
                .loginPage("/login") //추가 시 default로 제공되는 login과 logout 페이지는 사용할 수 없다.
                .usernameParameter("my-username") //default: username
                .passwordParameter("my-password")
                .permitAll(); //default: password
        http.httpBasic();

        //하위 쓰레드까지 SpringContextHolder 내용 유지 (Default는 ThreadLocal)
//        SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);

        //CSRF Filter 비활성화
//        http.csrf().disable();

        http.logout()
                .logoutUrl("/logout") //default: /logout
                .logoutSuccessUrl("/"); //default: /login

        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .sessionFixation()
                    .changeSessionId()
                .maximumSessions(1)
                    .maxSessionsPreventsLogin(true);
    }

/*
    //인메모리에 계정 추가하는 방법
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //{} 안에 인코더를 설정하면 입력된 값과 암호화되어 저장된 패스워드를 비교해 같은 값인지를 알 수 있다.
        //{noop}은 암호화 인코더를 설정하지 않았다는 의미
        auth.inMemoryAuthentication()
                .withUser("jsj1275").password("{noop}123").roles("USER").and()
                .withUser("admin").password("{noop}!@#").roles("ADMIN");
    }
*/
}
