package com.ikun.modules.mnt.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import com.ikun.base.CommonModel;

import java.io.Serializable;

/**
 *
 * Created by jinjin on 2020-09-30.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(value = { "handler" })
@TableName("mnt_deploy_server")
@ApiModel(value="DeploysServers对象", description="应用与服务器部署关联")
public class DeploysServers extends CommonModel<DeploysServers> implements Serializable{
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "部署ID")
    @TableField(value = "deploy_id")
    private Long deployId;

    @ApiModelProperty(value = "服务器ID")
    @TableField(value = "server_id")
    private Long serverId;

}
