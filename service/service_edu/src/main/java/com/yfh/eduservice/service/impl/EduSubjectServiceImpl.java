package com.yfh.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yfh.eduservice.entity.EduSubject;
import com.yfh.eduservice.entity.OneSubject;
import com.yfh.eduservice.entity.TwoSubject;
import com.yfh.eduservice.entity.excel.SubjectData;
import com.yfh.eduservice.listener.SubjectExcelListener;
import com.yfh.eduservice.mapper.EduSubjectMapper;
import com.yfh.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public List<OneSubject> getList() {
        // 1. 找出所有的一级分类 可以使用 mapper 或者 本身的service 进行查找
        List<EduSubject> oneSubjects = baseMapper.selectList(new QueryWrapper<EduSubject>().eq("parent_id", "0"));
//        List<EduSubject> oneSubjects = this.list(new QueryWrapper<EduSubject>().eq("parent_id", "0"));
//
        // 2. 找出所有的二级分类
        List<EduSubject> twoSubjects = baseMapper.selectList(new QueryWrapper<EduSubject>().ne("parent_id", "0"));

        // 3. 封装一级目录
        // 先创建一个集合  用于存入最终封装的数据
        List<OneSubject> finalSubjectList = new ArrayList<>();

        for(EduSubject one : oneSubjects) {
//            finalSubjectList.add(new OneSubject(one.getId(), one.getTitle()));
//            BeanUtils.copyProperties(one, new OneSubject()); // 工具类 将 one 属性值放到 OneSubject()对象中

            OneSubject oneSubject = new OneSubject();
            BeanUtils.copyProperties(one, oneSubject); // 没有的值 设置为null

            List<TwoSubject> twoSubjectList = new ArrayList<>();
            // 4. 封装二级目录
            for (EduSubject two : twoSubjects) {
                if(two.getParentId().equals(one.getId())) {
                    TwoSubject twoSubject = new TwoSubject();
                    BeanUtils.copyProperties(two, twoSubject);
                    twoSubjectList.add(twoSubject);
                }
            }
            oneSubject.setChildren(twoSubjectList);
            finalSubjectList.add(oneSubject);
        }



        return finalSubjectList;
    }
}
