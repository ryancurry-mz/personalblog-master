package com.mzr.blog.service.impl;

import com.mzr.blog.dao.CommentMapper;
import com.mzr.blog.pojo.Comment;
import com.mzr.blog.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: Ryan
 * @Description:
 * @Date: Create in 17:23 2020/2/22
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public List<Comment> findCommentsByBlogId(Long blogId) {
        List<Comment> comments = commentMapper.CommentsByBlogIdNull(blogId,Long.parseLong("-1"));
        return eachComment(comments);
    }

    @Transactional
    @Override
    public int insComment(Comment comment) {
        Long parentCommentId = comment.getParentCommentId();
        if(parentCommentId != -1){
            comment.setParentComment(commentMapper.findParentComment(parentCommentId, comment.getId()));
        }else{
            comment.setParentComment(null);
        }
        comment.setCreateTime(new Date());
        return commentMapper.saveComment(comment);
    }

    @Override
    public Comment findCommentParent(Long parentId, Long commentId) {
        return commentMapper.findParentComment(parentId,commentId);
    }

    @Override
    public List<Comment> findSonComment(Long parentId) {
        return commentMapper.findSonComment(parentId);
    }

    @Override
    public void delComment(Long id, Long blogId) {
        commentMapper.delComment(id,blogId);
    }

    /**
     * 循环每个顶级的评论节点
     * @param comments
     * @return
     */
    private List<Comment> eachComment(List<Comment> comments) {
        List<Comment> commentsView = new ArrayList<>();
        for (Comment comment : comments) {
            Comment c = new Comment();
            BeanUtils.copyProperties(comment,c);
            c.setReplyComments(findSonComment(c.getId()));
            commentsView.add(c);
        }
        //合并评论的各层子代到第一级子代集合中
        combineChildren(commentsView);
        return commentsView;
    }

    /**
     *
     * @param comments root根节点，blog不为空的对象集合
     * @return
     */
    private void combineChildren(List<Comment> comments) {

        for (Comment comment : comments) {
            List<Comment> replys1 = comment.getReplyComments();
            for(Comment reply1 : replys1) {
                //循环迭代，找出子代，存放在tempReplys中
                reply1.setParentComment(commentMapper.findParentComment(Long.parseLong("-1"),reply1.getParentCommentId()));
                recursively(reply1);
            }
            //修改顶级节点的reply集合为迭代处理后的集合
            comment.setReplyComments(tempReplys);
//            System.out.println("迭代处理后的顶级节点集合：" + comment.getReplyComments());
            //清除临时存放区
            tempReplys = new ArrayList<>();
        }
    }

    //存放迭代找出的所有子代的集合
    private List<Comment> tempReplys = new ArrayList<>();
    /**
     * 递归迭代，剥洋葱
     * @param comment 被迭代的对象
     * @return
     */
    private void recursively(Comment comment) {
        comment.setReplyComments(commentMapper.findSonComment(comment.getId()));
        System.out.println("comment" + comment);
        tempReplys.add(comment);//顶节点添加到临时存放集合
        if (comment.getReplyComments().size()>0) {
            List<Comment> replys = comment.getReplyComments();
            for (Comment reply : replys) {
                reply.setParentComment(commentMapper.findParentComment(reply.getParentCommentId(),reply.getId()));
                System.out.println("递归迭代：" + reply);
                tempReplys.add(reply);
                if (reply.getReplyComments().size()>0) {
                    reply.setParentComment(commentMapper.findParentComment(reply.getParentCommentId(),reply.getId()));
                    recursively(reply);
                }
            }
        }
    }
}
