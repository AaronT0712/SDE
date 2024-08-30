package com.tyh.flowsvr.service.impl;

import com.tyh.flowsvr.constant.ErrorStatusReturn;
import com.tyh.flowsvr.constant.Task;
import com.tyh.flowsvr.dao.AaronFlowTaskDao;
import com.tyh.flowsvr.dao.ScheduleConfigDao;
import com.tyh.flowsvr.dao.TSchedulePosDao;
import com.tyh.flowsvr.data.*;
import com.tyh.flowsvr.enums.ErrorStatus;
import com.tyh.flowsvr.enums.TaskStatus;
import com.tyh.flowsvr.service.AsyncTaskService;
import com.tyh.flowsvr.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author zhangdafeng
 */
@Service
public class AsyncTaskServiceImpl implements AsyncTaskService {
    Logger logger = LoggerFactory.getLogger(AsyncTaskServiceImpl.class);

    @Autowired
    private AaronFlowTaskDao aaronFlowTaskDao;

    @Autowired
    private ScheduleConfigDao scheduleConfigDao;

    @Autowired
    private TSchedulePosDao tSchedulePosDao;


    private AaronFlowClientData getAsyncFlowClientData(AsyncTaskRequest asyncTaskGroup) {
        AaronFlowClientData aaronFlowClientData = asyncTaskGroup.getTaskData();
        return aaronFlowClientData;
    }

    @Override
    public <T> ReturnStatus<T> createTask(AsyncTaskRequest asyncTaskRequest) {
        AaronFlowClientData aaronFlowClientData = getAsyncFlowClientData(asyncTaskRequest);
        TSchedulePos taskPos = null;
        try {
            taskPos = tSchedulePosDao.getTaskPos(aaronFlowClientData.getTask_type());
        } catch (Exception e) {
            return ErrorStatusReturn.ERR_GET_TASK_POS;
        }
        if (taskPos == null) {
            logger.error("db.TaskPosNsp.GetTaskPos failed.");
        }
        String tableName = getTableName(taskPos.getScheduleEndPos(), aaronFlowClientData.getTask_type());

        ScheduleConfig taskTypeCfg;
        try {
            taskTypeCfg = scheduleConfigDao.getTaskTypeCfg(aaronFlowClientData.getTask_type());
        } catch (Exception e) {
            logger.error("Visit t_task_type_cfg error");
            return ErrorStatusReturn.ERR_GET_TASK_SET_POS_FROM_DB;
        }

        AsyncTask asyncTask = new AsyncTask();
        String taskId = getTaskId(aaronFlowClientData.getTask_type(), taskPos.getScheduleEndPos(), tableName);
        try {
            fillTaskModel(aaronFlowClientData, asyncTask, taskId, taskTypeCfg);
            aaronFlowTaskDao.create(tableName, asyncTask);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("create task error");
            return ErrorStatusReturn.ERR_CREATE_TASK;

        }
        TaskResult taskResult = new TaskResult(taskId);
        return new ReturnStatus(taskResult);
    }

    private String getTaskId(String taskType, int taskPos, String tableName) {
        return Utils.getTaskId() + "_" + taskType + "_" + tableName() + "_" + taskPos;
    }

    public void fillTaskModel (AaronFlowClientData aaronFlowClientData, AsyncTask asyncTask, String taskId, ScheduleConfig taskTypeCfg) {
        asyncTask.setTask_id(taskId);
        asyncTask.setUser_id(aaronFlowClientData.getUser_id());
        asyncTask.setTask_type(aaronFlowClientData.getTask_type());
        asyncTask.setTask_stage(aaronFlowClientData.getTask_stage());
        Long currentTime = System.currentTimeMillis();
        asyncTask.setModify_time(currentTime);
        asyncTask.setMax_retry_interval(taskTypeCfg.getRetry_interval());
        asyncTask.setMax_retry_num(taskTypeCfg.getMax_retry_num());
        asyncTask.setCrt_retry_num(0);
        asyncTask.setOrder_time(currentTime);
        asyncTask.setCreate_time(currentTime);
        asyncTask.setStatus(TaskStatus.PENDING.getStatus());
        asyncTask.setSchedule_log(aaronFlowClientData.getSchedule_log());
        asyncTask.setTask_context(aaronFlowClientData.getTask_context());
    }

