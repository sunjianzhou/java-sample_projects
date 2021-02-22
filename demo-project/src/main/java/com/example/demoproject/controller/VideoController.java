package com.example.demoproject.controller;

import com.example.demoproject.domain.Video;
import com.example.demoproject.service.VideoService;
import com.example.demoproject.utils.JsonData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 视频控制器。
 *
 * @author sunjianzhou
 * @date 2021/1/22 15:11
 */
@RestController
@RequestMapping("/api/v1/pub/video")
public class VideoController {

//    @RequestMapping("list")
//    public Object list(){
//        Map<String, String> map = new HashMap<>();
//        map.put("1", "面试专题");
//        map.put("2", "Spring cloud 微服务课程");
//
//        return map;
//    }

    @Autowired
    private VideoService videoService;

    @RequestMapping("list")
    public JsonData list() throws JsonProcessingException {

        List<Video> list = videoService.listVideo();

        // 将json序列化成字符串。只是为了演示使用。
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonStr = objectMapper.writeValueAsString(list);

        System.out.println("jsonStr: " + jsonStr);

        // 将字符串反序列化回List
        List<Video> result = objectMapper.readValue(jsonStr, List.class);

        // 下面的这个result本质上就和原来的list一模一样，这里只是为了做试验，所以转过去又转回来了一下。
        return JsonData.buildSuccess(result);
    }

    @PostMapping("save-video-chapter")
    public JsonData saveVideoChapter(@RequestBody Video video){ // 这里的Video对象里面包含了一个章的数组。
        System.out.println(video.toString());
        return JsonData.buildSuccess(video);
    }
}
