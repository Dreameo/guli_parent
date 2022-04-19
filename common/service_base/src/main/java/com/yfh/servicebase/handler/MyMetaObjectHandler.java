package com.yfh.servicebase.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class MyMetaObjectHandler implements MetaObjectHandler {
    // 修改操作的时候，这个方法会自动调用
    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("gmtCreate", new Date(), metaObject);
        // meta  ：元数据，表名称，表字段  元数据（MetaData），是指定义数据结构的数据。那么数据库元数据就是指定义数据库各类对象结构的数据。
        // 例如数据库中的数据库名，表明， 列名、用户名、版本名以及从SQL语句得到的结果中的大部分字符串是元数据
        this.setFieldValByName("gmtModified", new Date(), metaObject); // 属性值
    }

    // 修改操作的时候，这个方法会自动调用
    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("gmtModified", new Date(), metaObject);
    }
}