package com.comet.auctionfinder.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Map;

@Slf4j
public class RestInterceptor implements AsyncHandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getParameterMap().isEmpty())
            log.info("url : {}", request.getRequestURL());
        else
            log.info("url : {} | param : {}", request.getRequestURL(), mapFlatter(request.getParameterMap()));
        return AsyncHandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        log.info("response : {}", response.getStatus());
    }

    private String mapFlatter(Map<String, String[]> input) {
        StringBuilder builder = new StringBuilder(); //비동기지만 객체 생성이므로 싱글쓰레드로 판단.
        for (String key : input.keySet()) { //pro : [경북, 서울], city : [김천, 구미] 대충 이렇게?
            String[] value = input.get(key);
            builder.append(key).append(" : ").append(Arrays.toString(value)).append(" ");
        }
        return builder.toString();

    }

}
