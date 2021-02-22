package com.example.demoproject;

import com.example.demoproject.dao.VideoMapper;
import com.example.demoproject.dao.VideoOrderMapper;
import com.example.demoproject.domain.User;
import com.example.demoproject.domain.Video;
import com.example.demoproject.domain.VideoOrder;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Write description here.
 *
 * @author sunjianzhou
 * @date 2021/1/29 10:44
 */
public class SqlSessionDemo {

    public static void main(String args[]) throws IOException {
        String resource = "config/mybatis-config.xml";

        // 读取配置文件
        InputStream inputStream = Resources.getResourceAsStream(resource);

        // 构建session工厂
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        // 获取Session
        try (SqlSession sqlSession = sqlSessionFactory.openSession(true)) { // 这里有点类似于with open的效果，会帮忙自动关闭。

            VideoMapper videoMapper = sqlSession.getMapper(VideoMapper.class); // 这里是通过反射去拿到的。
            VideoOrderMapper videoOrderMapper = sqlSession.getMapper(VideoOrderMapper.class);

            // 根据Mapper
//            Video video = videoMapper.selectById(1);
//            System.out.println(video.toString());

            // 直接使用注解
//            List<Video> videoList = videoMapper.selectList();
//            System.out.println(videoList.toString());

            // 也根据Mapper的方式玩
//            List<Video> videoListByXml = videoMapper.selectListByXml();
//            System.out.println(videoListByXml.toString());

            // 多参数查询
//            List<Video> videoList = videoMapper.selectByPointAndTitleLike(1.0, "he");
//            System.out.println(videoList.toString());

//            // 新增一条数据
//            Video video = new Video();
//            video.setTitle("TitleNew");
//            video.setCoverImg("CoverImgNew");
//            video.setPrice(666);
//            video.setCreateTime(new Date());
//            video.setPoint(9.9);
//            video.setSummary("SummaryNew");
//            int addedNum = videoMapper.add(video);
//            System.out.println(addedNum);
//            // 要想真正能够执行insert into，必须要做两以下操作。
////            第一种：sqlSession.commit(); // 表示提交事务。即将插入操作真正地录入到数据库中去。
//            // 第二种：sqlSession = sqlSessionFactory.openSession(true), 这里的true表示会自动提交，默认是false。 这里我们用的第二种。
//            System.out.println(video.toString());

            // 新增多条数据
//            Video videoOne = new Video();
//            videoOne.setTitle("TitleNew1");
//            videoOne.setCoverImg("CoverImgNew1");
//            videoOne.setPrice(6661);
//            videoOne.setCreateTime(new Date());
//            videoOne.setPoint(9.91);
//            videoOne.setSummary("SummaryNew1");
//
//            Video videoTwo = new Video();
//            videoTwo.setTitle("TitleNew2");
//            videoTwo.setCoverImg("CoverImgNew2");
//            videoTwo.setPrice(6662);
//            videoTwo.setCreateTime(new Date());
//            videoTwo.setPoint(9.92);
//            videoTwo.setSummary("SummaryNew2");
//
//            List<Video> videos = new ArrayList<>();
//            videos.add(videoOne);
//            videos.add(videoTwo);
//
//            int rows = videoMapper.addBatch(videos);
//            System.out.println(rows);
//            System.out.println(videos.toString());
//            System.out.println(videos.get(0).getId());

//            // 不合理的更新，即把没有值得内容传null
//            Video video = new Video();
//            video.setTitle("TitleUpdate");
//            video.setCoverImg("CoverImgUpdate");
//            video.setId(60);
//            video.setCreateTime(new Date());
//            int addedNum = videoMapper.updateVideo(video);
//            System.out.println(addedNum);

//            // 合理的更新，即没有值得列就不尽兴更新，只更新有值得对应列。
//            Video video = new Video();
//            video.setTitle("TitleUpdate");
//            video.setCoverImg("CoverImgUpdatetwo");
//            video.setId(59);
//            video.setCreateTime(new Date());
//            int addedNum = videoMapper.updateVideoSelective(video);
//            System.out.println(addedNum);

//            // 根据条件进行删除数据
//            Map<String, Object> map = new HashMap<>();
//            map.put("createTime", "2021-01-13 09:20:20");
//            map.put("price", 10);
//            int rows = videoMapper.deleteByCreateTimeAndPrice(map);
//            System.out.println(rows);

            // 按ResultMap进行结果返回
//            Video video = videoMapper.selectBaseFieldByIdWithResultMap(61);
//            System.out.println(video.toString());

//            // 关联表的ResultMap，用了associate，主要解决一对一的情况
//            List<VideoOrder> videos = videoOrderMapper.queryVideoOrderList();
//            System.out.println(videos.toString());
//            System.out.println(videos);

//            // 关联表的ResultMap，用了collection，主要解决一对多的情况
//            List<User> users = videoOrderMapper.queryUserOrder();
//            System.out.println(users);

            // 对ResultMap进行懒加载
            List<VideoOrder> videoOrders = videoOrderMapper.queryVideoOrderLazy();
            System.out.println(videoOrders.size());
            System.out.println("=============");
            for (VideoOrder videoOrder: videoOrders){
                System.out.println(videoOrder.getVideoTitle());
                System.out.println(videoOrder.getUser().getName());   // 只有此时，才会去做关联表查询操作。故而达到了懒加载的效果。
            }

        }
    }
}
