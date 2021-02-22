package com.example.demoproject;

import com.example.demoproject.dao.VideoMapper;
import com.example.demoproject.domain.Video;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class SqlSessionCacheDemo {
    public static void main(String args[]) throws IOException {
        String resource = "config/mybatis-config.xml";

        // 读取配置文件
        InputStream inputStream = Resources.getResourceAsStream(resource);

        // 构建session工厂
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSessionOne = sqlSessionFactory.openSession();
        SqlSession sqlSessionTwo = sqlSessionFactory.openSession();

        // 获取Session
        try {

            VideoMapper videoMapper = sqlSessionOne.getMapper(VideoMapper.class);
            Video videoOne = videoMapper.selectById(58);
            System.out.println(videoOne);
            sqlSessionOne.commit();

            VideoMapper videoMapperTwo = sqlSessionTwo.getMapper(VideoMapper.class);
            Video videoTwo = videoMapperTwo.selectById(58);
            System.out.println(videoTwo);
            sqlSessionTwo.commit();  // 因为上面openSession时没有给定autoCommit为true，所以此处需要显式地自己提交。

        }catch (Exception e){
            e.printStackTrace();
            sqlSessionOne.rollback();
            sqlSessionTwo.rollback();
        }
    }
}
