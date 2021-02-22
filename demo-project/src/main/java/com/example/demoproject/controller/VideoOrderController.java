package com.example.demoproject.controller;

import com.example.demoproject.utils.JsonData;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Write description here.
 *
 * @author sunjianzhou
 * @date 2021/1/27 14:47
 */
@RestController
@RequestMapping("api/v1/pri/order")
public class VideoOrderController {

    @RequestMapping("save")
    public JsonData saveOrder(){
        return JsonData.buildSuccess("下单成功");
    }
}
