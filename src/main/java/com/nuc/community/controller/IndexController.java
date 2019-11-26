package com.nuc.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {

    @GetMapping("/index")
    public String hello(String name)
    {
//        model.addAttribute("name",name);
        return "index";

    }
}
