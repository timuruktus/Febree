package ru.timuruktus.febree.LocalPart;

import java.util.ArrayList;

import ru.timuruktus.febree.BaseEvent;


public class ESaveAllTasks implements BaseEvent {

    private ArrayList<Task> tasks;

    public ESaveAllTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }



    @Override
    public void callback() {

    }
}
