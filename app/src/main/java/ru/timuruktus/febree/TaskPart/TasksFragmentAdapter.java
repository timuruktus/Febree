package ru.timuruktus.febree.TaskPart;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

import ru.timuruktus.febree.LocalPart.Task;

import static ru.timuruktus.febree.TaskPart.TaskFragment.ARG_TASK;

public class TasksFragmentAdapter extends FragmentStatePagerAdapter {

    ArrayList<Task> tasks;

    public TasksFragmentAdapter(FragmentManager fm, ArrayList<Task> tasks) {
        super(fm);
        this.tasks = tasks;
    }

    @Override
    public Fragment getItem(int i) {

        Fragment fragment = new TaskFragment();

        Bundle args = new Bundle();
        args.putSerializable(ARG_TASK, tasks.get(i));

        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public int getCount() {
        return tasks.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "Item " + (position + 1);
    }
}
