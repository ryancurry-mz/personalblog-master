package com.mzr.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Author: Ryan
 * @Description:
 * @Date: Create in 0:34 2020/2/28
 */
@Controller
public class AboutController {

    @GetMapping("/about")
    public String aboutPageShow(){
        return "about";
    }
}
