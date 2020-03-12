package com.mzr.blog.service;

import com.mzr.blog.pojo.blogTags;

/**
 * @Author: Ryan
 * @Description:
 * @Date: Create in 0:02 2020/2/19
 */
public interface BlogAndTagsService {
    int insBlogAndTags(blogTags bts);

    int delBlogAndTagByblogId(Long blogId);
}
