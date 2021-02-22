package com.jdicity.gateway.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Write description here.
 *
 * @author qixinyuan3
 * @date
 */


@Data
@TableName("constant_parameter")
@ApiModel(value = "ConstantParameter", description = "api常量参数")
public class ConstantParameter implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id字段
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "常量表的主键id", required = true)
    private Long id;

    /**
     * 参数名称
     */
    @ApiModelProperty(value = "参数名称", required = true)
    private String parameterName;

    /**
     * 参数位置
     */
    @ApiModelProperty(value = "参数位置：Path,Header,Body,Query", required = true)
    private String parameterPos;

    /**
     * 数据类型
     */
    @ApiModelProperty(value = "数据类型：String,Int", required = true)
    private String dataType;

    /**
     * 是否必须
     */
    @ApiModelProperty(value = "是否必须携带该参数：是,否", required = true)
    private String required;

    /**
     * 参数数值
     */
    @TableField(strategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "参数数值", required = false)
    private String value;

    /**
     * 描述
     */
    @TableField(strategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "描述信息", required = false)
    private String description;

    /**
     * api的id
     */
    @ApiModelProperty(value = "api的id", required = true)
    private Long apiId;

}
