package com.comet.auctionfinder.config;

import com.comet.auctionfinder.component.handler.AuthFailHandler;
import com.comet.auctionfinder.component.handler.AuthSuccessHandler;
import com.comet.auctionfinder.service.LoginService;
import com.comet.auctionfinder.service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@AllArgsConstructor
@Configuration
public class SpringSecurityConfig {

    private LoginService loginService;
    private AuthFailHandler failHandler;
    private AuthSuccessHandler successHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf() //이거 비활성화 해줘야 post 요청됨.. 칫
                .disable()
                .headers()
                .frameOptions()
                .disable()//h2-console
                .and()
                .userDetailsService(loginService)
                .authorizeRequests(auth ->

                        auth.mvcMatchers("/login")
                                .permitAll()
                                .mvcMatchers("/login/**")
                                .permitAll()
                                .mvcMatchers("/")
                                .permitAll()
                                .mvcMatchers("/main")
                                .permitAll()
                                .mvcMatchers("/map")
                                .permitAll()
                                .mvcMatchers("/register")
                                .permitAll()
                                .mvcMatchers("/api/**")
                                .permitAll()
                                .mvcMatchers("/assets/**")
                                .permitAll()
                                .mvcMatchers("/css/**")
                                .permitAll()
                                .mvcMatchers("/js/**")
                                .permitAll()
                                .mvcMatchers("/mypage/**")
                                .hasRole("USER")
                                .anyRequest()
                                .authenticated()

                )
                .formLogin(login -> login.loginPage("/login") //폼 형식 로그인
                        .usernameParameter("username") //username : ~
                        .passwordParameter("password") //password : ~
                        .loginProcessingUrl("/login")
                        .successHandler(successHandler)
                        .failureHandler(failHandler)
                        .permitAll())
                .logout()
                .logoutUrl("/logout")
                .invalidateHttpSession(true); //로그아웃 성공시

        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
