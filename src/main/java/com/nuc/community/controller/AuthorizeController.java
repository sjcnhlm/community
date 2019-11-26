package com.nuc.community.controller;

import com.nuc.community.dto.GithubDTO;
import com.nuc.community.dto.GithubUser;
import com.nuc.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {

    @Autowired
    GithubProvider githubProvider;

    @Value("${github.client.id}")
    private String client_id;
    @Value("${github.client.secret}")
    private String client_secret;
    @Value("${github.redirect.uri}")
    private String redirect_uri;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state)
    {
        GithubDTO githubDTO = new GithubDTO();
        githubDTO.setClient_id(client_id);
        githubDTO.setClient_secret(client_secret);
        githubDTO.setRedirect_uri(redirect_uri);
        githubDTO.setCode(code);
        githubDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(githubDTO);

        GithubUser githubUser = githubProvider.getUser(accessToken);
        System.out.println(githubUser.getName());
        return "index";
    }
}
