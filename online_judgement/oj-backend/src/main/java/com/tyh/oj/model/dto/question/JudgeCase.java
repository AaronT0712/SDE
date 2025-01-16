package com.tyh.oj.model.dto.question;

import lombok.Data;

@Data
public class JudgeCase {
    /**
     * 题目输入
     */
    private String input;

    /**
     * 题目输出
     */
    private String output;
}
