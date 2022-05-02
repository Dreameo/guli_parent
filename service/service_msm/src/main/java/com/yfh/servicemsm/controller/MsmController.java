package com.yfh.servicemsm.controller;

import com.yfh.commonutils.R;
import com.yfh.servicemsm.service.MsmService;
import com.yfh.servicemsm.utils.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/edumsm/msm")
@CrossOrigin
public class MsmController {

    @Autowired
    private MsmService msmService;

    @Autowired
    private RedisTemplate redisTemplate;

    // 发送验证码
    @GetMapping("/send/{phone}")
    public R send(@PathVariable String phone) {

        // 1. 如果redis 根据手机号码 能够查到验证码 那么就在redis中找
        ValueOperations valueOperations = redisTemplate.opsForValue();
        String redisCode = (String) valueOperations.get("msm::" + phone);
        if(!StringUtils.isEmpty(redisCode)) {
            return R.ok().data("code", redisCode);
        }

        // 2. 如果不存在 那么就发送验证码


        // 从工具类中获取4位随机值
        String code = RandomUtil.getFourBitRandom();
        Map<String, String> params = new HashMap<>();
        params.put("code", code);
        boolean isSuccess = msmService.send(params, phone);
        if(isSuccess) {
            // 设置失效时间
            valueOperations.set("msm::" + phone, code, 5 , TimeUnit.MINUTES);
            return R.ok().data("code", code);
        }

        return R.error();
    }

    @GetMapping("/test")
    public R test() {
        return R.ok().data("hello", "world");
    }
}
