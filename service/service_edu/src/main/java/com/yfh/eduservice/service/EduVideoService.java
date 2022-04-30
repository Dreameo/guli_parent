package com.yfh.eduservice.service;

import com.yfh.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-04-27
 */
public interface EduVideoService extends IService<EduVideo> {

    void removeCourseById(String course_id);
}
