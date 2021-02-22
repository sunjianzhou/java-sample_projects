package com.jdicity.gateway.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Write description here.
 *
 * @author qixinyuan3
 * @date
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("api_back_parameter")
@ApiModel(value = "ApiBackParameter", description = "api后端参数")
public class ApiBackParameter implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id值
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "后端参数表的主键id", required = true)
    private Long id;

    /**
     * 后端参数名称
     */
    @ApiModelProperty(value = "后端参数名称", required = true)
    private String parameterName;

    /**
     * 后端参数位置
     */
    @ApiModelProperty(value = "后端参数位置：Path,Header,Body,Query", required = true)
    private String parameterPos;

    /**
     * 前端参数名称
     */
    @ApiModelProperty(value = "前端参数名称", required = true)
    private String frontParaName;

    /**
     * 前端参数位置
     */
    @ApiModelProperty(value = "前端参数位置：Path,Header,Body,Query", required = true)
    private String frontParaPos;

    /**
     * 描述信息
     */
    @TableField(strategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "描述信息", required = false)
    private String description;

    /**
     * api的id
     */
    @ApiModelProperty(value = "api的主键id", required = true)
    private Long apiId;

}
