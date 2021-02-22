package com.jdicity.gateway.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("api_front_parameter")
@ApiModel(value = "ApiFrontParameter", description = "api前端参数")
public class ApiFrontParameter implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 前端参数id
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "前端参数表主键id", required = true)
    private Long id;

    /**
     * 参数名称
     */
    @ApiModelProperty(value = "前端参数名称", required = true)
    private String parameterName;

    /**
     * 参数位置
     */
    @ApiModelProperty(value = "前端参数位置：Path,Header,Body,Query", required = true)
    private String parameterPos;

    /**
     * 数据类型
     */
    @ApiModelProperty(value = "数据类型：String,Int", required = true)
    private String dataType;

    /**
     * 是否必须
     */
    @ApiModelProperty(value = "是否必须带此参数：是,否", required = true)
    private String required;

    /**
     * 参数描述
     */
    @ApiModelProperty(value = "参数描述信息", required = false)
    private String description;

    /**
     * api的id
     */
    @ApiModelProperty(value = "数据库表的主键", required = true)
    private Long apiId;

}
