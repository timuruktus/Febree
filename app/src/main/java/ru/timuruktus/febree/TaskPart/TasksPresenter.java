package ru.timuruktus.febree.TaskPart;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import ru.timuruktus.febree.ContentPart.StepsFragment;
import ru.timuruktus.febree.LocalPart.Step;
import ru.timuruktus.febree.LocalPart.Task;
import ru.timuruktus.febree.MainPart.MainActivity;
import ru.timuruktus.febree.MainPart.MainPresenter;
import ru.timuruktus.febree.R;
import ru.timuruktus.febree.TaskPart.Interfaces.BaseTasksPresenter;
import ru.timuruktus.febree.TaskPart.Interfaces.BaseTasksModel;
import ru.timuruktus.febree.TaskPart.Interfaces.BaseTasksView;
import rx.Observer;
import rx.Subscription;

import static ru.timuruktus.febree.MainPart.MainPresenter.DONT_ADD_TO_BACKSTACK;
import static ru.timuruktus.febree.MainPart.MainPresenter.DONT_HIDE_TOOLBAR;
import static ru.timuruktus.febree.MainPart.MainPresenter.DONT_REFRESH;
import static ru.timuruktus.febree.MainPart.MainPresenter.REFRESH;

class TasksPresenter implements BaseTasksPresenter {


    private BaseTasksView view;
    private BaseTasksModel model;
    private int blockNum;
    private int stepNum;

    @Override
    public ArrayList<Task> onCreate(BaseTasksView view, int blockNum, int stepNum) {
        this.view = view;
        this.model = new TasksModel();
        Log.d("mytag", "onCreate blockNum = " + blockNum + " step = " + stepNum);
        ArrayList<Task> tasks = new ArrayList<>();
        this.blockNum = blockNum;
        this.stepNum = stepNum;
        addSubscription(model.getTaskByStep(blockNum, stepNum).subscribe(new Observer<Task>() {
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
                Log.d("mytag", "Next task");
            }
        }));

        return null;
    }

    @Override
    public void onBackArrowClick() {
        view.getFragment().getActivity().onBackPressed();
        //MainPresenter.changeFragment(new StepsFragment(), DONT_ADD_TO_BACKSTACK, DONT_REFRESH, DONT_HIDE_TOOLBAR);
    }

    @Override
    public String getStepName() {
        return model.getStepByNumber(blockNum, stepNum);
    }

    @Override
    public void addSubscription(Subscription subscription) {
        allSubscriptions.add(subscription);
    }

    @Override
    public void onDestroy() {
        allSubscriptions.unsubscribe();
        Log.d("mytag", "onDestroy() in TasksPresenter");
    }
}
