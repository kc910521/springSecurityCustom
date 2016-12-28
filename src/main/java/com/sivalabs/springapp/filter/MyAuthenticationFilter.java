package com.sivalabs.springapp.filter;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by robu on 2016/12/27.
 */
public class MyAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String dyCode = this.findVertifyCode(request);
        System.out.println(dyCode+"--dy_code00000000000000000000000000000000000000000000000000000");
        if (!"888".equals(dyCode)){
            //直接中断验证
            throw new AuthenticationServiceException("ERROR Dy-Code value in:" + dyCode);
        }
        return super.attemptAuthentication(request, response);
    }

    protected String findVertifyCode(HttpServletRequest request){
        return  request.getParameter("dy_code");
    }
}
