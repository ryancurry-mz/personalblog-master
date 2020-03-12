package com.mzr.blog.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
@Getter
@Setter
@ToString
public class Tag {
    private Long id;    //标签编号
    private String name;    //标签名
}