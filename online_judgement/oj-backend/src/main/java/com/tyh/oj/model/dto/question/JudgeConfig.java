package com.tyh.oj.model.dto.question;

import lombok.Data;

@Data
public class JudgeConfig {
    /**
     * 时间限制 (ms)
     */
    private long timeLimit;

    /**
     * 堆栈限制 (kB)
     */
    private long stackLimit;

    /**
     * 空间限制
     */
    private long spaceLimit;
}
