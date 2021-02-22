package com.example.demoproject.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class VideoOrder implements Serializable {

    private int id;

    private String outTradeNo;

    private int state;

    private Date createTime;

    private int totalFee;

    private int videoId;

    private String videoTitle;

    private String videoImg;

    private int userId;

    private User user;
}
