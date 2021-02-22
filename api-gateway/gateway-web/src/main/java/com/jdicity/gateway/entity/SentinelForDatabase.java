package com.jdicity.gateway.entity;

import lombok.Data;

/**
 * Write description here.
 *
 * @author qixinyuan3
 * @date 2020/12/21 17:50
 */

@Data
public class SentinelForDatabase {
    /**
     * 前端路径
     */
    private String frontPath;

    /**
     * 0:线程数,1:QPS
     */
    private String flowGrade;

    /**
     * 线程数或QPS峰值
     */
    private Integer flowCount;

    /**
     * 0:直接，1:关联，2:链路
     */
    private String flowStartegy;

    /**
     * 0:直接失败，1:warm up，2:排队等待，3:warm+排队等待
     */
    private String flowBehavior;

    /**
     * true:集群模式，false:非集群模式
     */
    private String flowCluster;

    /**
     * 0:RT，1:异常比例
     */
    private String degradeGrade;

    /**
     * RT阈值和异常数
     */
    private Integer degradeCount;

    /**
     * 降级窗口时间
     */
    private Integer degradeTime;
}
