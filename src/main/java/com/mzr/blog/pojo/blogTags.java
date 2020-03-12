package com.mzr.blog.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class blogTags {
    private Long blogsId;   //博客编号
    private Long tagsId;    //标签编号
}