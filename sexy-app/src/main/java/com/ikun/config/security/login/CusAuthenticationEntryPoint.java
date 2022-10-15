package com.ikun.config.security.login;

import com.ikun.config.security.util.ResponseUtils;
import com.ikun.reponse.ResponseUtil;
import com.ikun.result.Result;
import com.ikun.utils.ApiResult;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CusAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        if(e!=null){
            ResponseUtils.out(httpServletResponse, ResponseUtil.authenticationNopass());
        }else{
            ResponseUtils.out2(httpServletResponse, Result.failed("jwtToken过期!"));
        }
    }
}
