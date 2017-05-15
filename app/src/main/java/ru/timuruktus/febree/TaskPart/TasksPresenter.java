package ru.timuruktus.febree.TaskPart;

import android.util.Log;

import java.util.ArrayList;

import ru.timuruktus.febree.LocalPart.Task;
import ru.timuruktus.febree.TaskPart.Interfaces.BaseTaskPresenter;
import ru.timuruktus.febree.TaskPart.Interfaces.BaseTasksModel;
import ru.timuruktus.febree.TaskPart.Interfaces.BaseTasksView;
import rx.Observable;
import rx.Observer;

class TasksPresenter implements BaseTaskPresenter {


    private BaseTasksView view;
    private BaseTasksModel model;

    @Override
    public ArrayList<Task> onCreate(BaseTasksView view, int block, int step) {
        this.view = view;
        this.model = new TasksModel();
        ArrayList<Task> tasks = new ArrayList<>();
        model.getTaskByStep(block, step).subscribe(new Observer<Task>() {
            @Override
            public void onCompleted() {
                view.showTasks(tasks);
            }

            @Override
            public void onError(Throwable e) {
                Log.d("mytag", "TasksPresenter.onCreate some error" + e);
            }

            @Override
            public void onNext(Task task) {
                tasks.add(task);
            }
        });

        return null;
    }

}
