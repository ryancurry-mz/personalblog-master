package com.mzr.blog.service.impl;

import com.mzr.blog.NotFoundException;
import com.mzr.blog.dao.BlogMapper;
import com.mzr.blog.pojo.Blog;
import com.mzr.blog.service.BlogService;
import com.mzr.blog.util.MarkdownUtils;
import com.mzr.blog.vo.BlogQuery;
import com.mzr.blog.vo.TagLimit;
import com.mzr.blog.vo.TypeLimit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @Author: Ryan
 * @Description:
 * @Date: Create in 15:47 2020/2/13
 */
@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogMapper blogMapper;

    @Override
    public Blog getBlog(Long id) {
        return blogMapper.getBlog(id);
    }

    @Override
    public Blog getAndConvertBlog(Long id) {
        Blog blog = blogMapper.getBlog(id);
        if(blog == null){
            throw new NotFoundException("该博客不存在！");
        }
        //增加浏览次数
        updateViews(id);
        String content = blog.getContent();
        blog.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
        return blog;
    }

    @Override
    public List<Blog> listBlog() {
        return blogMapper.listBlog();
    }

    @Transactional
    @Override
    public int insBlog(Blog blog) {
        //给新建的博客初始化时间和浏览次数
        blog.setCreateTime(new Date());
        blog.setUpdateTime(new Date());
        blog.setViews(0);
        return blogMapper.insBlog(blog);
    }

    @Transactional
    @Override
    public int updBlog(Blog blog) {
        blog.setUpdateTime(new Date());
        return blogMapper.updBlog(blog);
    }


    @Transactional
    @Override
    public int delBlog(Long id) {
        return blogMapper.delBlog(id);
    }

    @Override
    public List<Blog> searchBlog(BlogQuery blog) {
        return blogMapper.findBlogByTitleOrTypeOrRecommend(blog);
    }

    @Override
    public List<TypeLimit> BlogWithTypeTop(int num) {
        return blogMapper.BlogWithTypeTop(num);
    }

    @Override
    public List<TagLimit> BlogWithTagTop(int num) {
        return blogMapper.BlogWithTagTop(num);
    }

    @Override
    public List<Blog> findBlogByRecommend(int num) {
        return blogMapper.findBlogByRecommend(num);
    }

    @Override
    public List<Blog> fuzzyQueryBlogByTitleOrContent(String query) {
        return blogMapper.fuzzyQueryBlogByTitleOrContent(query);
    }

    @Transactional
    @Override
    public int updateViews(Long id) {
        return blogMapper.updateViews(id);
    }

    @Override
    public List<TypeLimit> listTypeByBlogNum() {
        return blogMapper.BlogWithType();
    }

    @Override
    public List<TagLimit> listTagByBlogNum() {
        return blogMapper.BlogWithTag();
    }

    @Override
    public List<Blog> findBlogsByTagId(Long tagId) {
        return blogMapper.listBlogByTagId(tagId);
    }

    @Override
    public Map<String, List<Blog>> findBlogsByTime() {
        List<String> listYears = blogMapper.findGroupYear();
        Map<String, List<Blog>> map = new LinkedHashMap<>();
        for (String year : listYears){
            map.put(year,blogMapper.findBlogByYear(year));
        }
        return map;
    }

    @Override
    public Long findBlogNum() {
        return blogMapper.blogNum();
    }

    @Override
    public List<Blog> findBlogsByTypeId(Long typeId) {
        return blogMapper.listBlogByTypeId(typeId);
    }

}
