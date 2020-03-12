package com.mzr.blog.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mzr.blog.pojo.Blog;
import com.mzr.blog.service.BlogService;
import com.mzr.blog.vo.BlogQuery;
import com.mzr.blog.vo.TypeLimit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author: Ryan
 * @Description:
 * @Date: Create in 16:19 2020/2/26
 */
@Controller
public class TypeShowController {

    @Autowired
    private BlogService blogServiceImpl;

    //分类页面展示
    @GetMapping("/type/{id}")
    public String typeShow(@RequestParam(value = "pageNum",defaultValue = "-1")Integer pageNum,
                           @PathVariable Long id, Model model){
        //展示所有分类
        List<TypeLimit> listType = blogServiceImpl.listTypeByBlogNum();
        if (id == -1){
            id = listType.get(0).getId();
        }
        Blog blog = new Blog();
        blog.setTypeId(id);
        //展示选中分类下的所有博客
        List<Blog> listBlogByType = blogServiceImpl.findBlogsByTypeId(id);
        PageHelper.startPage(pageNum,30);
        PageInfo pageInfo = new PageInfo(listBlogByType);
        model.addAttribute("types",listType);
        model.addAttribute("blogs",pageInfo);
        model.addAttribute("activeTypeId",id);
        return "types";
    }
}
