package com.yfh.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yfh.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-04-19
 */
public interface EduTeacherService extends IService<EduTeacher> {

    Map<String, Object> getTeacherPage(Page<EduTeacher> pageParams);

     Map<String, Object> getTeacherCoursesById(Long id);
}
