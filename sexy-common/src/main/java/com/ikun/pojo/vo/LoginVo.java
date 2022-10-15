package com.ikun.pojo.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class LoginVo implements Serializable {

    private String userName;
    private String password;
}
