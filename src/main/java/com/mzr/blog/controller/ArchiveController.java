package com.mzr.blog.controller;

import com.mzr.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Author: Ryan
 * @Description:
 * @Date: Create in 17:27 2020/2/27
 */
@Controller
public class ArchiveController {

    @Autowired
    private BlogService blogServiceImpl;

    @GetMapping("/archive")
    public String archiveShow(Model model){
        //传递博客数
        model.addAttribute("blogCount",blogServiceImpl.findBlogNum());
        //传递归档数据
        model.addAttribute("archiveMap",blogServiceImpl.findBlogsByTime());
        return "archives";
    }
}
