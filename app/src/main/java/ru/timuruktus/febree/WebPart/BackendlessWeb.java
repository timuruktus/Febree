package ru.timuruktus.febree.WebPart;

import org.greenrobot.eventbus.EventBus;

import ru.timuruktus.febree.BaseEvent;
import ru.timuruktus.febree.BaseModel;

public class BackendlessWeb implements BaseModel {


    public BackendlessWeb() {
        EventBus.getDefault().register(this);
    }


    @Override
    public void eventCallback(BaseEvent e) {

    }
}
