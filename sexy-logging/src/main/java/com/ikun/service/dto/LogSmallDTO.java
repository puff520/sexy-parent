
package com.ikun.service.dto;

import lombok.Data;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @author Zheng Jie
 * @date 2019-5-22
 */
@Data
public class LogSmallDTO implements Serializable {

    private String description;

    private String requestIp;

    private Long time;

    private String address;

    private String browser;

    private Date createTime;
}
