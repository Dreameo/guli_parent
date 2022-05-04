package com.yfh.orderservice.service.impl;

import com.yfh.orderservice.client.EduClient;
import com.yfh.orderservice.client.UcenterClient;
import com.yfh.orderservice.entity.Order;
import com.yfh.orderservice.mapper.OrderMapper;
import com.yfh.orderservice.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yfh.orderservice.utils.OrderNoUtil;
import com.yfh.ordervo.OrderCourseWebVo;
import com.yfh.ordervo.OrderUmemberVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author Dreameo
 * @since 2022-05-04
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    // 将 远程调用注入
    @Autowired
    private EduClient eduClient;

    @Autowired
    private UcenterClient ucenterClient;

    @Override
    public String createOrderByCourseId(String course_id, String userid) {

        // 1.从jwt中得到用户id 然后从ucenter模块获取用户信息
        OrderUmemberVo orderUmemberVo = ucenterClient.getOrderMemberById(userid);

        // 2. 根据courseid 在edu模块中查询课程信息
        OrderCourseWebVo orderCourseWebVo = eduClient.getOrderCourseById(course_id);

        //创建订单
        Order order = new Order();
        // 给订单进行赋值
        order.setOrderNo(OrderNoUtil.getOrderNo());
        order.setCourseId(course_id);
        order.setCourseTitle(orderCourseWebVo.getTitle());
        order.setCourseCover(orderCourseWebVo.getCover());
        order.setTeacherName(orderCourseWebVo.getTeacherName());
        order.setTotalFee(orderCourseWebVo.getPrice());
        order.setMemberId(userid);
        order.setMobile(orderUmemberVo.getMobile());
        order.setNickname(orderUmemberVo.getNickname());
        order.setStatus(0);
        order.setPayType(1);
        baseMapper.insert(order);

        return order.getOrderNo();
    }
}
