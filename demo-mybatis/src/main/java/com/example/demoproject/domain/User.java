package com.example.demoproject.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class User implements Serializable {  // 这里实现序列化是为了方便使用Mybatis的二级缓存。缓存中存储对象需要是实现序列化的。
    private int id;

    private String name;

    private String pwd;

    private String phone;

    private Date createTime;

    private String headImg;

    private List<VideoOrder> videoOrderList;
}
