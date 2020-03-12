package com.mzr.blog.service.impl;

import com.mzr.blog.NotFoundException;
import com.mzr.blog.dao.TypeMapper;
import com.mzr.blog.pojo.Type;
import com.mzr.blog.service.TypeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: Ryan
 * @Description:
 * @Date: Create in 17:01 2020/2/10
 */
@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    private TypeMapper typeMapper;

    @Transactional
    @Override
    public int saveType(Type type) {
        return typeMapper.saveType(type);
    }

    @Transactional
    @Override
    public Type getType(Long id) {
        return typeMapper.findType(id);
    }

    @Transactional
    @Override
    public List<Type> listType() {
        return typeMapper.listType();
    }

    @Transactional
    @Override
    public int updateType(Long id,String name) {
//        Type t = typeMapper.findType(type.getId());
//        if(t == null){
//            throw new NotFoundException("该类型不存在！");
//        }
//        BeanUtils.copyProperties(type, t);
//          return typeMapper.updateType(t);

        return typeMapper.updateType(id, name);
    }

    @Transactional
    @Override
    public void deleteType(Long id) {
        typeMapper.deleteType(id);
    }

    @Override
    public int checkName(String name) {
        return typeMapper.getTypeByName(name);
    }
}
