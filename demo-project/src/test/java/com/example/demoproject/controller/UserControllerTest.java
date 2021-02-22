package com.example.demoproject.controller;

import com.example.demoproject.DemoProjectApplication;
import com.example.demoproject.domain.User;
import junit.framework.TestCase;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={DemoProjectApplication.class})
class UserControllerTest {

    @Autowired
    private UserController userController;

    @Test
    void login() {
        User user = new User();
        user.setId(1);
        user.setUserName("jack");
        user.setPwd("123");
        TestCase.assertEquals(0, userController.login(user).getCode());
    }
}