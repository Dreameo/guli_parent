package com.yfh.servicebase.exception;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 自定义异常类
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GuliException extends RuntimeException {
    @ApiModelProperty("异常状态码")
    private Integer code;
    @ApiModelProperty("异常状态信息")
    private String msg;

}
