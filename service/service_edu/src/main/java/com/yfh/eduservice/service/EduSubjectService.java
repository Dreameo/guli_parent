package com.yfh.eduservice.service;

import com.yfh.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-04-22
 */
public interface EduSubjectService extends IService<EduSubject> {
    // 添加课程分类
    void addSubject(MultipartFile file, EduSubjectService subjectService);
}
