package ru.timuruktus.febree.TaskPart.Interfaces;

import java.util.ArrayList;


import ru.timuruktus.febree.LocalPart.Task;


public interface BaseTaskPresenter {

    ArrayList<Task> onCreate(BaseTasksView view, int block, int step);

}
