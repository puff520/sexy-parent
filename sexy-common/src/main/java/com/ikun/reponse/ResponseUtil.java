package com.ikun.reponse;

import org.springframework.stereotype.Component;

@Component
public class ResponseUtil {


    public static ResponseEntity error() {
        return new ResponseEntity(ResponseCode.ERROR);
    }

    public static ResponseEntity success() {
        return new ResponseEntity(ResponseCode.SUCCESS);
    }

    public static ResponseEntity success(Object data) {
        return new ResponseEntity(ResponseCode.SUCCESS, data);
    }

    public static ResponseEntity successMsg(String msg) {
        return new ResponseEntity(ResponseCode.SUCCESS, msg);
    }

    public static ResponseEntity successMsg(Object data, String msg) {
        return new ResponseEntity(ResponseCode.SUCCESS.getCode(), msg, data);
    }


    public static ResponseEntity requestLimit() {
        return new ResponseEntity(ResponseCode.REQUEST_LIMIT, ResponseCode.REQUEST_LIMIT.getMsg());
    }


    public static ResponseEntity customMsg(String msg) {
        return new ResponseEntity(msg);
    }


    public static ResponseEntity fail() {
        return new ResponseEntity(ResponseCode.FAIL);
    }

    public static ResponseEntity authenticationNopass() {
        return new ResponseEntity(ResponseCode.AUTHENTICATION_NOPASS, ResponseCode.AUTHENTICATION_NOPASS.getMsg());
    }
}
