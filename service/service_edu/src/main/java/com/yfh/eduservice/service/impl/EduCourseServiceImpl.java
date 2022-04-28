package com.yfh.eduservice.service.impl;

import com.yfh.eduservice.entity.EduCourse;
import com.yfh.eduservice.entity.EduCourseDescription;
import com.yfh.eduservice.entity.vo.CourseInfoVo;
import com.yfh.eduservice.mapper.EduCourseMapper;
import com.yfh.eduservice.service.EduCourseDescriptionService;
import com.yfh.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yfh.servicebase.exception.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-04-27
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    private EduCourseDescriptionService descriptionService;

    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
        // 1.向 课程表中加入数据
        // 将 CourseInfoVo 转为 EduCourse
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        int insert = baseMapper.insert(eduCourse); // insert 返回的是影响行数 大于0 成功 == 0 失败

        if (insert == 0) {
            // 抛出全局异常
            throw new GuliException(20001, "添加课程信息失败");
        }
        // 向简介信息表 中添加信息时 要先获取课程表的id 才能建立起 两者之间一对一的关系
        // 改了这里之后， 要将 课程简介的 id生成策略 改为input
        String eduCourseId = eduCourse.getId();

        // 2. 向 课程简介中加入数据
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        BeanUtils.copyProperties(courseInfoVo, eduCourseDescription);
        eduCourseDescription.setId(eduCourseId);
        descriptionService.save(eduCourseDescription);

        return eduCourseId;
    }
}
