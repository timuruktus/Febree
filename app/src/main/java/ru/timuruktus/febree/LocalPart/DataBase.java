package ru.timuruktus.febree.LocalPart;

import android.util.Log;

import com.orm.query.Condition;
import com.orm.query.Select;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import ru.timuruktus.febree.BaseEvent;
import ru.timuruktus.febree.BaseModel;
import ru.timuruktus.febree.GlobalEvents.ETaskCompleted;

public class DataBase implements BaseModel {


    public DataBase() {
        Log.d("mytag", "DataBase.DataBase() event handles initialised");
        initListener();
    }

    @Subscribe
    public void saveAllTasks(ESaveAllTasks event){
        ArrayList<Task> tasks = event.getTasks();
        for(Task currentTask : tasks){
            currentTask.save();
            Log.d("mytag", "DataBase.saveAllTasks() task saved");
        }
    }

    @Subscribe
    public void refreshAllTasks(ERefreshAllTasks event){
        ArrayList<Task> tasks = event.getTasks();

        for(Task currentTask : tasks){
            List<Task> localTask =
                    Task.find(Task.class, "unique_id = ?",
                            currentTask.getUniqueId() + "");
            if(localTask.size() == 0){
                currentTask.save();
            }else if(localTask.get(0).getVersion() < currentTask.getVersion()){
                localTask.get(0).setVersion(currentTask.getVersion());
                localTask.get(0).setText(currentTask.getText());
                localTask.get(0).setPoints(currentTask.getPoints());
                localTask.get(0).save();
            }
        }
    }

    @Subscribe
    public void taskCompleted(ETaskCompleted event){
        Task task = event.getTask();
        task.setPassed(true);
        task.save();
    }

    @Subscribe
    public void getAllPassedTasks(AGetPassedTasks event){
        event.setTasks(getTaskByPassed(1));
        event.callback();
    }

    @Subscribe
    public void getAllNonPassedTasks(AGetNonPassedTasks event){
        event.setTasks(getTaskByPassed(0));
        event.callback();
    }

    private ArrayList<Task> getTaskByPassed(int passed){
        return (ArrayList<Task>) Select.from(Task.class)
                .where(Condition.prop("passed").eq(passed))
                .where(Condition.prop("unique_id").notEq(-1))
                .where(Condition.prop("skipped").notEq(1))
                .list();
    }

    @Subscribe
    public void getTaskById(AGetTaskById event){
        long id = event.getTaskId();
        List<Task> tempList = Select.from(Task.class)
                .where(Condition.prop("unique_id").eq(id))
                .list();
        event.setTask(tempList.get(0));
        event.callback();
    }

    @Subscribe
    public void clearAllTasks(EClearAllTasks event){
        Task.deleteAll(Task.class);
    }

    @Override
    public void initListener() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void unregisterListener() {
        EventBus.getDefault().unregister(this);
    }
}
