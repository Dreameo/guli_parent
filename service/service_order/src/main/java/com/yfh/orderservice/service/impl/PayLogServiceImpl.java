package com.yfh.orderservice.service.impl;

import com.yfh.orderservice.entity.PayLog;
import com.yfh.orderservice.mapper.PayLogMapper;
import com.yfh.orderservice.service.PayLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 支付日志表 服务实现类
 * </p>
 *
 * @author Dreameo
 * @since 2022-05-04
 */
@Service
public class PayLogServiceImpl extends ServiceImpl<PayLogMapper, PayLog> implements PayLogService {

}
