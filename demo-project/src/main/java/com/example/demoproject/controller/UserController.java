package com.example.demoproject.controller;

import com.example.demoproject.domain.User;
import com.example.demoproject.service.UserService;
import com.example.demoproject.utils.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Write description here.
 *
 * @author sunjianzhou
 * @date 2021/1/25 9:49
 */
@RestController
@RequestMapping("api/v1/pub/user")
public class UserController {

//    以下这种方式可以通过body，x-www-form-urlencoded的方式传参数，也可以通过Params的方式传参数。
//    @PostMapping("login")
//    public JsonData login(String pwd, String userName){
//        System.out.println("userName=" + userName + "pwd=" + pwd);
//        return JsonData.buildSuccess("");
//    }

    @Autowired
    public UserService userService;

    /**
     * 登录接口
     * @param user
     * @return
     */
    @PostMapping("login")
    public JsonData login(@RequestBody User user){  // 这里如果参数上不加@RequestBody，那么就没法接受json类型的参数
        System.out.println("userName=" + user.getUserName() + "pwd=" + user.getPwd());
        String token = userService.login(user.getUserName(), user.getPwd());
//        if (null == result){
//            return JsonData.buildError("Login failed");
//        }else {
//            return JsonData.buildSuccess("Login Success.");
//        }
        return token != null ? JsonData.buildSuccess(token) : JsonData.buildError("登录失败。");

    }

    /**
     * 列出所有用户
     * @return
     */
    @GetMapping("list")
    public JsonData listUser(){
        return JsonData.buildSuccess(userService.listUser());
    }
}
