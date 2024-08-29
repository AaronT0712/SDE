package com.tyh.aaron.http;

import com.tyh.aaron.data.AsyncTaskRequest;
import com.tyh.aaron.data.AsyncTaskSetRequest;
import com.tyh.aaron.data.ReturnStatus;
import com.tyh.aaron.data.ScheduleConfig;



public interface FlowServer {
    ReturnStatus getTaskList(String taskType, int status, int limit);
    ReturnStatus createTask(AsyncTaskRequest asyncTaskRequest);
    ReturnStatus setTask(AsyncTaskSetRequest asyncTaskSetRequest);
    ReturnStatus getTask(String taskId);

    ReturnStatus getTaskTypeCfgList();
    ReturnStatus getUserTaskList(String user_id, int statusList);
    ReturnStatus createTaskCFG(ScheduleConfig scheduleConfig);

}
