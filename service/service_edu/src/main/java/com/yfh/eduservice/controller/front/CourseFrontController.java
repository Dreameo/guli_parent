package com.yfh.eduservice.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yfh.commonutils.R;
import com.yfh.eduservice.entity.EduCourse;
import com.yfh.eduservice.entity.chapter.ChapterVo;
import com.yfh.eduservice.entity.vo.CourseInfoVo;
import com.yfh.eduservice.entity.vo.frontvo.CourseQueryVo;
import com.yfh.eduservice.entity.vo.frontvo.CourseWebVo;
import com.yfh.eduservice.service.EduChapterService;
import com.yfh.eduservice.service.EduCourseService;
import com.yfh.ordervo.OrderCourseWebVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/eduservice/coursefront")
public class CourseFrontController {

    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduChapterService chapterService;


    // 1. 带条件查询的 分页显示课程列表 // 用requestBody一定要用post
    @PostMapping("/course/{page}/{limit}")
    public R coursePage(@PathVariable Long page, @PathVariable Long limit,
                        @RequestBody(required = false) CourseQueryVo courseQueryVo) {


        Page<EduCourse> pageParams = new Page<>(page, limit);

        Map<String,Object> map = courseService.getCoursePageList(pageParams, courseQueryVo);

        return R.ok().data(map);
    }

    // 2. 根据课程id 查看磕碜详情
    @GetMapping("/getCourseDetailById/{id}")
    public R getCourseDetailById(@PathVariable String id) {
        // 1. 根据id查看课程详情的基本信息 用dto对象进行封装
        CourseWebVo courseWebVo = courseService.getCourseDetailById(id);

        // 2. 根据课程id查看 课程大纲
        List<ChapterVo> chapters = chapterService.getChapters(id);

        // 3. 根据课程id 更新 课程浏览数量
        courseService.updateCourseViewCount(id);
        return R.ok().data("courseVo", courseWebVo).data("chapters", chapters);
    }

    // 3. 订单中需要获取的 订单信息 根据id获取
    @GetMapping("/getOrderCourseById/{course_id}")
    public OrderCourseWebVo getOrderCourseById(@PathVariable String course_id) {
        OrderCourseWebVo orderCourseWebVo = new OrderCourseWebVo();
        CourseInfoVo courseInfoVo = courseService.getCourseById(course_id);

        BeanUtils.copyProperties(courseInfoVo, orderCourseWebVo);

        return orderCourseWebVo;
    }




}
