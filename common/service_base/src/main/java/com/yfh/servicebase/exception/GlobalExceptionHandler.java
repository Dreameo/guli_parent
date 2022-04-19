package com.yfh.servicebase.exception;

import com.yfh.commonutils.R;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody // 可以返回数据格式
    @ExceptionHandler(Exception.class) // 捕获的异常
    public R error(Exception e) {
        e.printStackTrace();
        return R.error().message("捕获到了全局异常");
    }
}
