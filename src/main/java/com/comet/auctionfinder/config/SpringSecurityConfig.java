package com.comet.auctionfinder.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SpringSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf() //이거 비활성화 해줘야 post 요청됨.. 칫
            .disable()
            .headers()
            .frameOptions()
            .disable()//h2-console
            .and()
            .authorizeRequests(auth -> auth.anyRequest().permitAll())
            /*auth.mvcMatchers("/login")
                                           .permitAll()
                                           .mvcMatchers("/login/**")
                                           .permitAll()
                                           .mvcMatchers("/main")
                                           .permitAll()
                                           .mvcMatchers("/register")
                                           .permitAll()
                                           .antMatchers("/h2-console/**")
                                           .permitAll()
                                           .antMatchers("/board")
                                           .hasAuthority("USER")
                                           .anyRequest()
                                           .authenticated()

             */
            .formLogin(login -> login.loginPage("/login") //폼 형식 로그인
                                     .usernameParameter("username") //username : ~
                                     .passwordParameter("password") //password : ~
                                     .loginProcessingUrl("/login")
                                     .permitAll());

        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
