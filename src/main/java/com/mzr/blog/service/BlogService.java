package com.mzr.blog.service;

import com.mzr.blog.pojo.Blog;
import com.mzr.blog.vo.BlogQuery;
import com.mzr.blog.vo.TagLimit;
import com.mzr.blog.vo.TypeLimit;

import java.util.List;
import java.util.Map;

/**
 * @Author: Ryan
 * @Description:
 * @Date: Create in 15:35 2020/2/13
 */
public interface BlogService {

    Blog getBlog(Long id);

    Blog getAndConvertBlog(Long id);

    List<Blog> listBlog();

    int insBlog(Blog blog);

    int updBlog(Blog blog);

    int delBlog(Long id);

    List<Blog> searchBlog(BlogQuery blog);

    List<TypeLimit> BlogWithTypeTop(int num);

    List<TagLimit> BlogWithTagTop(int num);

    List<Blog> findBlogByRecommend(int num);

    List<Blog> fuzzyQueryBlogByTitleOrContent(String query);

    int updateViews(Long id);

    List<TypeLimit> listTypeByBlogNum();

    List<TagLimit> listTagByBlogNum();

    List<Blog> findBlogsByTagId(Long tagId);

    Map<String,List<Blog>> findBlogsByTime();

    Long findBlogNum();

    List<Blog> findBlogsByTypeId(Long typeId);
}
