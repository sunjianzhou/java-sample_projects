package net.xdclass.sp.domain;

import lombok.Data;

@Data
public class VideoOrder {

    private int id;

    // 订单流水号
    private String outTradeNo;

    private Video video;

}
