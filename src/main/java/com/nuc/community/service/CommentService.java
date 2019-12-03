package com.nuc.community.service;

import com.nuc.community.exception.CustomizeErrorCode;
import com.nuc.community.exception.CustomizeException;
import com.nuc.community.model.Comment;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    public void insert(Comment comment) {
        if (comment.getParentId() == null && comment.getParentId() == 0) {
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }

    }
}
