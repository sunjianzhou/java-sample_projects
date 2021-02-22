package com.jdicity.gateway.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("api_app")
@ApiModel(value = "ApiApp", description = "授权表")
public class ApiApp implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 表的主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "授权表的主键id", required = true)
    private Long id;

    /**
     * api的id值
     */
    @ApiModelProperty(value = "api的主键id", required = true)
    private Long apiId;

    /**
     * 应用的id值
     */
    @ApiModelProperty(value = "application的主键id", required = true)
    private Long appId;

}
