package com.example.demoproject.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 视频相关实体类.
 *
 * @author sunjianzhou
 * @date 2021/1/29 10:45
 */
@Data
public class Video implements Serializable {

    // 主键
    private int id;

    //标题
    private String title;

    // 视频详情
    private String summary;

    // 视频封面图
    private String coverImg;

    // 价格
    private int price;

    // 创建时间
    private Date createTime;

    // 评分
    private Double point;
}
