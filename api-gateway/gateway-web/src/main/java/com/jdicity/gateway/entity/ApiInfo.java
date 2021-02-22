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
@TableName("api_info")
@ApiModel(value = "ApiInfo", description = "api基本信息")
public class ApiInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * api基本信息id值
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "api基本信息id值", required = true)
    private Long id;

    /**
     * api的名称
     */
    @ApiModelProperty(value = "api的名称,名称是全局唯一的。", required = true)
    private String apiName;

    /**
     * api描述信息
     */
    @ApiModelProperty(value = "api描述信息", required = false)
    @TableField(strategy = FieldStrategy.IGNORED)
    private String apiDesc;

    /**
     * aseKey值
     */
    @TableField(strategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "aseKey值", required = false)
    private String aseKey;

    /**
     * rsaPrivatekey值
     */
    @TableField(strategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "rsaPrivatekey值", required = false)
    private String rsaPrivatekey;

    /**
     * rsaPublickey值
     */
    @TableField(strategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "rsaPublickey值", required = false)
    private String rsaPublickey;

    /**
     * 服务id
     */
    @ApiModelProperty(value = "分组id的值", required = true)
    private Long serivceId;

}
