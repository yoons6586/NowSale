package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity /* WebConfig 컴포넌트 등록 */
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//    @Autowired UserService userService;

    private static final String[] AUTH_WHITELIST = {
            // -- swagger ui
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/v2/api-docs",
            "/webjars/**",
            "/all/**",
            "/client/**",
            "/owner/**"
    };
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable().authorizeRequests()
                .antMatchers(AUTH_WHITELIST).permitAll()
                .anyRequest().permitAll()


//                .antMatchers(HttpMethod.POST,".client").permitAll()
//                .and()
//                .formLogin()
//                .and()
//                .httpBasic()
        ;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userService) .passwordEncoder(userService.passwordEncoder());
//    }

//    @Bean
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }

}


//    @Autowired
//    UserDetailsService userDetailsService;
//
//    @Autowired
//    PasswordEncoder passwordEncoder;
//
//    /* Password Encoder 등록 */
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//
//    /* 인증방식 */
//    @Autowired
//    public void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
//    }
//
//    /* Security 제외 패턴 */
//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        web.ignoring()
//                .antMatchers("/resources/**");
//    }
//
//    /* 각종 시큐어 패턴등록 */
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests() /* 인증 요청 선언?????? */
//
//                .antMatchers("/access").hasRole("ADMIN") /* /access 패턴은 ADMIN 유저만 접근 */
//                .and()
//
//                .formLogin(); /* 로그인 폼 나오도록 */
//
//    }