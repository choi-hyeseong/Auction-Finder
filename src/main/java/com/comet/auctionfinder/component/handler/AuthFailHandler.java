package com.comet.auctionfinder.component.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class AuthFailHandler extends SimpleUrlAuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String errorMessage = "";
        log.warn("login failed : {}", exception.getLocalizedMessage());
        if (exception instanceof BadCredentialsException)
            errorMessage = "아이디 또는 비밀번호가 틀립니다.";
        else if (exception instanceof LockedException)
            errorMessage = "잠긴 계정입니다.";
        else if (exception instanceof DisabledException)
            errorMessage = "비활성화된 계정입니다.";
        else if (exception instanceof AccountExpiredException)
            errorMessage = "만료된 계정입니다.";
        else if (exception instanceof CredentialsExpiredException)
            errorMessage = "비밀번호가 만료되었습니다.";
        else
            errorMessage = "존재하지 않는 사용자입니다.";
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        response.getWriter().print(errorMessage);
    }

}
