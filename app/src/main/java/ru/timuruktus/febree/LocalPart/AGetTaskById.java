package ru.timuruktus.febree.LocalPart;

import ru.timuruktus.febree.AsyncEvent;
import ru.timuruktus.febree.BaseEvent;
import ru.timuruktus.febree.EventCallbackListener;


public class AGetTaskById extends AsyncEvent {

    private long taskId;
    private Task task;

    public AGetTaskById(long taskId, EventCallbackListener listener) {
        super(listener);
        this.taskId = taskId;
    }

    public long getTaskId() {
        return taskId;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

}
