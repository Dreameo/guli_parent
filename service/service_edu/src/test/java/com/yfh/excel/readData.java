package com.yfh.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class readData {

    // 定义excel表头名称
    @ExcelProperty(value = "一级标题", index = 0) // 对应表中的第一列
    private String sno;

    @ExcelProperty(value = "二级标题", index = 1) // 对应表中第二列
    private String name;
}
