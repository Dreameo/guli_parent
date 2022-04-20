package com.yfh.servicebase.exception;

import com.yfh.commonutils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ResponseBody // 可以返回数据格式
    @ExceptionHandler(Exception.class) // 捕获的异常
    public R error(Exception e) {
        e.printStackTrace();
        return R.error().message("捕获到了全局异常");
    }


    @ResponseBody // 可以返回数据格式
    @ExceptionHandler(ArithmeticException.class) // 捕获的异常
    public R error(ArithmeticException e) {
        e.printStackTrace();
        return R.error().message("捕获到ArithmeticException 特定异常");
    }


    @ResponseBody // 可以返回数据格式
    @ExceptionHandler(GuliException.class) // 捕获的异常
    public R error(GuliException e) {
        e.printStackTrace();
//        log.error(e.getMsg());
        log.error(ExceptionUtil.getMessage(e));
        return R.error().code(e.getCode()).message(e.getMsg());
    }


}
