package com.yfh.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.CellData;

import java.util.Map;

public class ExcelListener extends AnalysisEventListener<readData> {
    // 这个方法， 一行一行读取excel的内容
    @Override
    public void invoke(readData data, AnalysisContext context) {
        System.out.println(data);
    }

    // 读取表头信息
    @Override
    public void invokeHead(Map<Integer, CellData> headMap, AnalysisContext context) {
        System.out.println("表头：" + headMap);
    }

    // 读取完之后的操作
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {

    }
}
