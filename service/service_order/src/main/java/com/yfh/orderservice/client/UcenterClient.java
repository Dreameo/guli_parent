package com.yfh.orderservice.client;

import com.yfh.ordervo.OrderUmemberVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient("service-ucenter")
public interface UcenterClient {

    // 根据 用户id查询用户信息
    @GetMapping("/eduucenter/ucentermember/getOrderMemberById/{user_id}") // 路径写全
    public OrderUmemberVo getOrderMemberById(@PathVariable("user_id") String user_id);  // Pathvariable 注解 也要写名字

}
