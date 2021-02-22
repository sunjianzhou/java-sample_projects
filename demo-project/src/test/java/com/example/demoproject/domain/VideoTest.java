package com.example.demoproject.domain;

import com.example.demoproject.DemoProjectApplication;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={DemoProjectApplication.class})
public class VideoTest {

    @Before
    public void testOne(){
        System.out.println("Before Test.");
        TestCase.assertEquals(1,1);
    }

    @Test
    public void testTwo(){
        System.out.println("Starting Test.");
    }

    @After
    public void testThree(){
        System.out.println("After Test.");
    }
}