package ru.timuruktus.febree.TaskPart.Interfaces;



import java.util.ArrayList;

import ru.timuruktus.febree.LocalPart.Task;
import rx.Observable;
import rx.Observer;

public interface BaseTasksModel {

    Observable<Task> getTaskByStep(int block, int step);
}
