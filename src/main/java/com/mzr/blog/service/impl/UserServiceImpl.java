package com.mzr.blog.service.impl;

import com.mzr.blog.dao.UserMapper;
import com.mzr.blog.pojo.User;
import com.mzr.blog.service.UserService;
import com.mzr.blog.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: Ryan
 * @Description:
 * @Date: Create in 22:13 2020/2/9
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User checkUser(String username, String password) {
        User user = userMapper.findByUsernameAndPassword(username, MD5Utils.MD5Encode(password,"utf-8"));
        return user;
    }
}
