package com.yfh.servicevod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.yfh.commonutils.R;
import com.yfh.servicebase.exception.GuliException;
import com.yfh.servicevod.service.VodService;
import com.yfh.servicevod.utils.InitObjectUtil;
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
    public R deleteBatch(@RequestParam("videoList") List<String> videoList) {
        vodService.deleteAllVideo(videoList);
        return R.ok().message("删除多个视频成功！");
    }


    // 根据视频id获取 播放凭证
    @GetMapping("/getPlayAuth/{id}")
    public R getPlayAuth(@PathVariable String id) {
        String playAuth = "";

        try {

            // 1. 创建初始化对象
            DefaultAcsClient client = InitObjectUtil.initVodClient("LTAI5t6PmmwLCX6t7uCi81cj", "khpNpzc2DDIxJTz7HWM5gstpcrZj43");

            // 2. 创建获取视频地址的request 和 response
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();

            // 3. 向 request对象里面设置 视频id
            request.setVideoId(id);


            // 4. 调用初始化对象的方法 得到凭证
            response = client.getAcsResponse(request);

            System.out.println("playauth: " + response.getPlayAuth());
            playAuth = response.getPlayAuth();
            return R.ok().data("playAuth", playAuth);
        } catch (Exception e) {
            throw new GuliException(20001, "获取凭证失败");
        }
    }

}
