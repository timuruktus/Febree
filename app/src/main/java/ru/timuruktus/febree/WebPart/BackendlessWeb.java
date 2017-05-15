package ru.timuruktus.febree.WebPart;

import android.util.Log;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.BackendlessDataQuery;
import com.backendless.persistence.DataQueryBuilder;
import com.backendless.persistence.QueryOptions;


import java.util.ArrayList;
import java.util.List;

import ru.timuruktus.febree.BaseModel;
import ru.timuruktus.febree.LocalPart.ERefreshAllTasks;
import ru.timuruktus.febree.LocalPart.ESaveAllTasks;
import ru.timuruktus.febree.LocalPart.Task;
import ru.timuruktus.febree.LocalPart.TaskEvent;

public class BackendlessWeb implements BaseModel{


    public BackendlessWeb() {
        initListener();
    }

    private ArrayList<Task> finalTasks = new ArrayList<>();
    private int offset = 0;


    final public void downloadAllTasks(EDownloadAllTasksAndCache event){
        finalTasks.clear();
        offset = 0;
        //getTasksFromWeb(new ESaveAllTasks());
    }


    final public void refreshAllTasks(EDownloadAndRefreshAllTasks event){
        finalTasks.clear();
        offset = 0;
        //getTasksFromWeb(new ERefreshAllTasks());
    }

    private void getTasksFromWeb(final TaskEvent event){
        DataQueryBuilder queryBuilder = DataQueryBuilder.create();
        //Log.d("mytag", "BackendlessWeb.getTasksFromWeb() event handled");
        queryBuilder.setPageSize(100);
        queryBuilder.setOffset(offset);
        Backendless.Persistence.of(Task.class).find(queryBuilder,
                new AsyncCallback<List<Task>>(){
                    @Override
                    public void handleResponse(List<Task> foundTasks) {
                        int size = foundTasks.size();
                        //Log.d("mytag", "BackendlessWeb.getTasksFromWeb().handleResponse() tasks got");
                        ArrayList<Task> tasks = (ArrayList<Task>) foundTasks;
                        //Log.d("mytag", "BackendlessWeb.getTasksFromWeb() tasks size" +  tasks.size());
                        if(size > 0){
                            offset += tasks.size();
                            finalTasks.addAll(tasks);
                            getTasksFromWeb(event);
                        }else {
                            offset = 0;
                            event.setTasks(finalTasks);
                        }
                    }
                    @Override
                    public void handleFault(BackendlessFault fault) {
                        //Log.d("MagFragmentFault", fault.getCode());
                    }
                });
    }

    @Override
    final public void initListener() {

    }

    @Override
    final public void unregisterListener() {

    }
}
