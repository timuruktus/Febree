package ru.timuruktus.febree.StepsPart.Interfaces;

import ru.timuruktus.febree.BasePresenter;
import rx.subscriptions.CompositeSubscription;

public interface BaseStepsPresenter extends BasePresenter {

    CompositeSubscription allSubscriptions = new CompositeSubscription();

    void onCreate();
    void onDestroy();
    void onStepClick(int blockNum, int stepNum);
}
