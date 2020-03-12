package com.mzr.blog.dao;

import com.mzr.blog.pojo.Blog;
import com.mzr.blog.pojo.Tag;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface TagMapper {

    //新增标签类型
    @Insert("insert into t_tag values(default, #{name})")
    int saveTag(Tag Tag);

    //根据Id查询标签类型
    @Select("select * from t_tag where id = #{id}")
    Tag findTag(@Param("id") Long id);

    //查询所有标签
    @Select("select * from t_tag")
    List<Tag> listTag();

    //根据id集合查询标签
    List<Tag> listTagById(List<Long> list);

    //更新标签
    @Update("update t_tag set name = #{name} where id = #{id}")
    int updateTag(Long id,String name);

    //删除标签
    @Delete("delete from t_tag where id = #{id}")
    void deleteTag(Long id);

    //根据名称查找标签类别
    @Select("select count(*) from t_tag where name = #{name}")
    int getTagByName(String name);

    //根据博客id查找标签
    List<Tag> findTagById(@Param("id")Long id);

}