package com.yfh.servicevod.controller;

import com.yfh.commonutils.R;
import com.yfh.servicevod.service.VodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
}
