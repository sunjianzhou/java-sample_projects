package com.example.demoproject.dao;

import com.example.demoproject.domain.Video;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * Write description here.
 *
 * @author sunjianzhou
 * @date 2021/1/29 10:44
 */
public interface VideoMapper {

    /**
     * 根据id寻找视频对象。
     * @param videoId
     * @return
     */
    Video selectById(@Param("video_id") int videoId);  // 这里@Param是为了起别名，方便找到对应变量。

    /**
     * 查询全部视频列表。
     * @return
     */
    @Select("select * from video;") // 这种方式就不需要在mapper文件里写sql语句了。
    List<Video> selectList();

    List<Video> selectListByXml();

    /**
     * 根据评分和标题查询，主要即多个参数。
     * @param point
     * @param title
     * @return
     */
    List<Video> selectByPointAndTitleLike(@Param("point") double point, @Param("title") String title);

    /**
     * 新增接口，这里int返回，代表的是成功插入了多少行。
     * @param video
     * @return
     */
    int add(Video video);

    /**
     * 批量插入
     * @param videos
     * @return
     */
    int addBatch(List<Video> videos);

    /**
     * 更新一条数据
     * @param video
     * @return
     */
    int updateVideo(Video video);

    /**
     * 有选择性的进行更新，即没有值的地方不做更新。
     */
    int updateVideoSelective(Video video);

    /**
     * 根据时间和价格删除数据
     */
    int deleteByCreateTimeAndPrice(Map<String, Object> map);

    /**
     * ResultMap
     * 根据id查询视频
     */
    Video selectBaseFieldByIdWithResultMap(@Param("videoId") int id);
}
