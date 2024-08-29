package com.tyh.flowsvr.service;

import com.tyh.flowsvr.data.ReturnStatus;
import com.tyh.flowsvr.data.ScheduleConfig;

public interface ScheduleConfigService {
    /**
     * 获取任务列表
     * @param <T>
     * @return
     */
    <T> ReturnStatus<T> getTaskTypeCfgList();

    /**
     * 新增任务配置项
     * @param scheduleConfig
     * @param <T>
     * @return
     */
    <T> ReturnStatus<T> save(ScheduleConfig scheduleConfig);
}
