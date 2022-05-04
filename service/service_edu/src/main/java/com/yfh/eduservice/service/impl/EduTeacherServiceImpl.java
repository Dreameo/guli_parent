package com.yfh.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yfh.eduservice.entity.EduCourse;
import com.yfh.eduservice.entity.EduTeacher;
import com.yfh.eduservice.mapper.EduTeacherMapper;
import com.yfh.eduservice.service.EduCourseService;
import com.yfh.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-04-19
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {
   @Autowired
   private EduCourseService courseService;


    @Override
    public Map<String, Object> getTeacherPage(Page<EduTeacher> pageParams) {

        baseMapper.selectPage(pageParams, new QueryWrapper<EduTeacher>().orderByDesc("gmt_modified"));
        Map<String, Object> map = new HashMap<>();

        List<EduTeacher> records = pageParams.getRecords(); // 总记录数
        long total = pageParams.getTotal();
        long pages = pageParams.getPages();
        long current = pageParams.getCurrent();
        long size = pageParams.getSize();
        boolean hasNext = pageParams.hasNext();
        boolean hasPrevious = pageParams.hasPrevious(); // 是否还有上一页

        map.put("records", records);
        map.put("total", total);
        map.put("pages", pages);
        map.put("current", current);
        map.put("size", size);
        map.put("hasPrevious", hasPrevious);
        map.put("hasNext", hasNext);

        return map;
    }

    @Override
    public Map<String, Object> getTeacherCoursesById(Long id) {
        // 按教师更新时间降序排序
        EduTeacher eduTeacher = baseMapper.selectOne(new QueryWrapper<EduTeacher>().eq("id", id));

        List<EduCourse> eduCourses = courseService.list(new QueryWrapper<EduCourse>().
                eq("teacher_id", id).
                orderByDesc("gmt_modified"));

        Map<String, Object> map = new HashMap<>();
        map.put("teacher", eduTeacher);
        map.put("courses", eduCourses);
        return map;
    }

}
