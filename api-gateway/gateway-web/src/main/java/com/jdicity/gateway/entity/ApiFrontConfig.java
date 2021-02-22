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
@TableName("api_front_config")
@ApiModel(value = "ApiFrontConfig", description = "api前端配置")
public class ApiFrontConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 前端配置id
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "前端配置表的主键id", required = true)
    private Long id;

    /**
     * 前端路径
     */
    @ApiModelProperty(value = "前端路径", required = true)
    private String frontPath;

    /**
     * 请求方式
     */
    @ApiModelProperty(value = "请求方式：GET,POST,PUT,DELETE,HEAD,PATCH,OPTIONS", required = true)
    private String method;

    /**
     * 接口协议
     */
    @ApiModelProperty(value = "接口协议：http,https", required = true)
    private String interfacePro;

    /**
     * api的id
     */
    @ApiModelProperty(value = "api的主键id", required = true)
    private Long apiId;

}