    @Override
    public <T> ReturnStatus<T> holdTask(String taskType, int status, int limit) {
        // 不能超过最大数
        if (limit > Task.MAX_TASK_LIST_LIMIT) {
            limit = Task.MAX_TASK_LIST_LIMIT;
        }
        if (limit == 0) {
            limit = Task.DEFAULT_TASK_LIST_LIMIT;
        }
        TSchedulePos taskPos;
        try {
            taskPos = tSchedulePosDao.getTaskPos(taskType);
        } catch (Exception e) {
            e.printStackTrace();
            return ErrorStatusReturn.ERR_GET_TASK_SET_POS_FROM_DB;
        }
        String tableName = getTableName(taskPos.getScheduleBeginPos(), taskType);
        List<AsyncTask> taskList;
        try {
            taskList = aaronFlowTaskDao.getTaskList(taskType, status, limit, tableName);

        } catch (Exception e) {
            logger.error(ErrorStatus.ERR_GET_TASK_LIST_FROM_DB.getMsg());
            return ErrorStatusReturn.ERR_GET_TASK_LIST_FROM_DB;
        }
        List<AsyncTask> filterList = taskList
                .stream()
                .parallel()
                .filter(asyncTask -> asyncTask.getCrt_retry_num() == 0 || asyncTask.getMax_retry_interval() != 0
                        && asyncTask.getOrder_time() <= System.currentTimeMillis()).collect(Collectors.toList());
        List<String> ids = conventTaskIdList(filterList);
        if (!ids.isEmpty()) {
            aaronFlowTaskDao.updateStatusBatch(ids, TaskStatus.EXECUTING.getStatus(), System.currentTimeMillis(), tableName);
        }
        List<AsyncTaskReturn> taskReturns = getTaskReturnList(filterList);
        TaskList list = new TaskList(taskReturns);
        return new ReturnStatus(list);
    }

    @Override
    public <T> ReturnStatus<T> getTaskList(String taskType, int status, int limit) {
        if (limit > Task.MAX_TASK_LIST_LIMIT) {
            limit = Task.MAX_TASK_LIST_LIMIT;
        }
        if (limit == 0) {
            limit = Task.DEFAULT_TASK_LIST_LIMIT;
        }
        TSchedulePos taskPos;
        try {
            taskPos = tSchedulePosDao.getTaskPos(taskType);
        } catch (Exception e) {
            e.printStackTrace();
            return ErrorStatusReturn.ERR_GET_TASK_SET_POS_FROM_DB;
        }
        String tableName = getTableName(taskPos.getScheduleBeginPos(), taskType);
        List<AsyncTask> taskList;
        try {
             taskList = aaronFlowTaskDao.getTaskList(taskType, status, limit, tableName);

        } catch (Exception e) {
            logger.error(ErrorStatus.ERR_GET_TASK_LIST_FROM_DB.getMsg());
            return ErrorStatusReturn.ERR_GET_TASK_LIST_FROM_DB;
        }
        List<AsyncTaskReturn> taskReturns = getTaskReturns(taskList);
        TaskList list = new TaskList(taskReturns);
        return new ReturnStatus(list);
    }

    private List<AsyncTaskReturn> getTaskReturns(List<AsyncTask> taskList) {
        List<AsyncTaskReturn> taskReturns = new ArrayList<>();
        for (AsyncTask asyncTask : taskList) {
            taskReturns.add(getTaskReturn(asyncTask));
        }
        return taskReturns;
    }

