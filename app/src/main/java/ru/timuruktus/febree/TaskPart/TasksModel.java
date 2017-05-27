package ru.timuruktus.febree.TaskPart;



import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import com.orm.query.Condition;
import com.orm.query.Select;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import ru.timuruktus.febree.LocalPart.Step;
import ru.timuruktus.febree.LocalPart.Task;
import ru.timuruktus.febree.MainPart.MainActivity;
import ru.timuruktus.febree.R;
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

    @Override
    public String getStepByNumber(int blockNum, int stepNum) {
        List<Step> step = Select.from(Step.class)
                .where(Condition.prop("block").eq(blockNum))
                .where(Condition.prop("id_in_block").eq(stepNum))
                .list();
        Log.d("mytag", "TasksModel.getStepByNumber() blockNum = " + blockNum
                + " stepNum = " + stepNum);
        Context context = MainActivity.getContext();
        Resources resources = context.getResources();
        return resources.getString(R.string.tasks_back_to_menu);
    }
}
