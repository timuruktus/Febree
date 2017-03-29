package ru.timuruktus.febree.LocalPart;

import org.greenrobot.eventbus.EventBus;

import ru.timuruktus.febree.BaseEvent;
import ru.timuruktus.febree.BaseModel;

public class DataBase implements BaseModel {


    public DataBase() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void eventCallback(BaseEvent e) {

    }
}
