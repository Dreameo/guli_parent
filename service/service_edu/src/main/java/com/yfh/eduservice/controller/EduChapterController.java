package com.yfh.eduservice.controller;


import com.yfh.commonutils.R;
import com.yfh.eduservice.entity.EduChapter;
import com.yfh.eduservice.entity.chapter.ChapterVo;
import com.yfh.eduservice.service.EduChapterService;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-04-27
 */
@RestController
@RequestMapping("/eduservice/chapter")
@CrossOrigin
public class EduChapterController {
    @Autowired
    private EduChapterService chapterService;

    @GetMapping("/getChapters/{course_id}")
    public R getChapters(@PathVariable String course_id) {
        List<ChapterVo> chapterVoList = chapterService.getChapters(course_id);
        return R.ok().data("chapters", chapterVoList);
    }


    /**
     * 增加章节
     */
    @PostMapping("/addChapter")
    public R addChapter(@RequestBody EduChapter eduChapter) {
        chapterService.save(eduChapter);
        return R.ok();
    }

    /**
     * 修改章节 1. 根据章节id查询章节 2. 修改章节
     */
    @GetMapping("/getChapterById/{id}")
    public R getChapterById(@PathVariable String id) {
        EduChapter eduChapter = chapterService.getById(id);
        return R.ok().data("chapter", eduChapter);
    }

    /**
     * 修改章节
     */
    @PostMapping("/updateChapter")
    public R updateChapter(@RequestBody EduChapter eduChapter) {
        boolean b = chapterService.updateById(eduChapter);
        return R.ok();
    }

    /**
     * 根据 chapter id 删除章节
     * 如果 存在 小节 不能删除 该章节
     */
    @DeleteMapping("/deleteChapterById/{id}")
    public R deleteChapterById(@PathVariable String id) {
        boolean b = chapterService.removeChapterById(id);
        if(b) return R.ok();

        return R.error().message("删除失败");
    }

}

