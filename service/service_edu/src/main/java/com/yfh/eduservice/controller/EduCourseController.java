package com.yfh.eduservice.controller;


import com.yfh.commonutils.R;
import com.yfh.eduservice.entity.EduCourse;
import com.yfh.eduservice.entity.vo.CourseInfoVo;
import com.yfh.eduservice.entity.vo.CoursePublishVo;
import com.yfh.eduservice.service.EduCourseService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-04-27
 */

@Api(description = "课程管理")
@RestController
@RequestMapping("/eduservice/course")
@CrossOrigin
public class EduCourseController {

    private EduCourseService courseService;

    /**
     * 添加课程信息 的方法
     */

    @PostMapping("addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {

        String courseId = courseService.saveCourseInfo(courseInfoVo);

        return R.ok().data("courseId", courseId);
    }


    /**
     * 根据 courseid 查找 course信息
     */
    @GetMapping("/getCourseInfo/{course_id}")
    public R getCourseInfo(@PathVariable String course_id) {

        CourseInfoVo courseInfoVo = courseService.getCourseById(course_id);


        return R.ok().data("courseInfoVo", courseInfoVo);
    }

    /**
     * 修改课程信息
     */
    @PostMapping("updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {

        courseService.updateCourseInfo(courseInfoVo);
        return R.ok();
    }

    /**
     * 根据课程id 查询确认信息
     */
    @GetMapping("/getPublishCourseInfo/{id}")
    public R getPublishCourseInfo(@PathVariable String id) {
        CoursePublishVo coursePublishVo = courseService.getPublishCourseInfo(id);
        return R.ok().data("publishCourse", coursePublishVo);
    }

    /**
     * 修改课程 发布状态
     */

    @PostMapping("/publishCourse/{id}")
    public R publishCourse(@PathVariable String id) {
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(id);
        eduCourse.setStatus("Normal");
        courseService.updateById(eduCourse);
        return R.ok();
    }


    // 课程列表  基本实现
    // TODO 完善条件查询
    public R getCourseList() {
        List<EduCourse> courseList = courseService.list(null);
        return R.ok().data("courseList", courseList);
    }

}

