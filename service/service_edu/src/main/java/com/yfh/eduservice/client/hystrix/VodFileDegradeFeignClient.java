package com.yfh.eduservice.client.hystrix;

import com.yfh.commonutils.R;
import com.yfh.eduservice.client.VodClient;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VodFileDegradeFeignClient implements VodClient {
    @Override
    public R deleteVideoById(String id) { // 熔断的时候调用


        return R.error().message("删除单个视频  熔断");
    }

    @Override
    public R removeVideoList(List<String> videoIdList) { // 熔断的时候调用
        return R.error().message("删除多个视频  熔断");
    }
}
