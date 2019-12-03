package com.nuc.community.controller;

import com.nuc.community.dto.QuestionDto;
import com.nuc.community.mapper.QuestionMapper;
import com.nuc.community.mapper.UserMapper;
import com.nuc.community.model.Question;
import com.nuc.community.model.User;
import com.nuc.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Autowired
    UserMapper userMapper;



    @Autowired
    QuestionService questionService;

    @GetMapping("/publish")
    public String publish()
    {
        return "publish";
    }

    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name = "id") Integer id,Model model)
    {
        QuestionDto question = questionService.getById(id);
        model.addAttribute("title",question.getTitle());
        model.addAttribute("tag",question.getTag());
        model.addAttribute("description",question.getDescription());
        model.addAttribute("id",question.getId());
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(@RequestParam String title,
                            @RequestParam String description,
                            @RequestParam String tag,
                            @RequestParam Integer id,
                            HttpServletRequest request,
                            Model model)
    {
        //将title，tag，description添加到页面中，为了回显。
        model.addAttribute("title",title);
        model.addAttribute("tag",tag);
        model.addAttribute("description",description);

        if(title == null || title.equals(""))
        {
            model.addAttribute("error","标题不能为空");
            return "publish";
        }
        if(tag == null || tag.equals(""))
        {
            model.addAttribute("error","标签不能为空");
            return "publish";
        }
        if(description == null || description.equals(""))
        {
            model.addAttribute("error","问题补充不能为空");
            return "publish";
        }

        User user = (User) request.getSession().getAttribute("user");


        if(user == null)
        {
            model.addAttribute("error","用户未登录");
            return "publish";
        }

        Question question = new Question();
        question.setTitle(title);
        question.setTag(tag);
        question.setDescription(description);

        question.setCreator(user.getId());
        question.setId(id);

        questionService.careateOrUpdate(question);


        return "redirect:/index";
    }
}
