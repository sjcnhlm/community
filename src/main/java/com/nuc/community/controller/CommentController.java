package com.nuc.community.controller;

import com.nuc.community.dto.CommentDTO;
import com.nuc.community.dto.ResultDTO;
import com.nuc.community.exception.CustomizeErrorCode;
import com.nuc.community.mapper.CommentMapper;
import com.nuc.community.model.Comment;
import com.nuc.community.model.User;
import com.nuc.community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class CommentController {

    @Autowired
    CommentMapper commentMapper;

    @Autowired
    CommentService commentService;

    @ResponseBody
    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    public Object post(@RequestBody CommentDTO commentDTO,
                       HttpServletRequest request)
    {
        User user = (User) request.getSession().getAttribute("user");
        if(user == null)
        {
            return ResultDTO.errorOf(CustomizeErrorCode.NOT_LOG_IN);
        }

        Comment comment =  new Comment();
        comment.setContent(commentDTO.getContent());
        comment.setParentId(commentDTO.getParentId());
        comment.setType(commentDTO.getType());
        comment.setCommentor(1);
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setLikeCount(0L);
        commentService.insert(comment);
        return ResultDTO.okOf();
    }

}
