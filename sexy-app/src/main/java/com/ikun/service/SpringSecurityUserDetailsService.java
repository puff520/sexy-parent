package com.ikun.service;

import com.ikun.service.domain.MemBaseInfo;
import com.ikun.service.mem.MemBaseInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service("userDetailsService")
public class SpringSecurityUserDetailsService implements UserDetailsService {

    @Autowired
    private MemBaseInfoService userService;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        MemBaseInfo user = userService.getMemBaseInfoByUserName(userName);
        if(user != null){
            return user;
        }
        log.info("User is {}",user);
        throw new UsernameNotFoundException("帐号或密码错误");
    }

    public UserDetails getUserDetaisByUserId(Long userid){
        MemBaseInfo user = userService.getMemBaseInfoById(userid);
        if(user == null){
            throw new BadCredentialsException("TOKEN已过期，请重新登录!");
        }
        return user;
    }
}
