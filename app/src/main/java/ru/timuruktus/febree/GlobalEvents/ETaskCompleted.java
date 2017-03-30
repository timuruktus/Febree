package ru.timuruktus.febree.GlobalEvents;

import ru.timuruktus.febree.BaseEvent;


public class ETaskCompleted implements BaseEvent {

    private int uniqueId;

    public ETaskCompleted(int uniqueId) {
        this.uniqueId = uniqueId;
    }

    public int getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(int uniqueId) {
        this.uniqueId = uniqueId;
    }



    @Override
    public void callback() {

    }
}
