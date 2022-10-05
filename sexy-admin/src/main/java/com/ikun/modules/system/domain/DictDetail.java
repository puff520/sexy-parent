package com.ikun.modules.system.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.IdType;
import com.ikun.modules.system.service.dto.DictSmallDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import com.ikun.base.CommonEntity;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
* @author jinjin
* @date 2020-09-24
*/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("sys_dict_detail")
public class DictDetail extends CommonEntity<DictDetail> implements Serializable {

    @ApiModelProperty(value = "ID")
    @TableId(value = "detail_id", type= IdType.AUTO)
    private Long id;

    @NotNull
    private Long dictId;

    @ApiModelProperty(value = "字典id")
    @NotNull
    @TableField(exist = false)
    private DictSmallDto dict;

    @ApiModelProperty(value = "字典标签")
    @NotBlank
    private String label;

    @ApiModelProperty(value = "字典值")
    @NotBlank
    private String value;

    @ApiModelProperty(value = "排序")
    private Integer dictSort;

    public void copyFrom(DictDetail source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
