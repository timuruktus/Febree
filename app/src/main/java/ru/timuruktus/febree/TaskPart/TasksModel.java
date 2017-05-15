package ru.timuruktus.febree.TaskPart;



import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.ArrayList;

import ru.timuruktus.febree.LocalPart.Task;
import ru.timuruktus.febree.TaskPart.Interfaces.BaseTasksModel;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class TasksModel implements BaseTasksModel {


    @Override
    public Observable<Task> getTaskByStep(int block, int step) {
        return Observable.from(Select.from(Task.class)
                                .where(Condition.prop("block").eq(block))
                                .where(Condition.prop("step").eq(step))
                                .list())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
