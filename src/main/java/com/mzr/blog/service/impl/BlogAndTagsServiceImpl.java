package com.mzr.blog.service.impl;

import com.mzr.blog.dao.blogTagsMapper;
import com.mzr.blog.pojo.blogTags;
import com.mzr.blog.service.BlogAndTagsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: Ryan
 * @Description:
 * @Date: Create in 0:03 2020/2/19
 */
@Service
public class BlogAndTagsServiceImpl implements BlogAndTagsService {

    @Autowired
    private blogTagsMapper blogTagsMapper;

    @Transactional
    @Override
    //新增博客同时往t_blogs_tags表新增对应的tagid
    public int insBlogAndTags(blogTags bts) {
        return blogTagsMapper.insBlogAndTag(bts);
    }

    @Transactional
    @Override
    public int delBlogAndTagByblogId(Long blogId) {
        return blogTagsMapper.delBlogAndTagByblogId(blogId);
    }
}
