package ru.timuruktus.febree.LocalPart;

import java.util.ArrayList;

import ru.timuruktus.febree.AsyncEvent;
import ru.timuruktus.febree.EventCallbackListener;


public class AGetPassedTasks extends AsyncEvent {

    public AGetPassedTasks(EventCallbackListener listener) {
        super(listener);
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    ArrayList<Task> tasks = new ArrayList<>();
}
