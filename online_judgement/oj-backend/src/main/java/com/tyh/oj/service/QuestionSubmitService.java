package com.tyh.oj.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tyh.oj.model.dto.question.QuestionQueryRequest;
import com.tyh.oj.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.tyh.oj.model.dto.questionsubmit.QuestionSubmitQueryRequest;
import com.tyh.oj.model.entity.Question;
import com.tyh.oj.model.entity.QuestionSubmit;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tyh.oj.model.entity.User;
import com.tyh.oj.model.vo.QuestionSubmitVO;
import com.tyh.oj.model.vo.QuestionVO;

import javax.servlet.http.HttpServletRequest;

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

    /**
     * 获取查询条件
     *
     * @param questionSubmitQueryRequest
     * @return
     */
    QueryWrapper<QuestionSubmit> getQueryWrapper(QuestionSubmitQueryRequest questionSubmitQueryRequest);

    /**
     * 获取题目封装
     *
     * @param questionSubmit
     * @param loginUser
     * @return
     */
    QuestionSubmitVO getQuestionSubmitVO(QuestionSubmit questionSubmit, User loginUser);

    /**
     * 分页获取题目封装
     *
     * @param questionPage
     * @param loginUser
     * @return
     */
    Page<QuestionSubmitVO> getQuestionSubmitVOPage(Page<QuestionSubmit> questionPage, User loginUser);
}
