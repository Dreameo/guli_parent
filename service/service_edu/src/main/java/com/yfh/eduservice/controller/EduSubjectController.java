package com.yfh.eduservice.controller;


import com.yfh.commonutils.R;
import com.yfh.eduservice.entity.OneSubject;
import com.yfh.eduservice.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-04-22
 */
@RestController
@RequestMapping("/eduservice/subject")
@CrossOrigin // 解决跨域问题
public class EduSubjectController {

    @Autowired
    private EduSubjectService subjectService;

    @PostMapping("/addSubject") // 上传excel 添加课程信息
    public R addSubject(MultipartFile file) {
        subjectService.addSubject(file, subjectService);
        return R.ok();
    }

    @GetMapping("/list")
    public R list() {
        List<OneSubject> list = subjectService.getList();
        return R.ok().data("list", list);
    }
}

