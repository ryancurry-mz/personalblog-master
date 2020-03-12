package com.mzr.blog.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mzr.blog.pojo.Blog;
import com.mzr.blog.pojo.Tag;
import com.mzr.blog.service.BlogService;
import com.mzr.blog.service.TagService;
import com.mzr.blog.service.TypeService;
import com.mzr.blog.vo.TagLimit;
import com.mzr.blog.vo.TypeLimit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Author: Ryan
 * @Description:
 * @Date: Create in 21:16 2020/2/6
 */
@Controller
public class IndexController {

    @Autowired
    private BlogService blogServiceImpl;
    @Autowired
    private TagService tagServiceImpl;
    @Autowired
    private TypeService typeServiceImpl;

    //首页内容
    @GetMapping("/")
    public String index(@RequestParam(value = "pageNum", defaultValue = "1")Integer pageNum,
                        Model model) {
        PageHelper.startPage(pageNum,6);
        //博客模块
        List<Blog> listBlog = blogServiceImpl.listBlog();
        //使用pageInfo封装查询后的结果
        PageInfo pageInfo = new PageInfo(listBlog);
        //分类模块
        List<TypeLimit> listTypeTop = blogServiceImpl.BlogWithTypeTop(6);
        System.out.println("listTypeTop" + listTypeTop);
        //筛标签模块
        List<TagLimit> listTagTop = blogServiceImpl.BlogWithTagTop(10);
        //推荐方法模块
        List<Blog> blogTopList = blogServiceImpl.findBlogByRecommend(8);
        model.addAttribute("blogs",pageInfo);
        model.addAttribute("typeTop",listTypeTop);
        model.addAttribute("tagTop",listTagTop);
        model.addAttribute("blogTop",blogTopList);
        return "index";
    }

    //搜索框
    @PostMapping("/search/{pageNo}")
    public String search(@PathVariable Integer pageNo,
                         @RequestParam String query, Model model){
        PageHelper.startPage(pageNo,6);
        List<Blog> listBlog = blogServiceImpl.fuzzyQueryBlogByTitleOrContent(query);
        PageInfo pageInfo = new PageInfo(listBlog);
        model.addAttribute("blogs",pageInfo);
        model.addAttribute("query",query);
        return "search";
    }

    //将传来的标签id字符串转为标签id集合
    private Set<Tag> convertToList(String ids){
        Set<Tag> set = new HashSet<>();
        if(!"".equals(ids) && ids != null){
            String[] idArray =  ids.split(",");
            for (int i = 0; i < idArray.length; i++) {
                Tag tag = new Tag();
                Long id = Long.valueOf(idArray[i]);
                tag.setId(id);
                tag.setName(tagServiceImpl.getTag(id).getName());
                set.add(tag);
            }
        }
        return set;
    }

    //博客详情
    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id, Model model) {
        Blog blog = blogServiceImpl.getAndConvertBlog(id);
        Set<Tag> set = convertToList(blog.getTagIds());
        blog.setTagSet(set);
        model.addAttribute("blog",blog);
        return "blog";
    }


    //footer最新博客展示
    @GetMapping("/footer/blog")
    public String footerBlog(Model model){
        model.addAttribute("blogList",blogServiceImpl.findBlogByRecommend(3));
        return "_fragments :: footerBlogList";
    }
}
