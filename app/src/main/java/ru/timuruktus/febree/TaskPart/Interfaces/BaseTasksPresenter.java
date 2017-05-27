package ru.timuruktus.febree.TaskPart.Interfaces;

import java.util.ArrayList;


import ru.timuruktus.febree.BasePresenter;
import ru.timuruktus.febree.LocalPart.Task;
import rx.subscriptions.CompositeSubscription;


public interface BaseTasksPresenter extends BasePresenter {

    CompositeSubscription allSubscriptions = new CompositeSubscription();

    ArrayList<Task> onCreate(BaseTasksView view, int block, int step);
    void onBackArrowClick();
    String getStepName();
    void onDestroy();

}
