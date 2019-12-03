package com.nuc.community.mapper;

import com.nuc.community.model.Question;

public interface QuestionExcMapper {
    void incViewCount(Question question);
}
