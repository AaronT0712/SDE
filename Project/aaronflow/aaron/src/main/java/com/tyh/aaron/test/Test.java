package com.tyh.aaron.test;

import com.tyh.aaron.Client.TaskFlower;
import com.tyh.aaron.Client.TaskFlowerImpl;
import com.tyh.aaron.data.AsyncFlowClientData;
import com.tyh.aaron.data.AsyncTaskRequest;
import com.tyh.aaron.data.AsyncTaskReturn;
import com.tyh.aaron.task.Lark;
import com.tyh.aaron.task.TaskBuilder;

import java.util.List;

public class Test {
    static TaskFlower taskFlower = new TaskFlowerImpl();
    public static void main(String[] args) {
        // 用于测试创建任务
        testCeateTask();
//        testSetTask();

    }

    private static void testGetTaskList() {
        List<AsyncTaskReturn> larkTask = taskFlower.getTaskList(Lark.class, 1, 5);
        System.out.println(larkTask);
    }

    private static void testSetTask() {
//        AsyncTaskSetRequest asyncTaskSetRequest = AsyncTaskSetRequest.builder()

//        asyncTaskSetRequest.setStatus(TaskStatus.PENDING.getStatus());
//        taskFlower.setTask(asyncTaskSetRequest);
    }

    private static void testGetTask() {
        AsyncTaskReturn task = taskFlower.getTask("123");
        System.out.println(task);
    }

    private static void testCeateTask() {
        AsyncFlowClientData asyncFlowClientData = null;
        try {
            asyncFlowClientData = TaskBuilder.build(new Lark());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        String task = taskFlower.createTask(new AsyncTaskRequest(asyncFlowClientData));
    }

    private static void createTaskConfig() {

    }
}
