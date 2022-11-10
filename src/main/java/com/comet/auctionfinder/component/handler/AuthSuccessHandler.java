package com.comet.auctionfinder.component.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
@Slf4j
public class AuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    //super 메소드 제거 안하면 리다이렉트 계속 발생.
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("login success! {}", ((User)authentication.getPrincipal()).getUsername());
        RequestCache cache = new HttpSessionRequestCache(); //스프링 시큐리티 캐시
        SavedRequest savedRequest = cache.getRequest(request, response);
        HttpSession session = request.getSession();
        if (session != null) {
            log.info("session : {}", session);
            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION); //에러 attribute 제거
        }
        String redirect = "url:"; //리다이렉트 uri
        if (savedRequest != null) {
            redirect += savedRequest.getRedirectUrl();
            cache.removeRequest(request, response);
        }
        else
            redirect += "/mypage";
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        response.getWriter().print(redirect); //ajax방식이므로 강제 리다이렉트 x
    }
}
