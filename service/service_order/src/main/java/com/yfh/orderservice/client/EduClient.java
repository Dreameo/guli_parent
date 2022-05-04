package com.yfh.orderservice.client;

import com.yfh.ordervo.OrderCourseWebVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient("service-edu")
public interface EduClient {

    // 3. 订单中需要获取的 订单信息 根据id获取
    @GetMapping("/eduservice/coursefront/getOrderCourseById/{course_id}")
    public OrderCourseWebVo getOrderCourseById(@PathVariable("course_id") String course_id);
}
