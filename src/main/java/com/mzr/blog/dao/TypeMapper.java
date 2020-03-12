package com.mzr.blog.dao;

import com.mzr.blog.pojo.Type;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface TypeMapper {

    //新增博客类型
    @Insert("insert into t_type values(default, #{name})")
    int saveType(Type type);

    //根据Id查询博客类型
    @Select("select * from t_type where id = #{id}")
    Type findType(@Param("id") Long id);

    //查询所有博客
    @Select("select * from t_type")
    List<Type> listType();

    //更新博客
    @Update("update t_type set name = #{name} where id = #{id}")
    int updateType(Long id,String name);

    //删除博客
    @Delete("delete from t_type where id = #{id}")
    void deleteType(Long id);

    //根据名称查找博客类别
    @Select("select count(*) from t_type where name = #{name}")
    int getTypeByName(String name);
}