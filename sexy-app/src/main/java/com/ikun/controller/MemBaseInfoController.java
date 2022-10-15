package com.ikun.controller;

import com.ikun.annotation.NoAuthentication;
import com.ikun.service.domain.MemBaseInfo;
import com.ikun.service.dto.LoginDto;
import com.ikun.result.Result;
import com.ikun.service.mem.MemBaseInfoService;
import com.ikun.utils.CasinoWebUtil;
import com.ikun.utils.IpUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Api(tags = "会员中心")
@RestController
@RequestMapping("/app/mem")
@RequiredArgsConstructor
public class MemBaseInfoController {

    @Resource
    private final MemBaseInfoService memBaseInfoService;

    @ApiOperation("app登录")
    @PostMapping(value = "/login")
    @NoAuthentication
    public Result login(@RequestBody LoginDto loginDto) {
        return memBaseInfoService.login(loginDto);
    }

    @ApiOperation("会员信息")
    @GetMapping(value = "/info")
    public Result memInfo(HttpServletRequest request) {
        Long authId = CasinoWebUtil.getAuthId();
        String address = IpUtil.getAddress(IpUtil.getIp(request));
        System.out.println(address);
        MemBaseInfo memBaseInfo = memBaseInfoService.memInfo("ycp1");
        return Result.success(memBaseInfo);
    }

}
