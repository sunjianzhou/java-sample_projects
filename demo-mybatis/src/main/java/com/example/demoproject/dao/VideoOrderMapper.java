package com.example.demoproject.dao;

import com.example.demoproject.domain.User;
import com.example.demoproject.domain.VideoOrder;

import java.util.List;

public interface VideoOrderMapper {

    /**
     * 查询全部订单
     * @return
     */
    List<VideoOrder> queryVideoOrderList();

    /**
     * 查看全部用户的全部订单。
     * @return
     */
    List<User> queryUserOrder();

    /**
     * 懒加载的方式查询全部订单
     * 因为订单信息中有一项是User属性，但是在不需要的时候就不想去查询这一项。
     * @return
     */
    List<VideoOrder> queryVideoOrderLazy();
}
