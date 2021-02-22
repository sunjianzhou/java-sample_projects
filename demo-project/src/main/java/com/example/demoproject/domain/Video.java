package com.example.demoproject.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Write description here.
 *
 * @author sunjianzhou
 * @date 2021/1/24 17:41
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Video implements Serializable {

    private int id;

    private String title;

    private String summary;

    private int price;

    @JsonProperty("cover_img")  // 使用JsonProperty是为了指定属性，变量一般用驼峰，下划线形式为了让前端看。
    private String coverImg;

    //JsonFormat是为了做时间格式化.
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", locale = "zh", timezone = "GMT+8")
    @JsonProperty("create_time")
    private Date createTime;

    //JsonInclude这里是为了在返回json的时候，过滤掉结果值为null的chapterList。
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("chapter_list")  // 起别名
    private List<Chapter> chapterList;  // 对象中嵌套另一个对象数组

    public Video(int id, String title){
        this.id = id;
        this.title = title;
    }
}
