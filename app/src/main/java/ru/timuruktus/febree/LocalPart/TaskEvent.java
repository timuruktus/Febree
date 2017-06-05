package ru.timuruktus.febree.LocalPart;

import java.util.ArrayList;



public class TaskEvent{

    private ArrayList<Task> tasks;

    public TaskEvent(){}

    public TaskEvent(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }
}
