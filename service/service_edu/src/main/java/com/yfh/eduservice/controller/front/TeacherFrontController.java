package com.yfh.eduservice.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yfh.commonutils.R;
import com.yfh.eduservice.entity.EduTeacher;
import com.yfh.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/eduservice/teacherfront")
public class TeacherFrontController {

    @Autowired
    private EduTeacherService teacherService;

    // 1. 前端分页显示教师
    @GetMapping("/teacher/{page}/{limit}")
    public R teacher(@PathVariable Long page, @PathVariable Long limit) {
        Page<EduTeacher> pageParams = new Page<>(page, limit);

//        teacherService.page(pageParams, null);

        Map<String, Object> map = teacherService.getTeacherPage(pageParams);

        return R.ok().data(map);
    }

    // 2. 根据教师id查询 教师信息
    @GetMapping("/teacher/{id}")
    public R getTeacherById(@PathVariable Long id) {

        // 要返回 教师信息 和 所教课程
        Map<String, Object> map = teacherService.getTeacherCoursesById(id);
        return R.ok().data(map);
    }
}
