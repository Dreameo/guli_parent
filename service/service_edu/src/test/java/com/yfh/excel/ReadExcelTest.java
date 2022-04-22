package com.yfh.excel;

import com.alibaba.excel.EasyExcel;
import org.junit.Test;

public class ReadExcelTest {

    @Test
    public void test() {

        // 调用方法
        // 1. 设置写入文件夹地址和excel文件名称
        String filename = "C:\\Users\\lab507\\Desktop\\标题测试.xls";

        // 2. 调用 easyExcel 方法 两个参数
        //    第一个参数 文件路径   第二个参数 对应实体类的class
        EasyExcel.read(filename, readData.class, new ExcelListener()).sheet().doRead();
    }
}
