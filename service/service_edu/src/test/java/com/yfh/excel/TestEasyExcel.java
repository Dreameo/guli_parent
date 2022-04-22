package com.yfh.excel;

import com.alibaba.excel.EasyExcel;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TestEasyExcel {


    @Test
    public void test() {
        // 实现 向 excel 文件中 写数据

        // 1. 设置写入文件夹地址和excel文件名称
        String filename = "E:\\Projects\\项目\\guli_parent\\service\\service_edu\\src\\test\\java\\com\\yfh\\excel\\result\\测试写excel.xls";

        // 2. 调用 easyExcel 方法 两个参数
        //    第一个参数 文件路径   第二个参数 对应实体类的class
        EasyExcel.write(filename, ExcelData.class).sheet("学生表").doWrite(getData());
    }


    public List<ExcelData> getData() {
        List<ExcelData> list = new ArrayList<>();

        for(int i = 0; i < 10; i++) {
            ExcelData data = new ExcelData();
            data.setSno(i);
            data.setName("学生姓名" + i);
            list.add(data);
        }
        return list;
    }
}