    private AsyncTaskReturn getTaskReturn(AsyncTask asyncTask) {
        AsyncTaskReturn tr = new AsyncTaskReturn(
                asyncTask.getUser_id(),
                asyncTask.getTask_id(),
                asyncTask.getTask_type(),
                asyncTask.getTask_stage(),
                asyncTask.getStatus(),
                asyncTask.getCrt_retry_num(),
                asyncTask.getMax_retry_num(),
                asyncTask.getMax_retry_interval(),
                asyncTask.getSchedule_log(),
                asyncTask.getTask_context(),
                asyncTask.getCreate_time(),
                asyncTask.getModify_time()
        );
        return tr;


    }

    @Override
    public <T> ReturnStatus<T> setTask(AsyncTaskSetRequest asyncTaskSetRequest) {
        AsyncTask asyncTask;
        String tableName = getTableNameById(asyncTaskSetRequest.getTask_id());
        try {
            asyncTask = aaronFlowTaskDao.find(asyncTaskSetRequest.getTask_id(), tableName);
        } catch (Exception e) {
            logger.error(ErrorStatus.ERR_GET_TASK_INFO.getMsg());
            return ErrorStatusReturn.ERR_GET_TASK_INFO;
        }

        if (asyncTask == null) {
            logger.error("db.TaskPosNsp.Find Task failed. TaskId:%s", asyncTaskSetRequest.getTask_id());
            return ErrorStatusReturn.ERR_GET_TASK_INFO;
        }
        if (!isUnUpdate(asyncTaskSetRequest.getStatus())) {
            asyncTask.setStatus(asyncTaskSetRequest.getStatus());
        }
        if (!isNullString(asyncTaskSetRequest.getTask_stage())) {
            asyncTask.setTask_stage(asyncTaskSetRequest.getTask_stage());
        }
        if (!isNullString(asyncTaskSetRequest.getTask_context())) {
            asyncTask.setTask_context(asyncTaskSetRequest.getTask_context());
        }
        if (!isNullString(asyncTaskSetRequest.getSchedule_log())) {
            asyncTask.setSchedule_log(asyncTaskSetRequest.getSchedule_log());
        }
        if (!isUnUpdate(asyncTaskSetRequest.getCrt_retry_num())) {
            asyncTask.setCrt_retry_num(asyncTaskSetRequest.getCrt_retry_num());
        }
        if (!isUnUpdate(asyncTaskSetRequest.getMax_retry_interval())) {
            asyncTask.setMax_retry_interval(asyncTaskSetRequest.getMax_retry_interval());
        }
        if (!isUnUpdate(asyncTaskSetRequest.getMax_retry_num())) {
            asyncTask.setMax_retry_num(asyncTaskSetRequest.getMax_retry_num());
        }
        if (asyncTaskSetRequest.getOrder_time() != 0) {
            asyncTask.setOrder_time(asyncTaskSetRequest.getOrder_time());
        }
        if (!isUnUpdate(asyncTaskSetRequest.getPriority())) {
            asyncTask.setPriority(asyncTaskSetRequest.getPriority());
        }

        asyncTask.setModify_time(System.currentTimeMillis());
        try {
            List<Integer> list = new ArrayList<Integer>() {{
                add(TaskStatus.SUCCESS.getStatus());
                add(TaskStatus.FAIL.getStatus());
            }};
            aaronFlowTaskDao.updateTask(asyncTask, list, tableName);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(ErrorStatus.ERR_SET_TASK.getMsg());
            return ErrorStatusReturn.ERR_SET_TASK;
        }
        return ErrorStatusReturn.SUCCESS;
    }

    private String getTableNameById(String taskId) {
        String[] strs = taskId.split("_");
        String tableName = getTableName(Integer.parseInt(strs[3]), strs[1]);
        return tableName;
    }

    private boolean isUnUpdate(int x) {
        return x == Task.DEFAULT_SET_TASK_STATUS;
    }

    private boolean isNullString(String s) {
        return s.equals(Task.DEFAULT_SET_TASK_STAGE_SCHEDULELOG_CONTEXT);
    }

