package com.mzr.blog.service;

import com.mzr.blog.pojo.Comment;

import java.util.List;

/**
 * @Author: Ryan
 * @Description:
 * @Date: Create in 17:23 2020/2/22
 */
public interface CommentService {

    List<Comment> findCommentsByBlogId(Long blogId);

    int insComment(Comment comment);

    Comment findCommentParent(Long parentId,Long commentId);

    List<Comment> findSonComment(Long parentId);

    void delComment(Long id, Long blogId);
}
