package com.nuc.community.controller;

import com.nuc.community.dto.PageDto;
import com.nuc.community.mapper.QuestionMapper;
import com.nuc.community.mapper.UserMapper;
import com.nuc.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

    @Autowired
    UserMapper userMapper;

    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    QuestionService questionService;
    @GetMapping("/index")
    public String hello(HttpServletRequest request,Model model,
                        @RequestParam(name = "page",defaultValue = "1") Integer page,
                        @RequestParam(name = "size",defaultValue = "2") Integer size)
    {


        PageDto pageDto = questionService.list(page, size);
        model.addAttribute("pageDto", pageDto);
        return "index";


    }
}
