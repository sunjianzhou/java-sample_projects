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
@TableName("service")
@ApiModel(value = "Service", description = "组")
public class ServiceGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * service表id
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "分组表的主键id", required = true)
    private Long id;

    /**
     * 服务名称
     */
    @ApiModelProperty(value = "分组的名称", required = true)
    private String serviceName;

    /**
     * 服务描述
     */
    @TableField(strategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "分组的描述信息", required = false)
    private String serviceDesc;

    /**
     * 服务负责人
     */
    @ApiModelProperty(value = "服务负责人id", required = true)
    private Long userId;

}
