package com.mzr.blog.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class User {
    private Long id;    //用户编号
    private String avatar;  //用户头像
    private Date createTime;    //用户注册时间
    private String email;   //用户邮箱
    private String nickname;    //昵称
    private String password;    //密码
    private Integer type;   //类型
    private Date updateTime;    //修改时间
    private String username;    //用户名
}