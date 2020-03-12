package com.mzr.blog.dao;

import com.mzr.blog.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {

    //根据用户名和密码查询用户
    @Select("select * from t_user where username = #{username} and password = #{password}")
    User findByUsernameAndPassword(@Param("username") String username,@Param("password") String password);
}