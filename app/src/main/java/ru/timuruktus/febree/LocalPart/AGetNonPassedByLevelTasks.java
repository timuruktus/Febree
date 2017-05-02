package ru.timuruktus.febree.LocalPart;

import java.util.ArrayList;

import ru.timuruktus.febree.AsyncEvent;
import ru.timuruktus.febree.EventCallbackListener;


public class AGetNonPassedByLevelTasks extends AsyncEvent {



    long level;
    ArrayList<Task> tasks = new ArrayList<>();

    public AGetNonPassedByLevelTasks(EventCallbackListener listener, long level) {
        super(listener);
        this.level = level;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public long getLevel() {
        return level;
    }

    public void setLevel(long level) {
        this.level = level;
    }

}
