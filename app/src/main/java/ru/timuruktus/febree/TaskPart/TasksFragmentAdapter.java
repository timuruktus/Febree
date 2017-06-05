package ru.timuruktus.febree.TaskPart;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcel;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.io.Serializable;
import java.util.ArrayList;

import ru.timuruktus.febree.LocalPart.Task;
import ru.timuruktus.febree.MainPart.MainActivity;
import ru.timuruktus.febree.R;
import ru.timuruktus.febree.TaskPart.Interfaces.BaseTasksView;


public class TasksFragmentAdapter extends FragmentStatePagerAdapter{

    private ArrayList<Task> tasks;
    private int blockNum, stepNum;
    private BaseTasksView fragment;

    public TasksFragmentAdapter(BaseTasksView fragment, ArrayList<Task> tasks, FragmentManager fm, int blockNum, int stepNum) {
        super(fm);
        this.tasks = tasks;
        this.blockNum = blockNum;
        this.stepNum = stepNum;
        this.fragment = fragment;
    }

    @Override
    public Fragment getItem(int position) {
        return TaskFragment.newInstance(tasks.get(position), blockNum, stepNum, position, this);
    }

    @Override
    public int getCount() {
        return tasks.size();
    }



    public int getBlockNum() {
        return blockNum;
    }

    public void setBlockNum(int blockNum) {
        this.blockNum = blockNum;
    }

    public int getStepNum() {
        return stepNum;
    }

    public void setStepNum(int stepNum) {
        this.stepNum = stepNum;
    }

    public void refreshData(int taskNum) {
        fragment.showTasks(tasks);
        fragment.refreshData(taskNum - 1);
    }
}
