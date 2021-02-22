package com.jdicity.gateway.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Write description here.
 *
 * @author qixinyuan3
 * @date
 */


@Data
@TableName("sentinel")
@ApiModel(value = "Sentinel", description = "限流降级规则")
public class Sentinel implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 限流降级表的主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "限流降级表的主键id", required = true)
    private Long id;

    /**
     * 对应的api的id
     */
    @ApiModelProperty(value = "对应的api的id", required = true)
    private Long apiId;

    /**
     * 0:线程数,1:QPS
     */
    @TableField(strategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "0:线程数,1:QPS", required = false)
    private String flowGrade;

    /**
     * 线程数或QPS峰值
     */
    @TableField(strategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "线程数或QPS峰值", required = false)
    private Integer flowCount;

    /**
     * 0:直接，1:关联，2:链路
     */
    @TableField(strategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "0:直接，1:关联，2:链路", required = false)
    private String flowStartegy;

    /**
     * 0:直接失败，1:warm up，2:排队等待，3:warm+排队等待
     */
    @TableField(strategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "0:直接失败，1:warm up，2:排队等待，3:warm+排队等待", required = false)
    private String flowBehavior;

    /**
     * true:集群模式，false:非集群模式
     */
    @TableField(strategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "true:集群模式，false:非集群模式", required = false)
    private String flowCluster;

    /**
     * 0:RT，1:异常比例
     */
    @TableField(strategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "0:RT，1:异常比例", required = false)
    private String degradeGrade;

    /**
     * RT阈值和异常数
     */
    @TableField(strategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "RT阈值和异常数", required = false)
    private Integer degradeCount;

    /**
     * 降级窗口时间
     */
    @TableField(strategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "降级窗口时间", required = false)
    private Integer degradeTime;


}
