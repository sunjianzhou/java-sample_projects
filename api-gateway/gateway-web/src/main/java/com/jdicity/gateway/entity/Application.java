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
@TableName("application")
@ApiModel(value = "Application", description = "应用")
public class Application implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id，也是AppId
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "应用表的主键id，也是AppId", required = true)
    private Long id;

    /**
     * 应用的名称
     */
    @ApiModelProperty(value = "应用的名称", required = true)
    private String name;

    /**
     * 应用的描述信息
     */
    @TableField(strategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "应用的描述信息", required = false)
    private String appDesc;

    /**
     * AppKey的值
     */
    @ApiModelProperty(value = "AppKey的值", required = false)
    private String appKey;

    /**
     * AppSecret的值
     */
    @ApiModelProperty(value = "AppSecret的值", required = false)
    private String appSecret;

    /**
     * app的公钥
     */
    @ApiModelProperty(value = "App的公钥", required = false)
    private String pubKey;

    /**
     * 加解密私钥
     */
    @ApiModelProperty(value = "加解密的私钥", required = false)
    private String aesPrivateKey;

    /**
     * AppCode的值
     */
    @ApiModelProperty(value = "AppCode的值", required = false)
    private String appCode;

    /**
     * 加密公钥
     */
    @ApiModelProperty(value = "加密公钥", required = false)
    private String encryptPublicKey;

    /**
     * 加密私钥
     */
    @ApiModelProperty(value = "加密私钥", required = false)
    private String encryptPrivateKey;

    /**
     * 签名公钥
     */
    @ApiModelProperty(value = "签名公钥", required = false)
    private String signPublicKey;

    /**
     * 签名私钥
     */
    @ApiModelProperty(value = "签名私钥", required = false)
    private String signPrivateKey;

    /**
     * 应用的创建者
     */
    @ApiModelProperty(value = "应用的创建者id", required = true)
    private Long userId;

}
