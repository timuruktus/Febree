package ru.timuruktus.febree.TaskPart.Interfaces;

import android.app.Fragment;

import java.util.ArrayList;

import ru.timuruktus.febree.LocalPart.Task;

public interface BaseTasksView {

    void showTasks(ArrayList<Task> tasks);
    Fragment getFragment();
}
