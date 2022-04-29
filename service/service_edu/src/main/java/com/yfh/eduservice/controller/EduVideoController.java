package com.yfh.eduservice.controller;


import com.sun.media.jfxmediaimpl.MediaUtils;
import com.yfh.commonutils.R;
import com.yfh.eduservice.entity.EduVideo;
import com.yfh.eduservice.service.EduVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-04-27
 */
@RestController
@RequestMapping("/eduservice/video")
@CrossOrigin
public class EduVideoController {

    @Autowired
    private EduVideoService videoService;

    /**
     * 增加小节
     */
    @PostMapping("/addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo) {
        videoService.save(eduVideo);
        return R.ok();
    }

    /**
     * 根据id删除小节
     */
    @DeleteMapping("/deleteVideoById/{video_id}")
    public R deleteVideoById(@PathVariable String video_id) {
        videoService.removeById(video_id);
        return R.ok();
    }

    /**
     * 根据id查找 小节
     */

    @GetMapping("/getVideoById/{video_id}")
    public R getVideoById(@PathVariable String video_id) {
        EduVideo video = videoService.getById(video_id);
        return R.ok().data("video", video);
    }

    /**
     * 修改小节
     */
    @PostMapping("/updateVideo")
    public R updateVideo(@RequestBody EduVideo eduVideo) {
        videoService.updateById(eduVideo);
        return R.ok();
    }

}

