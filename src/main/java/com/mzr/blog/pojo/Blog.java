package com.mzr.blog.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.*;

@Getter
@Setter
@ToString
public class Blog {
    private Long id;    //博客编号
    private Integer views;  //浏览次数
    private String description; //博客描述
    private String firstPicture;    //博客首图
    private String flag;    //博客标记
    private String title;   //博客标题
    private Boolean appreciation;   //博客赞赏是否开启
    private Boolean commendable;   //博客评论是否开启
    private Boolean published;  //是否发布
    private Boolean recommend;  //是否推荐
    private Boolean shareStatement; //转载声明是否开启
    private Date createTime;    //创建时间
    private Date updateTime;    //更新时间
    private Long typeId;    //博客类别编号
    private Long userId;    //用户编号
    private String content; //博客内容

    private Type type;  //博客类别
    private User user;  //用户

    private List<Tag> tags = new ArrayList<>();
    private List<Comment> comments = new ArrayList<>();
    private String tagIds;

    private Set<Tag> tagSet = new HashSet<>();
}