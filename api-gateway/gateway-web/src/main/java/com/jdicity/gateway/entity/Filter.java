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
@TableName("filter")
@ApiModel(value = "Filter", description = "过滤器")
public class Filter implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "过滤器表的主键id", required = true)
    private Long id;

    /**
     * filter的名称
     */
    @ApiModelProperty(value = "filter的名称", required = true)
    private String filterName;

    /**
     * filter的值
     */
    @ApiModelProperty(value = "filter的值", required = true)
    private String filterValue;

    /**
     * filter描述信息
     */
    @ApiModelProperty(value = "filter描述信息", required = false)
    @TableField(strategy = FieldStrategy.IGNORED)
    private String filterDesc;

    /**
     * 对应的api的id
     */
    @ApiModelProperty(value = "对应的api的id", required = true)
    private Long apiId;


}
