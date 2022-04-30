package com.yfh.servicevod.controller;

import com.yfh.commonutils.R;
import com.yfh.servicevod.service.VodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/eduvod/video")
public class VodController {

    @Autowired
    private VodService vodService;

    // 上传 视频到 阿里云
    @PostMapping("/uploadAliVideo")
    public R uploadAliVideo(MultipartFile file) {
        String videoId = vodService.uploadAliVideo(file);
        return R.ok().data("videoId", videoId);
    }

    // 根据视频id删除视频
    @GetMapping("/deleteVideoById/{id}")
    public R deleteVideoById(@PathVariable String id) {
        vodService.deleteVideoById(id);
        return R.ok();
    }

    // 删除多个视频
    @DeleteMapping("/delete-batch")
    public R deleteBatch(@RequestParam("videoList")List<String> videoList) {
        vodService.deleteAllVideo(videoList);
        return R.ok().message("删除多个视频成功！");
    }
}
