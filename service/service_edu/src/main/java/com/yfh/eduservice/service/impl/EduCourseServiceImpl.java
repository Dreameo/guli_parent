package com.yfh.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yfh.eduservice.entity.EduChapter;
import com.yfh.eduservice.entity.EduCourse;
import com.yfh.eduservice.entity.EduCourseDescription;
import com.yfh.eduservice.entity.EduVideo;
import com.yfh.eduservice.entity.vo.CourseInfoVo;
import com.yfh.eduservice.entity.vo.CoursePublishVo;
import com.yfh.eduservice.mapper.EduCourseMapper;
import com.yfh.eduservice.service.EduChapterService;
import com.yfh.eduservice.service.EduCourseDescriptionService;
import com.yfh.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yfh.eduservice.service.EduVideoService;
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

    @Autowired
    private EduVideoService videoService;

    @Autowired
    private EduChapterService chapterService;




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

    @Override
    public CourseInfoVo getCourseById(String course_id) {

        // 1. 根据id查询 课程信息
        EduCourse eduCourse = baseMapper.selectById(course_id);

        // 2. 根据id查询 课程简介信息
        EduCourseDescription courseDescription = descriptionService.getById(course_id);

        // 3. 将 课程信息和 简介信息 封装到 CourseVo对象中
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(eduCourse, courseInfoVo);

        courseInfoVo.setDescription(courseDescription.getDescription());

        return courseInfoVo;
    }

    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        // 1. 修改课程表
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);

        int update = baseMapper.updateById(eduCourse);

        if(update == 0) {
            throw new GuliException(20001, "课程修改失败");
        }

        // 2. 修改课程简介表
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setDescription(courseInfoVo.getDescription()); // 修改描述信息
        eduCourseDescription.setId(courseInfoVo.getId());

        // 更新操作
        descriptionService.updateById(eduCourseDescription);


    }

    @Override
    public CoursePublishVo getPublishCourseInfo(String id) {
        CoursePublishVo publishCourseInfo = baseMapper.getPublishCourseInfo(id);
        return publishCourseInfo;
    }

    @Override
    public void deleteCourseById(String course_id) {
        // TODO 删除小节对应的的视频
        // 1. 删除 小节对应的视频

        // 2. 删除小节
        videoService.remove(new QueryWrapper<EduVideo>().eq("course_id", course_id));

        // 3. 删除章节
        chapterService.remove(new QueryWrapper<EduChapter>().eq("course_id", course_id));

        // 4. 删除课程简介信息
        descriptionService.removeById(course_id);

        // 5. 删除课程信息
        this.removeById(course_id);

    }
}
