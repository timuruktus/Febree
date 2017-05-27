package ru.timuruktus.febree;

import rx.Subscription;

public interface BasePresenter {

    void addSubscription(Subscription subscription);
    void onDestroy();

}