    @Override
    public <T> ReturnStatus<T> getTask(String task_id) {
        AsyncTask asyncTask;
        String tableName = getTableNameById(task_id);
        try {
            asyncTask = aaronFlowTaskDao.find(task_id, tableName);
        } catch (Exception e) {
            logger.error("get task info error");
            return ErrorStatusReturn.ERR_GET_TASK_INFO;
        }
        TaskByTaskIdReturn<AsyncTaskReturn> taskByTaskIdReturn = new TaskByTaskIdReturn(getTaskReturn(asyncTask));
        return new ReturnStatus(taskByTaskIdReturn);
    }

    @Override
    public <T> ReturnStatus<T> getTaskByUserIdAndStatus(String user_id, int statusList) {

        List<AsyncTask> asyncTaskList;
        String tableName = getTableName(1, "LarkTask");
        try {
            asyncTaskList = aaronFlowTaskDao.getTaskByUser_idAndStatus(user_id, getStatusList(statusList), tableName);
        } catch (Exception e) {
            logger.error("get task info error");
            return ErrorStatusReturn.ERR_GET_TASK_INFO;
        }
        List<AsyncTaskReturn> taskReturns = getTaskReturns(asyncTaskList);
        TaskList list = new TaskList(taskReturns);
        return new ReturnStatus(list);
    }



    private List<Integer> getStatusList(int status) {
        List<Integer> statusList = new ArrayList<>();
        while (status != 0) {
            int cur = status & -status;
            statusList.add(cur);
            status ^= cur;
        }
        return statusList;
    }


    private List<AsyncTaskReturn> getAsyncTaskReturns(List<AsyncTask> taskList) {
        return getTaskReturnList(taskList);
    }

    private List<AsyncTaskReturn> getTaskReturnList(List<AsyncTask> taskList) {
        List<AsyncTaskReturn> tasks = new ArrayList<>();
        for (AsyncTask asyncTask : taskList) {
            AsyncTaskReturn asyncTaskReturn = new AsyncTaskReturn(
                    asyncTask.getUser_id(),
                    asyncTask.getTask_id(),
                    asyncTask.getTask_type(),
                    asyncTask.getTask_stage(),
                    asyncTask.getStatus(),
                    asyncTask.getCrt_retry_num(),
                    asyncTask.getMax_retry_num(),
                    asyncTask.getMax_retry_interval(),
                    asyncTask.getSchedule_log(),
                    asyncTask.getTask_context(),
                    asyncTask.getCreate_time(),
                    asyncTask.getModify_time()
            );
            tasks.add(asyncTaskReturn);
        }
        return tasks;
    }

    public List<String> conventTaskIdList(List<AsyncTask> list) {
        return list.stream().map(AsyncTask::getId).collect(Collectors.toList());
    }

    public int getTaskCountByStatus(TaskStatus taskStatus) {
        String tableName = getTableName(1, "LarkTask");
        return aaronFlowTaskDao.getTaskCountByStatus(taskStatus.getStatus(), tableName);
    }

    public int getAliveTaskCount() {
        String tableName = getTableName(1, "LarkTask");
        return aaronFlowTaskDao.getTaskCount(this.getAliveStatus(), tableName);
    }

    public int getAllTaskCount() {
        String tableName = getTableName(1, "LarkTask");
        return aaronFlowTaskDao.getTaskCount(this.getAllStatus(), tableName);
    }

    public List<AsyncTask> getAliveTaskList() {
        String tableName = getTableName(1, "LarkTask");
        return aaronFlowTaskDao.getAliveTaskList(this.getAliveStatus(), tableName);
    }

    public List<Integer> getAliveStatus() {
        return new LinkedList<Integer>() {{
            add(TaskStatus.PENDING.getStatus());
            add(TaskStatus.EXECUTING.getStatus());
        }};
    }
    public List<Integer> getAllStatus() {
        return new LinkedList<Integer>() {{
            add(TaskStatus.PENDING.getStatus());
            add(TaskStatus.EXECUTING.getStatus());
            add(TaskStatus.SUCCESS.getStatus());
            add(TaskStatus.FAIL.getStatus());
        }};
    }

    public String getTableName(int pos, String taskType) {
        return "t_" + taskType.toLowerCase() + "_" + this.tableName() + "_" + pos;
    }

    public String tableName() {
        return "task";
    }
}
