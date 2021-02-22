package com.example.demoproject.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Random;

/**
 * 定时统计订单。
 *
 * @author sunjianzhou
 * @date 2021/1/28 11:02
 */
@Component
public class VideoOrderTask {

//    @Scheduled(fixedRate = 2 * 1000)  // 每隔两秒执行一次，按上一次任务开始时间计算
    @Scheduled(fixedDelay = 2 * 1000)  // 每隔两秒执行一次，按上一次任务结束时间计算
//    @Scheduled(cron = "*/2 * * * * *")  // 每隔两秒执行一次
    public void sum(){
        // 模拟演示
        System.out.println(LocalDateTime.now() + "当前交易额等于" + Math.random());

        try {
            Thread.sleep(4000L);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
