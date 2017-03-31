package ru.timuruktus.febree.GlobalEvents;

import ru.timuruktus.febree.BaseEvent;
import ru.timuruktus.febree.LocalPart.Task;


public class ETaskCompleted implements BaseEvent {

    private Task task;

    public ETaskCompleted(Task task) {
        this.task = task;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

}
