package com.yfh.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yfh.commonutils.R;
import com.yfh.eduservice.entity.EduTeacher;
import com.yfh.eduservice.entity.vo.TeacherQuery;
import com.yfh.eduservice.service.EduTeacherService;
import com.yfh.servicebase.exception.GuliException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-04-19
 */
@Api(description="讲师管理")
@RestController
@RequestMapping("/eduservice/teacher")
@CrossOrigin // 解决跨域问题
public class EduTeacherController {
    @Autowired
    private EduTeacherService eduTeacherService;


    // 查询所有教师
    @ApiOperation("查询所有讲师")
    @GetMapping("/list")
    public R getAll() {
        List<EduTeacher> list = eduTeacherService.list(null);
        return  R.ok().data("items", list);
    }

    /**
     * 逻辑删除教师
     *
     */
    @ApiOperation("根据id删除讲师")
    @DeleteMapping("/{teach_id}")
    public R delete(@ApiParam("教师id") @PathVariable("teach_id") String id) {
        boolean b = eduTeacherService.removeById(id);
        if (b) return R.ok();
        return R.error();
    }

    /**
     * 分页查询结果
     */
    @ApiOperation("分页显示教师")
    @GetMapping("{page}/{limit}")
    public R pageTeacher(@PathVariable(value = "page", required = true) Long page, @PathVariable(value = "limit", required = true) Long limit) {
//        Page<User> page = new Page<>(1, 5);

        Page<EduTeacher> pageParam = new Page<>(page, limit);
        // service中有page方法 mapper中是select page
        eduTeacherService.page(pageParam, null); // 将数据全部封装到 pageParam中

        long total = pageParam.getTotal(); // 得到总记录数
        List<EduTeacher> records = pageParam.getRecords(); // 数据封装到 records中去


        /**
         * 模拟异常
         */
        try {
            int i = 10/ 0;
        } catch (Exception e) {
            throw new GuliException(20001,"出现自定义异常");
        }


        return R.ok().data("total", total).data("records", records);
    }

    /**
     * 多条件组合 查询
     */
    @ApiOperation("条件查询显示教师")
    @PostMapping("pageTeacherCondition/{page}/{limit}")
    public R ConditionPageTeacher(@PathVariable(value = "page") Long page, @PathVariable(value = "limit") Long limit, @RequestBody TeacherQuery teacherQuery) {

        // 1. 创建page
        Page<EduTeacher> pageTeacher = new Page<>(page, limit);


        // 创建条件, 多条件组合查询，可能有，可能没有
        // mybatis --》 动态sql

        QueryWrapper<EduTeacher> queryWrapper = new QueryWrapper<>();
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();

        // MyBatisPlus wrapper拼接，  判断条件值是否为空， 不为空就拼接条件
        if(!StringUtils.isEmpty(name)) {
            queryWrapper.like("name", name);
        }

        if(!StringUtils.isEmpty(level)) {
            queryWrapper.like("level", level); // 讲师头衔
        }

        if(!StringUtils.isEmpty(begin)) {
            queryWrapper.ge("gmt_create", begin); // 字段名
        }

        if(!StringUtils.isEmpty(end)) {
            queryWrapper.le("gmt_modified", end); // 字段名
        }

        // 排序显示
        queryWrapper.orderByDesc("gmt_create");

        // 2. 调用分页方法
        eduTeacherService.page(pageTeacher, queryWrapper);


        long total = pageTeacher.getTotal(); // 得到总记录数
        List<EduTeacher> records = pageTeacher.getRecords(); // 数据封装到 records中去
        return R.ok().data("total", total).data("records", records);
    }

    /**
     * 新增讲师
     */
    @ApiOperation("添加讲师")
    @PostMapping("/save")
    public R save(@RequestBody EduTeacher eduTeacher) {
        boolean save = eduTeacherService.save(eduTeacher);
        if(save)
            return R.ok();
        return R.error();
    }

    /**
     * 根据id查询讲师
     */

    @ApiOperation("根据id查询讲师")
    @GetMapping("/{id}")
    public R getById(@PathVariable String id) {
        EduTeacher teacher = eduTeacherService.getById(id);

        if(null != teacher)
            return R.ok().data("teacher", teacher);

        return R.error();
    }


    /**
     * 修改讲师， 1. 先根据id查询讲师， 2. 然后进行修改
     */

    @ApiOperation("修改讲师信息")
    @PostMapping("/updateTeacher")
    public R updateTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean update = eduTeacherService.updateById(eduTeacher);
        if (update)
            return R.ok();

        return R.error();
    }



}

