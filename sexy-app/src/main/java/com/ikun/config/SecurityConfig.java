package com.ikun.config;

import com.ikun.config.security.filter.MyAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

/**
 * @author Zheng Jie
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyAuthenticationFilter myAuthenticationFilter;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests().anyRequest().permitAll().and().logout().permitAll();
        http.addFilterBefore(myAuthenticationFilter, BasicAuthenticationFilter.class);
    }
}
