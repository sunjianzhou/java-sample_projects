package com.example.demoproject.controller;

import com.example.demoproject.task.AsyncTask;
import com.example.demoproject.utils.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Write description here.
 *
 * @author sunjianzhou
 * @date 2021/1/28 15:13
 */
@RestController
@RequestMapping("api/v1/pub/task")
public class TestAsyncTaskController {

    @Autowired
    AsyncTask asyncTask;

    @GetMapping("async")
    public JsonData testAsync() {
        long begin = System.currentTimeMillis();
        asyncTask.taskOne();
        asyncTask.taskTwo();
        asyncTask.taskThree();
        long end = System.currentTimeMillis();
        return JsonData.buildSuccess(end - begin);
    }

    @GetMapping("async-two")
    public JsonData testAsyncWithReturn() throws InterruptedException { // 这种方式是带返回值的。
        long begin = System.currentTimeMillis();
        Future<String> task3 = asyncTask.taskThree();
        Future<String> task4 = asyncTask.taskFour();

        while(!task3.isDone() && !task4.isDone()){
            Thread.sleep(1000L);
        }

        String task3Result = null;
        try {
            task3Result = task3.get();
            System.out.println(task3Result);
            String task4Result = task4.get();
            System.out.println(task4Result);
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        long end = System.currentTimeMillis();
        System.out.println("Total cost " + (end - begin));
        return JsonData.buildSuccess(end - begin);
    }
}
