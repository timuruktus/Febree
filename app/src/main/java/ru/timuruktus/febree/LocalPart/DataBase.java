package ru.timuruktus.febree.LocalPart;

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
        EventBus.getDefault().register(this);
    }

    @Subscribe
    public void saveAllTasks(ESaveAllTasks event){
        ArrayList<Task> tasks = event.getTasks();
        for(Task currentTask : tasks){
            currentTask.save();
        }
    }

    @Subscribe
    public void taskCompleted(ETaskCompleted event){
        int id = event.getUniqueId();
        List<Task> tempList = Select.from(Task.class)
                .where(Condition.prop("unique_id").eq(id))
                .list();
        Task currentTask = tempList.get(0);
        currentTask.setPassed(true);
        currentTask.save();
    }

    @Subscribe
    public void getAllNonPassedTasks(EGetNonPassedTasks event){
        List<Task> tempList = Select.from(Task.class)
                .where(Condition.prop("passed").eq(false))
                .list();
        event.setTasks((ArrayList<Task>) tempList);
        event.callback();
    }

    public static Task getTaskById(int id){
        List<Task> tempList = Select.from(Task.class)
                .where(Condition.prop("unique_id").eq(id))
                .list();
        return tempList.get(0);
    }

    @Override
    public void eventCallback(BaseEvent e) {

    }
}
