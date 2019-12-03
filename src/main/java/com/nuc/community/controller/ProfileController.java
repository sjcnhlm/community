package com.nuc.community.controller;

import com.nuc.community.dto.PageDto;
import com.nuc.community.mapper.QuestionMapper;
import com.nuc.community.mapper.UserMapper;
import com.nuc.community.model.User;
import com.nuc.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {

    @Autowired
    UserMapper userMapper;

    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    QuestionService questionService;

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action") String action,
                          Model model,
                          HttpServletRequest request,
                          @RequestParam(name = "page",defaultValue = "1") Integer page,
                          @RequestParam(name = "size",defaultValue = "2") Integer size)
    {

        User user = (User) request.getSession().getAttribute("user");

        if(user == null)
        {
            return "redirect:/index";
        }
        if("questions".equals(action))
        {
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","我的提问");
        }else if("replies".equals(action)){
            model.addAttribute("section","replies");
            model.addAttribute("sectionName","最新回复");

        }

        PageDto pageDto = questionService.list(user.getId(),page,size);

        model.addAttribute("pageDto",pageDto);
        return "profile";
    }

}
