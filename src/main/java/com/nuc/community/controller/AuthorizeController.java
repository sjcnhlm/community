package com.nuc.community.controller;

import com.nuc.community.dto.GithubDTO;
import com.nuc.community.dto.GithubUser;
import com.nuc.community.mapper.UserMapper;
import com.nuc.community.model.User;
import com.nuc.community.provider.GithubProvider;
import com.nuc.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {

    @Autowired
    GithubProvider githubProvider;

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserService userService;

    @Value("${github.client.id}")
    private String client_id;
    @Value("${github.client.secret}")
    private String client_secret;
    @Value("${github.redirect.uri}")
    private String redirect_uri;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletRequest request,
                           HttpServletResponse response)
    {

        GithubDTO githubDTO = new GithubDTO();
        githubDTO.setClient_id(client_id);
        githubDTO.setClient_secret(client_secret);
        githubDTO.setRedirect_uri(redirect_uri);
        githubDTO.setCode(code);
        githubDTO.setState(state);
        //获取accessToken
        String accessToken = githubProvider.getAccessToken(githubDTO);
        //根据accessToken获取到GithubUser
        GithubUser githubUser = githubProvider.getUser(accessToken);

        if(githubUser != null)
        {
            User user  = new User();
            String token = UUID.randomUUID().toString();
            user.setAccountId(githubUser.getId());
            user.setName(githubUser.getName());
            user.setToken(token);
            user.setAvatarUrl(githubUser.getAvatarUrl());
            //将用户信息插入到数据库中。

            userService.createOrUpdate(user);

            //将token添加到cookie中。
            response.addCookie(new Cookie("token",token));


            //request.getSession().setAttribute("user",githubUser);
            return "redirect:/index";
        }else{
            return "redirect:/index";
        }
    }

    @GetMapping("/logout")
    public String logOut(HttpServletRequest request,HttpServletResponse response)
    {
        request.getSession().removeAttribute("user");

        //删除之前的cookie
        Cookie cookie = new Cookie("token",null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/index";
    }
}
