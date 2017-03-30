package ru.timuruktus.febree.LocalPart;

import java.util.ArrayList;

import ru.timuruktus.febree.BaseEvent;
import ru.timuruktus.febree.EventCallbackListener;


public class EGetNonPassedTasks implements BaseEvent {

    EventCallbackListener listener;

    public EGetNonPassedTasks(EventCallbackListener listener) {
        this.listener = listener;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    ArrayList<Task> tasks = new ArrayList<>();


    @Override
    public void callback() {
        listener.eventCallback(this);
    }
}
