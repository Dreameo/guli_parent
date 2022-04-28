package com.yfh.eduservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.catalina.LifecycleState;

import java.util.ArrayList;
import java.util.List;

/**
 * 一级分类
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OneSubject {

    private String id;

    private String title;

    private List<TwoSubject> children = new ArrayList<>();

    public OneSubject(String id, String title) {
        this.id = id;
        this.title = title;
    }
}
