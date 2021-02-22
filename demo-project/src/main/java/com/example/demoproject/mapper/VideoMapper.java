package com.example.demoproject.mapper;

import com.example.demoproject.domain.Video;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Write description here.
 *
 * @author sunjianzhou
 * @date 2021/1/24 17:40
 */
@Repository
public class VideoMapper {

    private static Map<String, Video> videoMap = new HashMap<>();

    static {
        videoMap.put("1", new Video(1, "Java"));
        videoMap.put("2", new Video(2, "Python"));
        videoMap.put("3", new Video(3, "Spring Boot"));
        videoMap.put("4", new Video(4, "Spring Cloud"));
    }

    public List<Video> listVideo(){
        List<Video> list = new ArrayList<>();
        list.addAll(videoMap.values());
        return list;
    }
}
