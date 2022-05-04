package com.yfh.orderservice.service;

import com.yfh.orderservice.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author Dreameo
 * @since 2022-05-04
 */
public interface OrderService extends IService<Order> {

    String createOrderByCourseId(String course_id, String userid);

}
