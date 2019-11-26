package com.nuc.community.provider;



import com.alibaba.fastjson.JSON;
import com.nuc.community.dto.GithubDTO;
import com.nuc.community.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GithubProvider {

    public String getAccessToken(GithubDTO githubDTO)
    {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(githubDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {

            String string1=response.body().string();
            //System.out.println(string1);
            String accessToken = string1.split("&")[0].split("=")[1];
            return accessToken;
        }catch (IOException io)
        {
        }
        return null;
    }

    public GithubUser getUser(String accessToken)
    {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(" https://api.github.com/user?access_token="+accessToken)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            GithubUser githubUser=JSON.parseObject(string,GithubUser.class);

            return githubUser;
        }catch (IOException io)
        {
        }
        return null;
    }



}
