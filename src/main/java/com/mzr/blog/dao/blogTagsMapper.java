package com.mzr.blog.dao;

import com.mzr.blog.pojo.blogTags;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface blogTagsMapper {

    @Insert("insert into t_blog_tags values(#{blogsId},#{tagsId})")
    int insBlogAndTag(blogTags bts);

    @Delete("delete from t_blog_tags where blogs_id = #{blogsId}")
    int delBlogAndTagByblogId(Long blogId);
}