package com.mzr.blog.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Author: Ryan
 * @Description:
 * @Date: Create in 23:26 2020/2/19
 */
@Getter
@Setter
@ToString
//用于博客首页筛选出使用次数最多的博客标签（前X个）
public class TagLimit {
    private Long id;
    private String name;
    private int count;  //标签对应的博客数
}
