package com.mzr.blog.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mzr.blog.pojo.Blog;
import com.mzr.blog.pojo.Tag;
import com.mzr.blog.service.BlogService;
import com.mzr.blog.service.TagService;
import com.mzr.blog.vo.TagLimit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Ryan
 * @Description:
 * @Date: Create in 21:50 2020/2/26
 */
@Controller
public class TagShowController {

    @Autowired
    private BlogService blogServiceImpl;

    //标签页面展示
    @GetMapping("/tag/{id}")
    public String tagShow(@RequestParam(value = "pageNum",defaultValue = "-1")Integer pageNum,
                          @PathVariable Long id, Model model){
        List<TagLimit> listTag = blogServiceImpl.listTagByBlogNum();
        if (id == -1){
            id = listTag.get(0).getId();
        }
        List<Blog> listBlog = blogServiceImpl.findBlogsByTagId(id);
        PageHelper.startPage(pageNum,30);
        PageInfo pageInfo = new PageInfo(listBlog);
        model.addAttribute("tags",listTag);
        model.addAttribute("blogs",pageInfo);
        model.addAttribute("activeTagId",id);
        return "tags";
    }
}
