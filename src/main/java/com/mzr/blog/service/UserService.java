package com.mzr.blog.service;

import com.mzr.blog.pojo.User;

/**
 * @Author: Ryan
 * @Description:
 * @Date: Create in 21:51 2020/2/9
 */
public interface UserService {

    //根据用户名和密码查询用户
    User checkUser(String username, String password);
}
