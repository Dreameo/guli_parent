package com.yfh.eduservice.mapper;

import com.yfh.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yfh.eduservice.entity.vo.CoursePublishVo;
import com.yfh.eduservice.entity.vo.frontvo.CourseWebVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2022-04-27
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    public CoursePublishVo getPublishCourseInfo(String courseId);

    CourseWebVo getCourseWebDeatilById(String id);
}
