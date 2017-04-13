package ru.timuruktus.febree.LocalPart;

import java.util.ArrayList;

import ru.timuruktus.febree.BaseEvent;


public class ERefreshAllTasks extends TaskEvent {


    public ERefreshAllTasks(){}

    public ERefreshAllTasks(ArrayList<Task> tasks) {
        super(tasks);
    }
}
