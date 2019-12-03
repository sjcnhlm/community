package com.nuc.community.dto;

import com.nuc.community.model.User;
import lombok.Data;

/**
 * QuestionDto属性和Question基本相同，多了一个User对象的属性。
 * model中的实体类是和数据库中的表一一对应的，
 * dto中的实体类对model中的数据进行加工。
 */
@Data
public class QuestionDto {
    private Integer id;
    private String title;
    private String description;
    private String tag;
    private Integer creator;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer viewCount;
    private Integer commentCount;
    private Integer likeCount;

    private User user;
}
