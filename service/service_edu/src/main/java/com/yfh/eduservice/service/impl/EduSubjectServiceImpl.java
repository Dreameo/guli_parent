package com.yfh.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.yfh.eduservice.entity.EduSubject;
import com.yfh.eduservice.entity.excel.SubjectData;
import com.yfh.eduservice.listener.SubjectExcelListener;
import com.yfh.eduservice.mapper.EduSubjectMapper;
import com.yfh.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-04-22
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Override
    public void addSubject(MultipartFile file, EduSubjectService subjectService) { // 添加课程
        try {
            // 文件输入流
            InputStream inputStream = file.getInputStream();

            // 调用方法进行读取, 不能交给监听器这个类不能够被Spring管理，每次使用单独的new出来
            //有个很重要的点 SubjectExcelListener 不能被spring管理，要每次读取excel都要new,然后里面用到spring可以构造方法传进去
            EasyExcel.read(inputStream, SubjectData.class, new SubjectExcelListener(subjectService)).sheet().doRead();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
