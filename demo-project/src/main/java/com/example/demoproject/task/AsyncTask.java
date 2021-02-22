package com.example.demoproject.task;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;

/**
 * Write description here.
 *
 * @author sunjianzhou
 * @date 2021/1/28 15:00
 */
@Component
@Async  // 加在类上，则表示这个类中所有方法都是异步的，加在方法上则表示对应方法是异步的。
public class AsyncTask {

    public void taskOne(){
        try {
            Thread.sleep(4000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Task one.");
    }

    public void taskTwo(){
        try {
            Thread.sleep(4000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Task two.");
    }

    public Future<String> taskThree(){
        try {
            Thread.sleep(4000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Task three.");
        return new AsyncResult<String>("Task Three Result.");
    }

    public Future<String> taskFour(){
        try {
            Thread.sleep(4000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Task four.");
        return new AsyncResult<String>("Task Four Result.");
    }

}
