package com.yfh.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yfh.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yfh.eduservice.entity.vo.CourseInfoVo;
import com.yfh.eduservice.entity.vo.CoursePublishVo;
import com.yfh.eduservice.entity.vo.frontvo.CourseQueryVo;
import com.yfh.eduservice.entity.vo.frontvo.CourseWebVo;

import java.util.Map;

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

    Map<String, Object> getCoursePageList(Page<EduCourse> pageParams, CourseQueryVo courseQueryVo);

    CourseWebVo getCourseDetailById(String id);

    void updateCourseViewCount(String id);
}
