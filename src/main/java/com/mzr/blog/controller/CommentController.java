package com.mzr.blog.controller;

import com.mzr.blog.pojo.Comment;
import com.mzr.blog.pojo.User;
import com.mzr.blog.service.BlogService;
import com.mzr.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Ryan
 * @Description:
 * @Date: Create in 17:57 2020/2/22
 */
@Controller
public class CommentController {

    @Autowired
    private CommentService commentServiceImpl;

    @Value("${comment.avatar}")
    private String avatar;

    //展示该博客的评论
    @GetMapping("/comment/{blogId}")
    public String comments(@PathVariable Long blogId, Model model){
        List<Comment> commentList = commentServiceImpl.findCommentsByBlogId(blogId);
        model.addAttribute("comments",commentList);
//        for(Comment comment : commentList){
//            System.out.println("第一级评论列表:" + comment.getId() + comment.getReplyComments());
//            System.out.println("子评论：" + comment.getReplyComments().size());
//        }
        return "blog :: commentList";
    }

    //保存评论
    @PostMapping("/comment")
    public String saveComment(Comment comment, HttpSession session){
        Long blogId = comment.getBlog().getId();
        Long commentParentId = comment.getParentComment().getId();
        User user = (User) session.getAttribute("user");
        if(user != null){
            comment.setAvatar(user.getAvatar());
            comment.setAdminComment(true);

        }else{
            comment.setAvatar(avatar);
        }
        comment.setBlogId(blogId);
        comment.setParentCommentId(commentParentId);
        System.out.println(comment);
        commentServiceImpl.insComment(comment);
        return "redirect:/comment/" + blogId;
    }

    //删除评论
    @GetMapping("/comment/{id}/{blogId}")
    public String delComment(@PathVariable Long id,@PathVariable Long blogId, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(id != null && blogId != null){
            commentServiceImpl.delComment(id,blogId);
            System.out.println("删除成功");
        }
        return "redirect:/blog/" + blogId;
    }
}
