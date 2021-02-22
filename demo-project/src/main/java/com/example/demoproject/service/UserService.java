package com.example.demoproject.service;

import com.example.demoproject.domain.User;

import java.util.List;

/**
 * Write description here.
 *
 * @author sunjianzhou
 * @date 2021/1/24 20:19
 */
public interface UserService {

    String login(String userName, String pwd);

    List<User> listUser();
}
