package com.jdicity.ucuc.interceptor.threadlocals;

import lombok.Data;

import java.util.List;


/**
 * 定义userInfo.
 *
 * @author liyingda
 * @date 2020-11-19 18:16
 * @version v1.0.0
 */
@Data
public class UserInfo {
    /**
     * 统一参数
     */
    private Integer id;
    /**
     * 租户id
     */
    private Long tenantId;
    /**
     * 账号id
     */
    private Integer accountId;

    /**
     * 校验类型.
     * <p>
     * 0：账号密码登录类型
     * 1：clientId登录类型
     */
    private Integer type;


    //账号密码登录类型
    /**
     * 用户名
     */
    private String userName;
    /**
     * 用户类型
     */
    private String userType;
    /**
     * 真实姓名
     */
    private String realName;
    /**
     * 用户状态
     */
    private String userStatus;
    /**
     * 创建时间
     */
    private Long createTime;
    /**
     * 上传登录时间
     */
    private Long lastLoginTime;
    /**
     * 更新时间
     */
    private Long updateTime;
    /**
     * 创建人
     */
    private Integer createUser;
    /**
     * 最后操作人
     */
    private Integer updateUser;
    /**
     * 管理源
     */
    private String manageSource;
    /**
     * 角色列表
     */
    private List<String> roleName;
    /**
     * 组织名称列表
     */
    private List<String> organizationName;
    /**
     * 组织列表
     */
    private List<String> organizations;
    /**
     * openId
     */
    private String openId;

    //clientId登录类型
    /**
     * 用户名
     */
    private String name;
    /**
     * 用户code码
     */
    private String code;
    /**
     * 说明
     */
    private String description;
    /**
     * 额外信息
     */
    private String additionalInfo;
    /**
     * openid
     */
    private String openid;
    /**
     * rsa
     */
    private String rsa;
    /**
     * secretKey
     */
    private String secretKey;
    /**
     * 白名单列表
     */
    private boolean whitelistable;
    /**
     * 账号状态
     */
    private String status;
}
