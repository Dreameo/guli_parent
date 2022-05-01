package com.yfh.eduservice.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yfh.commonutils.R;
import com.yfh.eduservice.entity.EduCourse;
import com.yfh.eduservice.entity.EduTeacher;
import com.yfh.eduservice.service.EduCourseService;
import com.yfh.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/eduservice/indexfront")
@CrossOrigin
public class IndexFrontController {

    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduTeacherService teacherService;

    /**
     * 1. 查询 前8 条热门课程 询 前4名名师
     */
    @GetMapping("/index")
    public R index() {
        // 查询前八条课程
        List<EduCourse> topCourses = courseService.list(new QueryWrapper<EduCourse>().orderByDesc("id").last("limit 8"));

        // 查询 前 4条热门名师
        List<EduTeacher> topTeachers = teacherService.list(new QueryWrapper<EduTeacher>().orderByDesc("id").last("limit 4"));

        return R.ok().data("topCourses", topCourses).data("topTeachers", topTeachers);
    }



}
