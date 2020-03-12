package com.mzr.blog.service;

import com.mzr.blog.pojo.Blog;
import com.mzr.blog.pojo.Tag;
import com.mzr.blog.vo.TagLimit;

import java.util.List;

/**
 * @Author: Ryan
 * @Description:
 * @Date: Create in 17:23 2020/2/11
 */
public interface TagService {
    int saveTag(Tag tag);

    Tag getTag(Long id);

    List<Tag> listTag();

    List<Tag> listTagByIds(String ids);

    int updateTag(Long id, String name);

    void deleteTag(Long id);

    int checkTagName(String name);

    List<Tag> listTagByBlogId(Long id);

}
