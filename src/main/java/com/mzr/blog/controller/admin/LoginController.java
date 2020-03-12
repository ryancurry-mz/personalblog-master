package com.mzr.blog.controller.admin;

import com.mzr.blog.pojo.User;
import com.mzr.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * @Author: Ryan
 * @Description:
 * @Date: Create in 22:23 2020/2/9
 */
@Controller
@RequestMapping("/admin")
public class LoginController {

    @Autowired
    private UserService userServiceImpl;

    @GetMapping
    public String loginPage(){
        return "admin/login";
    }


    @PostMapping("/login")
    //判断登录的用户名和密码是否正确，若正确，进入主页面；若错误，跳转到登录页面
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes attributes){
        System.out.println("登录用户:" + username);
        User user = userServiceImpl.checkUser(username,password);
        if(user != null){
            user.setPassword(null);
            session.setAttribute("user",user);
            return "admin/index";
        }else{
            attributes.addFlashAttribute("message","用户名或密码有误！");
            return "redirect:/admin";
        }
    }

    @GetMapping("/logout")
    //退出登录
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/admin";
    }
}
