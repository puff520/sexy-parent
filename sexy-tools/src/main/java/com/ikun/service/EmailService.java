
package com.ikun.service;

import com.ikun.base.CommonService;
import com.ikun.domain.vo.EmailVo;
import com.ikun.domain.EmailConfig;

/**
 * @author Zheng Jie
 * @date 2018-12-26
 */
public interface EmailService extends CommonService<EmailConfig> {

    /**
     * 更新邮件配置
     * @param emailConfig 邮箱配置
     * @param old /
     * @return /
     * @throws Exception /
     */
    EmailConfig config(EmailConfig emailConfig, EmailConfig old) throws Exception;

    /**
     * 查询配置
     * @return EmailConfig 邮件配置
     */
    EmailConfig find();

    /**
     * 发送邮件
     * @param emailVo 邮件发送的内容
     * @param emailConfig 邮件配置
     * @throws Exception /
     */
    void send(EmailVo emailVo, EmailConfig emailConfig);
}
