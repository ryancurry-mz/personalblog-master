package com.mzr.blog.dao;

import com.mzr.blog.pojo.Blog;
import com.mzr.blog.vo.BlogQuery;
import com.mzr.blog.vo.TagLimit;
import com.mzr.blog.vo.TypeLimit;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface BlogMapper {
    //根据Id查询博客
    Blog getBlog(Long id);

    //查询博客列表
    List<Blog> listBlog();

    //新增博客
    @Insert("insert into t_blog(appreciation,commendable,content,create_time,description," +
            "first_picture,flag,published,recommend,share_statement,title,update_time," +
            "views,type_id,user_id,tag_ids) " +
            "values(#{appreciation},#{commendable}," +
            "#{content},#{createTime},#{description},#{firstPicture},#{flag}," +
            "#{published},#{recommend},#{shareStatement},#{title},#{updateTime}," +
            "#{views},#{typeId},#{userId},#{tagIds})")
    @Options(useGeneratedKeys=true,keyProperty="id")
    int insBlog(Blog blog);

    //根据Id删除博客
    @Delete("delete from t_blog where id = #{id}")
    int delBlog(Long id);

    //根据id修改博客
    @Update("update t_blog set appreciation = #{appreciation},commendable=#{commendable}," +
            "content=#{content},description=#{description}," +
            "first_picture=#{firstPicture},flag=#{flag},published=#{published}," +
            "recommend=#{recommend},share_statement=#{shareStatement},title=#{title}," +
            "update_time=#{updateTime},type_id=#{typeId},user_id=#{userId}," +
            "tag_ids=#{tagIds} where id=#{id}")
    int updBlog(Blog blog);

    //根据标题、分类、是否推荐 进行动态查询
    List<Blog> findBlogByTitleOrTypeOrRecommend(BlogQuery blog);

    //查询博客分类最多
    @Select("select t.id,t.`name`,IFNULL(typeCount,0) as count from t_type as t left join" +
            "(select type_id as typeId,count(*) as typeCount from t_blog group by type_id ORDER BY typeCount DESC)" +
            "as b on t.id=b.typeId LIMIT 0,#{0}")
    List<TypeLimit> BlogWithTypeTop(int num);

    //查询博客标签最多
    @Select("select tt.id,tt.`name`,IFNULL(tagCount,0) as count from t_tag as tt left join" +
            "(select tags_id,count(*) as tagCount from t_blog_tags GROUP BY tags_id)" +
            "as tbt on tbt.tags_id = tt.id ORDER BY tagCount DESC LIMIT 0,#{0}")
    List<TagLimit> BlogWithTagTop(int num);

    //查询推荐的博客
    @Select("select * from t_blog where recommend = true order by update_time desc LIMIT 0,#{num}")
    List<Blog> findBlogByRecommend(int num);

    //根据博客标题或内容进行模糊查询
    List<Blog> fuzzyQueryBlogByTitleOrContent(String query);

    //修改博客浏览次数
    @Update("update t_blog set views = views + 1 where id = #{id}")
    int updateViews(Long id);

    //查询分类对应的博客数量
    @Select("select t.id,t.`name`,IFNULL(typeCount,0) as count from t_type as t left join" +
            "(select type_id as typeId,count(*) as typeCount from t_blog group by type_id ORDER BY typeCount DESC)" +
            "as b on t.id=b.typeId")
    List<TypeLimit> BlogWithType();

    //查询标签对应的博客数量
    @Select("select tt.id,tt.`name`,IFNULL(tagCount,0) as count from t_tag as tt left join" +
            "(select tags_id,count(*) as tagCount from t_blog_tags GROUP BY tags_id)" +
            "as tbt on tbt.tags_id = tt.id ORDER BY tagCount DESC")
    List<TagLimit> BlogWithTag();

    //根据标签id查询博客
    List<Blog> listBlogByTagId(@Param("tagId") Long tagId);

    //查询博客年份组
    @Select("select DATE_FORMAT(tb.update_time,'%Y') as `year` from t_blog tb GROUP BY `year` ORDER BY `year` DESC")
    List<String> findGroupYear();

    //根据博客年份查询博客组
    @Select("select * from t_blog tb where DATE_FORMAT(tb.update_time,'%Y') = #{year}")
    List<Blog> findBlogByYear(String year);

    //查询博客数量
    @Select("select COUNT(*) from t_blog")
    Long blogNum();

    //根据类别查询博客
    List<Blog> listBlogByTypeId(Long typeId);
}