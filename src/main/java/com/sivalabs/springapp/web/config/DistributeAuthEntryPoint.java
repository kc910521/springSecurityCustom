package com.sivalabs.springapp.web.config;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by robu on 2017/1/3.
 * 进行ajax和普通请求权限不足的分流处理
 */
public class DistributeAuthEntryPoint extends LoginUrlAuthenticationEntryPoint {

    public DistributeAuthEntryPoint(String loginFormUrl) {
        super(loginFormUrl);
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        String requestType = request.getHeader("X-Requested-With");
        if ("XMLHttpRequest".equals(requestType)){
            response.getWriter().write("{\"code\":8}");
        }else{
            super.commence(request, response, authException);
        }


    }
}
