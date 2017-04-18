package ru.timuruktus.febree.DoneTasksPart;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import ru.timuruktus.febree.BaseEvent;
import ru.timuruktus.febree.BaseFragment;
import ru.timuruktus.febree.EventCallbackListener;
import ru.timuruktus.febree.LocalPart.AGetPassedTasks;
import ru.timuruktus.febree.LocalPart.Settings;
import ru.timuruktus.febree.R;


public class DoneTasksFragment extends BaseFragment implements EventCallbackListener {

    public View rootView;
    private  ListView tasksListView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rootView =
                inflater.inflate(R.layout.done_tasks_fragment, container, false);
        tasksListView = (ListView) rootView.findViewById(R.id.doneTasksList);
        EventBus.getDefault().post(new AGetPassedTasks(this));
        return rootView;
    }

    @Override
    public void eventCallback(BaseEvent event) {
        AGetPassedTasks currentEvent = (AGetPassedTasks) event;
        DoneTasksAdapter adapter = new DoneTasksAdapter(rootView.getContext(), currentEvent.getTasks());
        tasksListView.setAdapter(adapter);
        tasksListView.addHeaderView(createHeader());
    }


    private View createHeader() {
         return getActivity().getLayoutInflater().inflate(R.layout.done_tasks_header, null);
    }
}
