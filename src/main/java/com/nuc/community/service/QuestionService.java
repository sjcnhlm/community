package com.nuc.community.service;

import com.nuc.community.dto.PageDto;
import com.nuc.community.dto.QuestionDto;
import com.nuc.community.exception.CustomizeErrorCode;
import com.nuc.community.exception.CustomizeException;
import com.nuc.community.mapper.QuestionExcMapper;
import com.nuc.community.mapper.QuestionMapper;
import com.nuc.community.mapper.UserMapper;
import com.nuc.community.model.Question;
import com.nuc.community.model.QuestionExample;
import com.nuc.community.model.User;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.ast.QualifiedIdentifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    UserMapper userMapper;

    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    QuestionExcMapper questionExcMapper;

    /**
     * 将Question类中的各个实体传递给QuestionDao的对象，并且将User也封装到QuestionDao对象中。
     * @return QuestionDto
     * @param page
     * @param size
     */
    public PageDto list(Integer page, Integer size) {
        Integer offset = size*(page-1);
        Integer totalPage;

        List<Question> questions = questionMapper.selectByExampleWithRowbounds(new QuestionExample(), new RowBounds(offset, size));
        List<QuestionDto> questionDtoList = new ArrayList<>();
        //List<PageDto> pageDtos  = new ArrayList<>();

        PageDto pageDto = new PageDto();

        Integer totalCount = (int) questionMapper.countByExample(new QuestionExample());
        //Integer totalCount = questionMapper.count();
       // pageDto.setPage(totalCount,page,size);

        if(totalCount % size == 0)
        {
            totalPage = totalCount/size;
        }else{
            totalPage = totalCount / size +1;
        }

        if(page < 1)
        {
            page = 1;
        }
        if(page > totalPage)
        {
            page = totalPage;
        }
        pageDto.setPage(totalPage,page);

        //循环遍历，将question对象的属性复制给questionDto,并将关联的user对象添加到questionDto。
        for(Question question: questions)
        {
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDto questionDto = new QuestionDto();
            questionDto.setUser(user);
            BeanUtils.copyProperties(question,questionDto);
            questionDtoList.add(questionDto);

        }
        pageDto.setQuestionDtos(questionDtoList);

       // pageDtos.add(pageDto);
        return pageDto;
    }

    public PageDto list(int userId, Integer page, Integer size) {

        Integer offset = size*(page-1);

        Integer totalCount = (int) questionMapper.countByExample(new QuestionExample());

        QuestionExample questionExample = new QuestionExample();
         questionExample.createCriteria().andCreatorEqualTo(userId);
        List<Question> questions = questionMapper.selectByExampleWithRowbounds(questionExample, new RowBounds(offset, size));
        List<QuestionDto> questionDtoList = new ArrayList<>();
        //List<PageDto> pageDtos  = new ArrayList<>();

        PageDto pageDto = new PageDto();

        Integer totalPage;


        if(totalCount % size == 0)
        {
            totalPage = totalCount/size;
        }else{
            totalPage = totalCount / size +1;
        }

        if(page < 1)
        {
            page = 1;
        }
        if(page > totalPage)
        {
            page = totalPage;
        }
        pageDto.setPage(totalPage,page);



        //循环遍历，将question对象的属性复制给questionDto,并将关联的user对象添加到questionDto。
        for(Question question: questions)
        {
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDto questionDto = new QuestionDto();
            questionDto.setUser(user);
            BeanUtils.copyProperties(question,questionDto);
            questionDtoList.add(questionDto);

        }
        pageDto.setQuestionDtos(questionDtoList);
        return pageDto;
    }

    public QuestionDto getById(Integer id) {

        Question question = questionMapper.selectByPrimaryKey(id);
        if(question == null)
        {
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        QuestionDto questionDto = new QuestionDto();
        BeanUtils.copyProperties(question,questionDto);
        User user = userMapper.selectByPrimaryKey(question.getCreator());
        questionDto.setUser(user);
        return questionDto;
    }

    public void careateOrUpdate(Question question) {

        if(question.getId() == null)
        {
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());

            questionMapper.insert(question);
        }else{

            Question updateQuestion = new Question();
            updateQuestion.setGmtModified(System.currentTimeMillis());
            updateQuestion.setTag(question.getTag());
            updateQuestion.setTitle(question.getTitle());
            updateQuestion.setDescription(question.getDescription());
            QuestionExample questionExample = new QuestionExample();
            questionExample.createCriteria().andIdEqualTo(question.getId());
            int updated = questionMapper.updateByExampleSelective(updateQuestion,questionExample);
            if(updated != 1)
            {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
        }
    }

    public void incViewCount(Integer id) {

        Question question = new Question();
        question.setId(id);
        question.setViewCount(1);
        questionExcMapper.incViewCount(question);

    }
}
