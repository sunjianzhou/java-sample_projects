package com.example.demoproject.service.impl;

import com.example.demoproject.domain.Video;
import com.example.demoproject.mapper.VideoMapper;
import com.example.demoproject.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Write description here.
 *
 * @author sunjianzhou
 * @date 2021/1/24 17:07
 */
@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideoMapper videoMapper;

    @Override
    public List<Video> listVideo() {
        return videoMapper.listVideo();
    }
}
