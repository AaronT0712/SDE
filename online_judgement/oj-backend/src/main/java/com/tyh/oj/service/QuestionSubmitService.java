package com.tyh.oj.service;

import com.tyh.oj.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.tyh.oj.model.entity.QuestionSubmit;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tyh.oj.model.entity.User;

/**
* @author 10047
* @description 针对表【question_submit(题目提交)】的数据库操作Service
* @createDate 2024-11-30 16:05:39
*/
public interface QuestionSubmitService extends IService<QuestionSubmit> {
    /**
     * 提交题目
     *
     * @param questionSubmitAddRequest 题目提交信息
     * @param loginUser
     * @return
     */
    long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser);
}
