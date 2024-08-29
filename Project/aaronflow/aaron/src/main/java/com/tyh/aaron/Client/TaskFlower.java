package com.tyh.aaron.Client;

import com.tyh.aaron.data.AsyncTaskRequest;
import com.tyh.aaron.data.AsyncTaskReturn;
import com.tyh.aaron.data.AsyncTaskSetRequest;
import com.tyh.aaron.data.ScheduleConfig;
import com.tyh.aaron.enums.TaskStatus;

import java.util.List;

public interface TaskFlower {
    public String createTask(AsyncTaskRequest asyncTaskRequest);
    public void setTask(AsyncTaskSetRequest asyncTaskSetRequest);
    public AsyncTaskReturn getTask(String taskId);
    public List<AsyncTaskReturn> getTaskList(Class<?> clazz, int status, int limit);
    public List<ScheduleConfig> getTaskTypeCfgList();
    public List<AsyncTaskReturn> getUserTaskList(List<TaskStatus> taskStatuses);
    public void createTaskCFG(ScheduleConfig scheduleConfig);


}
