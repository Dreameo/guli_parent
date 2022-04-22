package com.yfh.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.CellData;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yfh.eduservice.entity.EduSubject;
import com.yfh.eduservice.entity.excel.SubjectData;
import com.yfh.eduservice.service.EduSubjectService;
import com.yfh.servicebase.exception.GuliException;

import java.util.Map;

public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {


    // 用来操作 数据库  每次调用读 方法 传入 subjectService
    private EduSubjectService subjectService;

    public SubjectExcelListener() {
    }

    public SubjectExcelListener(EduSubjectService subjectService) {
        this.subjectService = subjectService;
    }

    // 一行一行的读取数据
    @Override
    public void invoke(SubjectData data, AnalysisContext context) {
        if (data == null)
            throw new GuliException(20001, "excel数据为空");
        // 第一个值一级分类  第二个值二级分类

        // 判断一级分类是否重复
        String pid = "";
        if(!existOneSubject(data.getOneSubjectName(), subjectService)) { // 没有一级分类， 进行添加
            EduSubject eduSubject = new EduSubject();
            eduSubject.setTitle(data.getOneSubjectName());
            eduSubject.setParentId("0"); // 根目录了
            subjectService.save(eduSubject); // 添加一级分类
            pid = eduSubject.getId();
        }

        // 添加二级分类
        if(!existTwoSubject(data.getTwoSubjectName(), subjectService, pid)) {
            EduSubject eduSubject = new EduSubject();
            eduSubject.setTitle(data.getTwoSubjectName());
            eduSubject.setParentId(pid); // 输入进来的父目录
            subjectService.save(eduSubject); // 添加二级分类
        }
    }

    // 读取表头
    @Override
    public void invokeHead(Map<Integer, CellData> headMap, AnalysisContext context) {
        System.out.println("表头信息：" + headMap);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {

    }

    // 判断 是否重复添加一级分类
    public boolean existOneSubject(String name, EduSubjectService subjectService) {
        QueryWrapper<EduSubject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("title", name);
        queryWrapper.eq("parent_id", "0");
        EduSubject one = subjectService.getOne(queryWrapper);
        if (one == null) return false;
        return true;
    }

    // 判断 是否重复添加二级分类
    public boolean existTwoSubject(String name, EduSubjectService subjectService, String pid) {
        QueryWrapper<EduSubject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("title", name);
        queryWrapper.eq("parent_id", pid);
        EduSubject two = subjectService.getOne(queryWrapper);
        if (two == null) return false; // 如果查询为空， 那么不存在相同二级分类
        return true;
    }
}
