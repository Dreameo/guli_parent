package com.yfh.eduservice.client;

import com.yfh.commonutils.R;
import com.yfh.eduservice.client.hystrix.VodFileDegradeFeignClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "service-vod", fallback = VodFileDegradeFeignClient.class) // 被调用的服务名称
@Component
public interface VodClient {

    // 根据视频id删除视频
    // @PathVarible必须要指定名称 不然会出现问题
    @GetMapping("/eduvod/video/deleteVideoById/{id}") // 写全路径
    public R deleteVideoById(@PathVariable("id") String id);

    @DeleteMapping(value = "/eduvod/video/delete-batch")
    public R removeVideoList(@RequestParam("videoIdList") List<String> videoIdList);

}
