package ru.timuruktus.febree;

public abstract class AsyncEvent implements BaseEvent {

    EventCallbackListener listener;

    public AsyncEvent(EventCallbackListener listener) {
        this.listener = listener;
    }

    public void callback(){
        listener.eventCallback(this);
    }
}
