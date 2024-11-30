package com.tyh.oj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tyh.oj.model.entity.Question;
import com.tyh.oj.service.QuestionService;
import com.tyh.oj.mapper.QuestionMapper;
import org.springframework.stereotype.Service;

/**
* @author 10047
* @description 针对表【question(题目)】的数据库操作Service实现
* @createDate 2024-11-30 16:04:46
*/
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question>
    implements QuestionService{

}




