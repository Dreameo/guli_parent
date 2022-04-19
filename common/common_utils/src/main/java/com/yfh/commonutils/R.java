package com.yfh.commonutils;
import com.google.common.collect.Maps;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 统一结果返回
 */
@Data
public class R {

    @ApiModelProperty(value = "是否成功")
    private Boolean success;
    @ApiModelProperty(value = "返回码")
    private Integer code;
    @ApiModelProperty(value = "返回消息")
    private String message;
    @ApiModelProperty(value = "返回数据")
    private Map<String, Object> data = new HashMap<String, Object>();

    private R(){} // 其他方法不能调用


    // 成功静态方法
    public static R ok() {
        R r = new R(); // 本类中调用构造方法
        r.setSuccess(true); // 返回成功
        r.setCode(ResultCode.SUCCESS); // 返回状态码
        r.setMessage("成功");

        return r;
    }

    // 失败静态方法
    public static R error() {
        R r = new R(); // 本类中调用构造方法
        r.setSuccess(false); // 返回成功
        r.setCode(ResultCode.ERROR); // 返回状态码
        r.setMessage("失败");
        return r;
    }

    // 上面没有  map 因此下面提供设置 map值
    // 可以实现链式编程 R.ok().code().message()..
    public R success(Boolean success){ //
        this.setSuccess(success);
        return this;
    }
    public R message(String message){
        this.setMessage(message);
        return this;
    }
    public R code(Integer code){
        this.setCode(code);
        return this;
    }
    public R data(String key, Object value){
        this.data.put(key, value);
        return this;
    }

    public R data(Map<String, Object> map) {
        this.setData(map);
        return this;
    }

}
