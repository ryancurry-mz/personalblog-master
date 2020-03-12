package com.mzr.blog.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class Type {
    private Long id;    //类别编号
    @NotBlank(message = "分类名称不能为空")
    private String name;    //类别名
}