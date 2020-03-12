package com.mzr.blog.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
public class Comment {
    private Long id;    //评论编号
    private Boolean adminComment;   //管理员评论
    private String avatar;  //头像
    private String content; //内容
    private Date createTime;    //创建时间
    private String email;   //邮箱
    private String nickname;    //昵称
    private Long blogId;    //博客编号
    private Long parentCommentId;   //父级评论

    private Blog blog;
    private Comment parentComment;
    private List<Comment> replyComments = new ArrayList<>();
}
