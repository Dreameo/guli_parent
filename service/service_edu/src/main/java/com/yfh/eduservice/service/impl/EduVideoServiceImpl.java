package com.yfh.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yfh.eduservice.client.VodClient;
import com.yfh.eduservice.entity.EduVideo;
import com.yfh.eduservice.mapper.EduVideoMapper;
import com.yfh.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-04-27
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    @Autowired
    private VodClient vodClient;

    @Override
    public void removeCourseById(String course_id) {

        // 根据courseid查找 小节对应的视频  字段
        List<EduVideo> EduVideos = baseMapper.selectList(new QueryWrapper<EduVideo>().eq("course_id", course_id).select("video_source_id"));

        //得到所有视频列表的云端原始视频id
        List<String> ids = new ArrayList<>();
        for (EduVideo eduVideo : EduVideos) {
            if (null != eduVideo) {
                String videoSourceId = eduVideo.getVideoSourceId();
                if(!StringUtils.isEmpty(videoSourceId))
                    ids.add(videoSourceId);
            }

        }

        // 远程调用删除 视频
        if (ids.size() > 0)
            vodClient.removeVideoList(ids);


        //删除小节信息
        Integer count = baseMapper.delete(new QueryWrapper<EduVideo>().eq("course_id", course_id));

    }
}
