package ru.timuruktus.febree.TaskPart;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import me.crosswall.lib.coverflow.CoverFlow;
import ru.timuruktus.febree.LocalPart.Task;
import ru.timuruktus.febree.R;
import ru.timuruktus.febree.TaskPart.Interfaces.BaseTaskPresenter;
import ru.timuruktus.febree.TaskPart.Interfaces.BaseTasksView;

public class TasksFragment extends Fragment implements BaseTasksView {

    public static final String ARG_BLOCK = "item_block";
    public static final String ARG_STEP = "item_step";
    private int block;
    private int step;
    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        rootView  = inflater.inflate(
                R.layout.tasks_fragment, container, false);
        Bundle args = getArguments();
        block = args.getInt(ARG_BLOCK);
        step = args.getInt(ARG_STEP);

        BaseTaskPresenter presenter = new TasksPresenter();
        presenter.onCreate(this, block, step);

        return rootView;
    }


    @Override
    public void showTasks(ArrayList<Task> tasks) {
        ViewPager tasksViewPager = (ViewPager) rootView.findViewById(R.id.tasksViewPager);
        TasksFragmentAdapter textPagerAdapter = new TasksFragmentAdapter(
                getActivity().getSupportFragmentManager(), tasks);
        tasksViewPager.setAdapter(textPagerAdapter);

        new CoverFlow.Builder()
                .with(tasksViewPager)
                .pagerMargin(0f)
                .scale(0.3f)
                .spaceSize(0f)
                .rotationY(0f)
                .build();
    }
}
