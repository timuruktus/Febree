package ru.timuruktus.febree.WebPart;

import android.util.Log;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.BackendlessDataQuery;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import ru.timuruktus.febree.BaseEvent;
import ru.timuruktus.febree.BaseModel;
import ru.timuruktus.febree.LocalPart.ESaveAllTasks;
import ru.timuruktus.febree.LocalPart.Task;

public class BackendlessWeb implements BaseModel{


    public BackendlessWeb() {
        EventBus.getDefault().register(this);
    }

    @Subscribe
    public void downloadAllTasks(EDownloadAllTasksAndCache event){
        BackendlessDataQuery dataQuery = new BackendlessDataQuery();
        Log.d("mytag", "BackendlessWeb.downloadAllTasks() event handled");
        Backendless.Persistence.of(Task.class).find(dataQuery,
                new AsyncCallback<BackendlessCollection<Task>>(){
                    @Override
                    public void handleResponse(BackendlessCollection<Task> foundTasks) {
                        Log.d("mytag", "BackendlessWeb.downloadAllTasks().handleResponse() tasks got");
                        ArrayList<Task> tasks = (ArrayList<Task>) foundTasks.getCurrentPage();
                        EventBus.getDefault().post(new ESaveAllTasks(tasks));
                    }
                    @Override
                    public void handleFault(BackendlessFault fault) {
                        Log.d("MagFragmentFault", fault.getCode());
                    }
                });
    }

}
