
package com.ikun.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import com.ikun.base.CommonModel;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 代码生成配置
 * @author Zheng Jie
 * @date 2019-01-03
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("code_gen_config")
public class GenConfig extends CommonModel<GenConfig> implements Serializable {

    public GenConfig(String tableName) {
        this.tableName = tableName;
    }

    @ApiModelProperty(value = "ID", hidden = true)
    @TableId(value = "config_id", type= IdType.AUTO)
    private Long id;

    @NotBlank
    @ApiModelProperty(value = "表名")
    private String tableName;

    @ApiModelProperty(value = "接口名称")
    private String apiAlias;

    @NotBlank
    @ApiModelProperty(value = "包路径")
    private String pack;

    @NotBlank
    @ApiModelProperty(value = "模块名")
    private String moduleName;

    @NotBlank
    @ApiModelProperty(value = "前端文件路径")
    private String path;

    @ApiModelProperty(value = "前端文件路径")
    private String apiPath;

    @ApiModelProperty(value = "作者")
    private String author;

    @ApiModelProperty(value = "表前缀")
    private String prefix;

    @ApiModelProperty(value = "是否覆盖")
    private Boolean cover = false;
}
