package com.yfh.eduservice.service;

import com.yfh.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yfh.eduservice.entity.vo.CourseInfoVo;
import com.yfh.eduservice.entity.vo.CoursePublishVo;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-04-27
 */
public interface EduCourseService extends IService<EduCourse> {

    String saveCourseInfo(CourseInfoVo courseInfoVo);

    CourseInfoVo getCourseById(String course_id);

    void updateCourseInfo(CourseInfoVo courseInfoVo);

    CoursePublishVo getPublishCourseInfo(String id);

    void deleteCourseById(String course_id);
}
