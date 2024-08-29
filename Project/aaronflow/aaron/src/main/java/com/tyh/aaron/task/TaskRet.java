package com.tyh.aaron.task;

import com.tyh.aaron.data.AsyncTaskSetStage;
import lombok.Data;

@Data
public class TaskRet<T> {
    T result;
    AsyncTaskSetStage asyncTaskSetStage;
    public TaskRet(T result) {
        this(result, null);
    }
    public TaskRet(T result, AsyncTaskSetStage asyncTaskSetStage) {
        this.result = result;
        this.asyncTaskSetStage = asyncTaskSetStage;
    }

}
