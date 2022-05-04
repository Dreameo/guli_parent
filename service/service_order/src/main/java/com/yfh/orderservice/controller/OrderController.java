package com.yfh.orderservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yfh.commonutils.JwtUtils;
import com.yfh.commonutils.R;
import com.yfh.orderservice.entity.Order;
import com.yfh.orderservice.service.OrderService;
import com.yfh.orderservice.utils.HttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author Dreameo
 * @since 2022-05-04
 */
@RestController
@RequestMapping("/eduorder/order")
@CrossOrigin
public class OrderController {

    @Autowired
    private OrderService orderService;

    // 1. 生成订单接口   //根据课程id和用户id创建订单，返回订单id
    @PostMapping("/createOrder/{course_id}") // 根据课程id生成订单id
    public R createOrder(@PathVariable String course_id, HttpServletRequest request) {
        // token存在request header中 用工具类进行获取
        String userid = JwtUtils.getMemberIdByJwtToken(request);

        String orderNo = orderService.createOrderByCourseId(course_id, userid);

        // 返回订单号
        return R.ok().data("orderNo", orderNo);
    }

    // 2. 根据订单id查询订单信息
    @GetMapping("/getOrderDetailById/{orderNo}")
    public R getOrderDeatilById(@PathVariable String orderNo) {
        Order order = orderService.getOne(new QueryWrapper<Order>().eq("order_no", orderNo));

        return R.ok().data("order", order);
    }
}

