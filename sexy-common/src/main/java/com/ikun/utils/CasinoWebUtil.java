package com.ikun.utils;

import com.ikun.constant.Constants;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class CasinoWebUtil {

    public final static String salt = "f44grgr";
    public final static String auth_header = "authorization";

    //获取当前操作者的身份
    public static Long getAuthId() {
        String token = getToken();
        if (ObjectUtils.isEmpty(token)) {
            return null;
        }
        JjwtUtil.Subject subject = JjwtUtil.getSubject(token);
        if (subject == null) {
            return null;
        }
        String userId = subject.getUserId();
        if (ObjectUtils.isEmpty(userId)) {
            return null;
        }
        return Long.parseLong(userId);
//        return getAuthId(token);
    }

    //获取当前操作者的身份
    public static Long getAuthId(String token) {
        JjwtUtil.Subject subject = JjwtUtil.parse(token,  Constants.CASINO_WEB);
        if (subject == null) {
            return null;
        }
        String userIds = subject.getUserId();
        Long userId = null;
        if (!CasinoWebUtil.checkNull(userIds)) {
            userId = Long.parseLong(userIds);
        }
        return userId;
    }

    public static boolean checkNull(Object... obj) {
        if (obj == null || obj.length < 1) {
            return true;
        }
        for (Object v : obj) {
            if (ObjectUtils.isEmpty(v)) {
                return true;
            }
        }
        return false;
    }

    //加密
    public static String bcrypt(String value) {
        return BCrypt.hashpw(value, BCrypt.gensalt());
    }

    //校验加密
    public static boolean checkBcrypt(String value, String bcryptValue) {
        if (checkNull(value) || checkNull(bcryptValue)) {
            return false;
        }
        return BCrypt.checkpw(value, bcryptValue);
    }

    public static String getCaptchaKey(HttpServletRequest request, String code) {
        String ip = IpUtil.getIp(request);
        return ip + code;
    }

    public static String getToken() {
        String token = getRequest().getHeader(auth_header);
        if (!checkNull(token)) {
            token = token.replaceAll("Bearer ", "");
        }
        return token;
    }

    public static String getToken(String token) {
        if (!checkNull(token)) {
            token = token.replaceAll("Bearer ", "");
        }
        return token;
    }

    public static HttpServletRequest getRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes attributes = (ServletRequestAttributes) requestAttributes;
        return attributes.getRequest();
    }

    public static Pageable setPageable(Integer pageCode, Integer pageSize, Sort sort) {

        if (pageSize == null || pageCode == null) {
            pageCode = 1;
            pageSize = 10;
        }

        if (pageCode < 1 || pageSize < 1) {
            pageCode = 1;
            pageSize = 10;
        }

        if (pageSize > 100) {
            pageSize = 100;
        }

        Pageable pageable = PageRequest.of(pageCode - 1, pageSize, sort);
        return pageable;
    }

    public static Pageable setPageable(Integer pageCode, Integer pageSize) {

        return setPageable(pageCode, pageSize, null);
    }



    public static void main(String[] args) {
        System.out.println(bcrypt("123456"));

    }
}