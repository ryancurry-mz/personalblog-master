package com.mzr.blog.controller.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mzr.blog.pojo.Tag;
import com.mzr.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

/**
 * @Author: Ryan
 * @Description:
 * @Date: Create in 17:28 2020/2/11
 */
@Controller
@RequestMapping("/admin")
public class TagController {

    @Autowired
    private TagService tagServiceImpl;

    /**
     * 查询所有博客标签
     * @return
     */
    @GetMapping("/tags")
    public String tags(@RequestParam(value="pageNum",defaultValue="1")Integer pageNum,
                        Model model){
        //引入分页插件
        //传入页码，每页显示的条数
        PageHelper.startPage(pageNum, 5);
        List<Tag> tags = tagServiceImpl.listTag();
        //使用pageInfo包装查询后的结果
        //封装了详细的分页信息，包括查出来的数据以及传入连续显示的页数
        PageInfo pageInfo  = new PageInfo(tags,5);
        model.addAttribute("tags",pageInfo);
        return "admin/tags";
    }

    //进入新增标签页面
    @GetMapping("/tags/input")
    public String input(Model model){
        model.addAttribute("tag",new Tag());
        return "admin/tags-input";
    }

    //校验标签名称是否重复方法
    public boolean checkTagName(Tag tag, BindingResult result){
        int checkName = tagServiceImpl.checkTagName(tag.getName());
        if(checkName > 0){
            result.rejectValue("name","nameError","该标签已存在！！");
            return false;
        }else{
            return true;
        }
    }

    //后端校验
    public boolean backendCheck(BindingResult result){
        if(result.hasErrors()){
            return false;
        }else{
            return true;
        }
    }

    //新增标签功能实现
    @PostMapping("/tags")
    public String insTag(@Valid Tag tag, BindingResult result, RedirectAttributes attributes){
        //调用校验类别名方法
        if(!checkTagName(tag, result)){
           return "admin/tags-input";
        }
        //调用后端校验方法
        if(!backendCheck(result)){
            return "admin/tags-input";
        }
        int t = tagServiceImpl.saveTag(tag);
        if(t > 0){
            attributes.addFlashAttribute("message","新增成功！");
        }else{
            attributes.addFlashAttribute("message","新增失败！");
        }
        return "redirect:/admin/tags";
    }


    //进入修改页面
    @GetMapping("/tags/{id}/update")
    public String updPage(@PathVariable Long id, Model model){
        Tag tag = tagServiceImpl.getTag(id);
        model.addAttribute("tag",tag);
        return "admin/tags-update";
    }

    //修改标签功能实现
    @PostMapping("/tags/{id}")
    public String updTag(@Valid Tag tag, BindingResult result,@PathVariable Long id, RedirectAttributes attributes){
        //调用校验类别名方法
        if(!checkTagName(tag, result)){
            return "admin/tags-input";
        }
        //调用后端校验方法
        if(!backendCheck(result)){
            return "admin/tags-input";
        }
        int t = tagServiceImpl.updateTag(id,tag.getName());
        if(t > 0){
            attributes.addFlashAttribute("message","修改成功！");
        }else{
            attributes.addFlashAttribute("message","修改失败！");
        }
        return "redirect:/admin/tags";
    }

    //删除标签
    @GetMapping("/tags/{id}/{pageNum}/delete")
    public String delete(@PathVariable Long id,
                         @PathVariable int pageNum,
                         RedirectAttributes attributes){
        tagServiceImpl.deleteTag(id);
        attributes.addFlashAttribute("message","删除成功！");
        return "redirect:/admin/tags?pageNum=" + pageNum;
    }

}
