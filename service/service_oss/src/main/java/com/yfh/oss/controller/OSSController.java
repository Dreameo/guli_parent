package com.yfh.oss.controller;

import com.yfh.commonutils.R;
import com.yfh.oss.service.OSSService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/eduoss/fileoss")
@CrossOrigin // 防止跨域问题
public class OSSController {

    @Autowired
    private OSSService ossService;

    /**
     * 上传头像
     */
    @PostMapping
    public R uploadOssFile(MultipartFile multipartFile) { // 上传文件
        // 获取上传文件 MultipartFile

        // 方法返回 图片路径
        String url = ossService.uploadAvatar(multipartFile);

        return R.ok().data("url", url);
    }
}
