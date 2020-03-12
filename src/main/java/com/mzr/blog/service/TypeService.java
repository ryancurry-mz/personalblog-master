package com.mzr.blog.service;

import com.mzr.blog.pojo.Type;

import java.util.List;


/**
 * @Author: Ryan
 * @Description:
 * @Date: Create in 16:49 2020/2/10
 */
public interface TypeService {
    int saveType(Type type);

    Type getType(Long id);

    List<Type> listType();

    int updateType(Long id, String name);

    void deleteType(Long id);

    int checkName(String name);
}
