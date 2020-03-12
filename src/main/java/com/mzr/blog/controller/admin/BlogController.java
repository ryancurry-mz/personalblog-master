package com.mzr.blog.controller.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mzr.blog.pojo.Blog;
import com.mzr.blog.pojo.Type;
import com.mzr.blog.pojo.User;
import com.mzr.blog.pojo.blogTags;
import com.mzr.blog.service.BlogAndTagsService;
import com.mzr.blog.service.BlogService;
import com.mzr.blog.service.TagService;
import com.mzr.blog.service.TypeService;
import com.mzr.blog.vo.BlogQuery;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * @Author: Ryan
 * @Description:
 * @Date: Create in 15:14 2020/2/10
 */
@Controller
@RequestMapping("/admin")
public class BlogController {

    private static final String LIST = "admin/blogs";
    private static final String INPUT = "admin/blogs-input";
    private static final String UPDATE = "admin/blogs-update";
    private static final String REDIRECT_LIST = "redirect:/admin/blogs";

    @Autowired
    private BlogService blogServiceImpl;
    @Autowired
    private TypeService typeServiceImpl;
    @Autowired
    private TagService tagServiceImpl;
    @Autowired
    private BlogAndTagsService blogAndTagsServiceImpl;

    //查询所有博客
    @GetMapping("/blogs")
    public String blogs(@RequestParam(value = "pageNum", defaultValue = "1")Integer pageNum,
                        Model model){
        //渲染类别下拉框的值
        List<Type> listType = typeServiceImpl.listType();
        model.addAttribute("types",listType);

        //引入分页插件
        PageHelper.startPage(pageNum,5);
        List<Blog> listBlog = blogServiceImpl.listBlog();
        //使用pageInfo封装查询后的结果
        PageInfo pageInfo = new PageInfo(listBlog);
        model.addAttribute("blogs",pageInfo);
        return LIST;
    }

    //根据条件查询博客
    @PostMapping("/blogs/search")
    public String searchBlog(@RequestParam(value = "pageNum", defaultValue = "1")Integer pageNum,
                             Model model,
                             BlogQuery blog){
        //引入分页插件
        PageHelper.startPage(pageNum,5);
        List<Blog> listBlog = blogServiceImpl.searchBlog(blog);
        for (int i = 0; i < listBlog.size(); i++) {
            System.out.println(listBlog.get(i));
        }

        //使用pageInfo封装查询后的结果
        PageInfo<Blog> pageInfo = new PageInfo(listBlog);
        model.addAttribute("blogs",pageInfo);
        return LIST + ":: blogList";
    }

    //获取所有博客类型和标签
    private void setTypeAndTag(Model model){
        model.addAttribute("types",typeServiceImpl.listType());
        model.addAttribute("tags",tagServiceImpl.listTag());
    }

    //进入增加博客页面
    @GetMapping("/blogs/input")
    public String inputBlog(Model model){
        setTypeAndTag(model);
        model.addAttribute(new Blog());
        return INPUT;
    }

    //进入修改博客页面
    @GetMapping("/blogs/{id}/input")
    public String editBlog(@PathVariable Long id, Model model){
        setTypeAndTag(model);
        Blog blog = blogServiceImpl.getBlog(id);
        model.addAttribute("blog",blog);
        return UPDATE;
    }

    //设置博客属性
    public void setupBlog(Blog blog, HttpSession session){
        blog.setUser((User) session.getAttribute("user"));
        //设置blog的type
        blog.setType(typeServiceImpl.getType(blog.getType().getId()));
        //设置blog的typeId属性
        blog.setTypeId(blog.getType().getId());
        //设置blog中的List<Tag>，即为博客添加标签
        blog.setTags(tagServiceImpl.listTagByIds(blog.getTagIds()));
        //设置博客的用户id
        blog.setUserId(blog.getUser().getId());
    }

    //新增博客
    @PostMapping("/blogs")
    public String insBlog(Blog blog, RedirectAttributes attributes, HttpSession session){
        //调用设置属性方法
        setupBlog(blog,session);
        //调用新增方法
        int result = blogServiceImpl.insBlog(blog);
        //插入博客对应的标签
        insBlogAndTags(blog);
        if(result > 0){
            attributes.addFlashAttribute("message","新增成功！");
        }else{
            attributes.addFlashAttribute("message","新增失败！");
        }
        return REDIRECT_LIST;
    }

    //往blog_tags表中插入blog对应的tagId方法
    public void insBlogAndTags(Blog blog){
        blogTags bts = new blogTags();
        for (int i = 0; i < blog.getTags().size(); i++) {
            bts.setBlogsId(blog.getId());
            bts.setTagsId(blog.getTags().get(i).getId());
            blogAndTagsServiceImpl.insBlogAndTags(bts);
        }
    }

    //修改博客
    @PostMapping("/blogs/{id}")
    public String updBlog(Blog blog,@PathVariable Long id,RedirectAttributes attributes, HttpSession session){
        //调用设置属性方法
        setupBlog(blog,session);
        //设置博客id
        blog.setId(id);
        int result = blogServiceImpl.updBlog(blog);
        //标签修改
        int blogTags = blogAndTagsServiceImpl.delBlogAndTagByblogId(blog.getId());
        if(blogTags > 0){
            insBlogAndTags(blog);
        }
        if(result > 0){
            attributes.addFlashAttribute("message","修改成功！");
        }else{
            attributes.addFlashAttribute("message","修改失败！");
        }
        return REDIRECT_LIST;
    }

    //删除博客
    @GetMapping("/blogs/{id}/delete")
    public String delBlog(@PathVariable Long id,RedirectAttributes attributes){
        int result = blogServiceImpl.delBlog(id);
        if(result > 0){
            attributes.addFlashAttribute("message","删除成功");
        }else{
            attributes.addFlashAttribute("message","删除成功");
        }
        return REDIRECT_LIST;
    }
}
