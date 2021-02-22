package com.example.demoproject.service;

import com.example.demoproject.DemoProjectApplication;
import com.example.demoproject.domain.Video;
import junit.framework.TestCase;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {DemoProjectApplication.class})
class VideoServiceTest {

    @Autowired
    private VideoService videoService;

    @Test
    public void listVideo() {
        List<Video> videoList = videoService.listVideo();
        TestCase.assertNotNull(videoList);
        TestCase.assertTrue(videoList.size() > 0);
    }
}