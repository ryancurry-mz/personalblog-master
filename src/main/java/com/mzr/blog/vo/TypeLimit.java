package com.mzr.blog.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Author: Ryan
 * @Description:
 * @Date: Create in 2:36 2020/2/19
 */
@Getter
@Setter
@ToString
//用于博客首页筛选出使用次数最多的博客分类（前X个）
public class TypeLimit {
    private Long id;
    private String name;
    //博客数量
    private int count;
}
