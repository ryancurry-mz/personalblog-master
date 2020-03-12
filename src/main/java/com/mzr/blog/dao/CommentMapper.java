package com.mzr.blog.dao;

import com.mzr.blog.pojo.Comment;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface CommentMapper {

    //根据博客编号查询该篇博客的评论
    @Select("select * from t_comment where blog_id = #{blogId} and parent_comment_id = #{blogParentId}")
    List<Comment> CommentsByBlogIdNull(@Param("blogId") Long blogId, @Param("blogParentId") Long blogParentId);

    //保存评论
    @Insert("insert into t_comment values(default,#{adminComment},#{avatar}," +
            "#{content},#{createTime},#{email},#{nickname},#{blogId},#{parentCommentId})")
    @Options(useGeneratedKeys = true,keyProperty = "id")
    int saveComment(Comment comment);

    //查询父评论
    @Select("select * from t_comment where parent_comment_id = #{parentCommentId} and id = #{commentId}")
    Comment findParentComment(Long parentCommentId,Long commentId);

    //查询该标签下的所有子标签
    @Select("select * from t_comment where parent_comment_id = #{parentId}")
    List<Comment> findSonComment(Long parentId);

    //删除评论
    @Delete("delete from t_comment where id = #{id} and blog_id = #{blogId}")
    void delComment(Long id,Long blogId);
}