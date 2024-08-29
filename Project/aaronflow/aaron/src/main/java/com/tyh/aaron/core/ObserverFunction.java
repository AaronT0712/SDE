package com.tyh.aaron.core;

import com.tyh.aaron.data.AsyncTaskBase;
import com.tyh.aaron.data.AsyncTaskReturn;
import com.tyh.aaron.data.AsyncTaskSetStage;
import com.tyh.aaron.data.ScheduleConfig;

import java.util.List;

public interface ObserverFunction {
    void onBoot();
    void onObtain(List<AsyncTaskReturn> taskList, List<AsyncTaskBase> asyncTaskBaseList);
    void onExecute(AsyncTaskBase asyncTaskReturn);
    void onFinish(AsyncTaskBase asyncTaskReturn, AsyncTaskSetStage asyncTaskSetStage, Class<?> aClass);
    void onStop(AsyncTaskBase asyncTaskReturn);
    void onError(AsyncTaskBase asyncTaskReturn, ScheduleConfig scheduleConfig, List<AsyncTaskBase> asyncTaskBaseList, Class<?> aClass, Exception e);

}
