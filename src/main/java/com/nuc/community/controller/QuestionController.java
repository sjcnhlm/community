package com.nuc.community.controller;

import com.nuc.community.dto.QuestionDto;
import com.nuc.community.mapper.QuestionMapper;
import com.nuc.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Integer id,
                           Model model)
    {
        QuestionDto questionDto = questionService.getById(id);

        questionService.incViewCount(id);
        model.addAttribute("question",questionDto);
        return "question";
    }

}
