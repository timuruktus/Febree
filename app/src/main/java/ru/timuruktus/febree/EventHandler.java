package ru.timuruktus.febree;

import org.greenrobot.eventbus.EventBus;

public interface EventHandler {

    public void initListener();

    public void unregisterListener();

}
