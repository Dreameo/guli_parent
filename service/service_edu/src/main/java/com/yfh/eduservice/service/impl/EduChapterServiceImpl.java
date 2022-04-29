package com.yfh.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yfh.eduservice.entity.EduChapter;
import com.yfh.eduservice.entity.EduVideo;
import com.yfh.eduservice.entity.chapter.ChapterVo;
import com.yfh.eduservice.entity.chapter.VideoVo;
import com.yfh.eduservice.mapper.EduChapterMapper;
import com.yfh.eduservice.mapper.EduVideoMapper;
import com.yfh.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yfh.servicebase.exception.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-04-27
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {
    @Autowired
    private EduVideoMapper videoMapper;

    @Override
    public List<ChapterVo> getChapters(String course_id) {
        // 1. 根据course_id找出 章节
        List<EduChapter> eduChapters = baseMapper.selectList(new QueryWrapper<EduChapter>()
                .eq("course_id", course_id).orderByAsc("id"));
        // 2. 找出所有的  小节
        List<EduVideo> eduVideos = videoMapper.selectList(new QueryWrapper<EduVideo>().eq("course_id", course_id)
                .orderByAsc("id"));

        List<ChapterVo> finalList = new ArrayList<>();
        // 3. 封装 章节
        for (EduChapter eduChapter : eduChapters) {
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(eduChapter, chapterVo);

            List<VideoVo> videoVoList = new ArrayList<>();
            // 4. 封装 小节
            for (EduVideo eduVideo : eduVideos) {
                if (eduVideo.getChapterId().equals(chapterVo.getId())) { // 章节id 与 小节id对比
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(eduVideo, videoVo);
                    videoVoList.add(videoVo);
                }
            }

            chapterVo.setChildren(videoVoList);

            finalList.add(chapterVo);
        }


        return finalList;
    }

    @Override
    public boolean removeChapterById(String id) {
        Integer count = videoMapper.selectCount(new QueryWrapper<EduVideo>().eq("chapter_id", id));
        if(count > 0) {
            throw new GuliException(20001, "该章节下面还有小节没有删除");
        }
        int delete = baseMapper.deleteById(id);
        return delete > 0;
    }
}
