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
@TableName("api_back_config")
@ApiModel(value = "ApiBackConfig", description = "api后端配置")
public class ApiBackConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id值
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "后端配置表的主键id", required = true)
    private Long id;

    /**
     * 后端地址
     */
    @ApiModelProperty(value = "后端地址", required = true)
    private String backAddr;

    /**
     * 后端路径
     */
    @ApiModelProperty(value = "后端路径", required = true)
    private String backPath;

    /**
     * 请求方式
     */
    @ApiModelProperty(value = "请求方式：透传,GET,POST,PUT,DELETE,HEAD,PATCH,OPTIONS", required = true)
    private String method;

    /**
     * 接口协议
     */
    @ApiModelProperty(value = "接口协议：http,https", required = true)
    private String interfacePro;

    /**
     * 断言
     */
    @ApiModelProperty(value = "断言", required = false)
    private String predicates;

    /**
     * api的id
     */
    @ApiModelProperty(value = "api的id", required = true)
    private Long apiId;


}
