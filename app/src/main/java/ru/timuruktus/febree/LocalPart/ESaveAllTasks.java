package ru.timuruktus.febree.LocalPart;

import java.util.ArrayList;

import ru.timuruktus.febree.BaseEvent;


public class ESaveAllTasks extends TaskEvent {

    public ESaveAllTasks(){}

    public ESaveAllTasks(ArrayList<Task> tasks) {
        super(tasks);
    }
}
