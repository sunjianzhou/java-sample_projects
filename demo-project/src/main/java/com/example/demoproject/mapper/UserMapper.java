package com.example.demoproject.mapper;

import com.example.demoproject.domain.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Write description here.
 *
 * @author sunjianzhou
 * @date 2021/1/24 20:15
 */
@Repository
public class UserMapper {

    private static Map<String, User> userMap = new HashMap<>();

    static{
        userMap.put("jack", new User(1, "jack", "123"));
        userMap.put("rose", new User(2, "rose", "234"));
        userMap.put("lily", new User(3, "lily", "345"));
    }

    public User login(String userName, String pwd){
        User user = userMap.get(userName);
        if (user == null){
            return null;
        }
        if (user.getPwd().equals(pwd)){
            return user;
        }
        return null;
    }

    public List<User> listUser(){
        List<User> list = new ArrayList<>();
        list.addAll(userMap.values());
        return list;
    }
}
