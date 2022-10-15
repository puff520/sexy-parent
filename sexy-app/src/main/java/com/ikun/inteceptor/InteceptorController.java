package com.ikun.inteceptor;


import com.ikun.annotation.NoAuthentication;
import com.ikun.result.Result;
import com.ikun.result.ResultCode;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InteceptorController implements ErrorController {

    @NoAuthentication
    @RequestMapping("error")
    public Result error() {
        return Result.failed();
    }

    @RequestMapping("authenticationBan")
    public Result authenticationBan() {
        return Result.failed("帐号被封");
    }

    @NoAuthentication
    @RequestMapping("authenticationNoPass")
    public Result authenticationNoPass() {
        return Result.failed(ResultCode.LIVE_ERROR_401);
    }

    @NoAuthentication
    @RequestMapping("authenticationMultiDevice")
    public Result authenticationMultiDevice() {
        return Result.failed(ResultCode.RESOURCE_NOT_FOUND2);
    }



}
