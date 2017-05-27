package ru.timuruktus.febree.TaskPart;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import me.crosswall.lib.coverflow.CoverFlow;
import me.crosswall.lib.coverflow.core.PagerContainer;
import ru.timuruktus.febree.LocalPart.Task;
import ru.timuruktus.febree.R;
import ru.timuruktus.febree.TaskPart.Interfaces.BaseTasksPresenter;
import ru.timuruktus.febree.TaskPart.Interfaces.BaseTasksView;

import static ru.timuruktus.febree.MainPart.MainPresenter.ARG_INFO;

public class TasksFragment extends Fragment implements BaseTasksView, Serializable {

    public static final String ARGS_BLOCK = "Block";
    public static final String ARGS_STEP = "Step";

    private int block;
    private int step;
    private View rootView;
    private BaseTasksPresenter presenter;
    private PagerAdapter textPagerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        rootView  = inflater.inflate(
                R.layout.tasks_fragment, container, false);
        presenter = new TasksPresenter();
        Bundle args = getArguments();
        HashMap<String, Integer> info = (HashMap<String, Integer>) args.getSerializable(ARG_INFO);
        block = info.get(ARGS_BLOCK);
        step = info.get(ARGS_STEP);
        presenter.onCreate(this, block, step);
        ImageView tasksBackArrow = (ImageView) rootView.findViewById(R.id.tasksBackArrow);
        tasksBackArrow.setOnClickListener(v -> presenter.onBackArrowClick());
        TextView tasksStepName = (TextView) rootView.findViewById(R.id.tasksStepName);
        tasksStepName.setText(presenter.getStepName());

        return rootView;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d("mytag", "onDestroy() is TasksFragment");
        //presenter.onDestroy();
        //presenter = null;
        // TODO: Если раскоментить 2 строчки выше- появляется баг. Понять почему
    }


    @Override
    public void showTasks(ArrayList<Task> tasks) {
        PagerContainer container = (PagerContainer) rootView.findViewById(R.id.pager_container);
        ViewPager pager = container.getViewPager();
        FragmentManager fm = getChildFragmentManager();
        textPagerAdapter = new TasksFragmentAdapter(this, tasks, fm, block, step);
        pager.setAdapter(textPagerAdapter);

        new CoverFlow.Builder()
                .with(pager)
                .pagerMargin(0f)
                .scale(0.5f)
                .spaceSize(0f)
                .rotationY(0f)
                .build();
    }

    @Override
    public Fragment getFragment() {
        return this;
    }

}
