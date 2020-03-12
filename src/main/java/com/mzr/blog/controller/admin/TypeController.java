package com.mzr.blog.controller.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mzr.blog.pojo.Type;
import com.mzr.blog.service.TypeService;
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
 * @Date: Create in 17:27 2020/2/10
 */
@Controller
@RequestMapping("/admin")
public class TypeController {

    @Autowired
    private TypeService typeServiceImpl;

    /**
     * 查询所有博客类别
     * @return
     */
    @GetMapping("/types")
    public String types(@RequestParam(value="pageNum",defaultValue="1")Integer pageNum,
                        Model model){
        //引入分页插件
        //传入页码，每页显示的条数
        PageHelper.startPage(pageNum, 5);
        List<Type> types = typeServiceImpl.listType();
        //使用pageInfo包装查询后的结果
        //封装了详细的分页信息，包括查出来的数据以及传入连续显示的页数
        PageInfo pageInfo  = new PageInfo(types,5);
        model.addAttribute("types",pageInfo);
        return "admin/types";
    }

    //进入新增类别页面
    @GetMapping("/types/input")
    public String input(Model model){
        model.addAttribute("type",new Type());
        return "admin/types-input";
    }

    //新增类别功能实现
    @PostMapping("/types")
    public String insType(@Valid Type type, BindingResult result, RedirectAttributes attributes){
        //校验类别名称是否重复
        int checkName = typeServiceImpl.checkName(type.getName());
        if(checkName > 0){
            result.rejectValue("name","nameError","该类别已存在！！");
            return "admin/types-input";
        }

        //后端校验
        if(result.hasErrors()){
            return "admin/types-input";
        }

        int t = typeServiceImpl.saveType(type);
        if(t > 0){
            attributes.addFlashAttribute("message","新增成功！");
        }else{
            attributes.addFlashAttribute("message","新增失败！");
        }
        return "redirect:/admin/types";
    }


    //进入修改页面
    @GetMapping("/types/{id}/update")
    public String updPage(@PathVariable Long id, Model model){
        Type type = typeServiceImpl.getType(id);
        model.addAttribute("type",type);
        return "admin/types-update";
    }

    //修改类别功能实现
    @PostMapping("/types/{id}")
    public String insType(@Valid Type type, BindingResult result,@PathVariable Long id, RedirectAttributes attributes){
        //校验类别名称是否重复
        int checkName = typeServiceImpl.checkName(type.getName());
        if(checkName > 0){
            result.rejectValue("name","nameError","该类别已存在！！");
            return "admin/types-input";
        }

        //后端校验
        if(result.hasErrors()){
            return "admin/types-input";
        }

        int t = typeServiceImpl.updateType(id,type.getName());
        if(t > 0){
            attributes.addFlashAttribute("message","修改成功！");
        }else{
            attributes.addFlashAttribute("message","修改失败！");
        }
        return "redirect:/admin/types";
    }

    //删除类别
    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){
        typeServiceImpl.deleteType(id);
        attributes.addFlashAttribute("message","删除成功！");
        return "redirect:/admin/types";
    }

}
