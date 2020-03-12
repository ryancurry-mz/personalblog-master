package com.mzr.blog.service.impl;

import com.mzr.blog.dao.TagMapper;
import com.mzr.blog.pojo.Blog;
import com.mzr.blog.pojo.Tag;
import com.mzr.blog.service.TagService;
import com.mzr.blog.vo.TagLimit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Ryan
 * @Description:
 * @Date: Create in 17:25 2020/2/11
 */
@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagMapper tagMapper;

    @Transactional
    @Override
    public int saveTag(Tag tag) {
        return tagMapper.saveTag(tag);
    }

    @Override
    public Tag getTag(Long id) {
        return tagMapper.findTag(id);
    }

    @Override
    public List<Tag> listTag() {
        return tagMapper.listTag();
    }

    @Override
    public List<Tag> listTagByIds(String ids) {
        return tagMapper.listTagById(convertToList(ids));
    }

    //将传来的标签id字符串转为标签id集合
    private List<Long> convertToList(String ids){
        List<Long> list = new ArrayList<>();
        if(!"".equals(ids) && ids != null){
            String[] idArray =  ids.split(",");
            for (int i = 0; i < idArray.length; i++) {
                list.add(new Long(idArray[i]));
            }
        }
        return list;
    }

    @Transactional
    @Override
    public int updateTag(Long id, String name) {
        return tagMapper.updateTag(id, name);
    }

    @Transactional
    @Override
    public void deleteTag(Long id) {
        tagMapper.deleteTag(id);
    }

    @Transactional
    @Override
    public int checkTagName(String name) {
        return tagMapper.getTagByName(name);
    }

    @Override
    public List<Tag> listTagByBlogId(Long id) {
        return tagMapper.findTagById(id);
    }

}
