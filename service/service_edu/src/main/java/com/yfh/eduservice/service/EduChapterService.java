package com.yfh.eduservice.service;

import com.yfh.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yfh.eduservice.entity.chapter.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-04-27
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterVo> getChapters(String course_id);

    boolean removeChapterById(String id);
}
