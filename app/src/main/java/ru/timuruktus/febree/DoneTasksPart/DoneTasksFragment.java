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

import static ru.timuruktus.febree.ProjectUtils.Utils.boldTypeFace;
import static ru.timuruktus.febree.ProjectUtils.Utils.italicTypeface;
import static ru.timuruktus.febree.ProjectUtils.Utils.thinTypeface;
import static ru.timuruktus.febree.ProjectUtils.Utils.usualTypeface;


public class DoneTasksFragment extends BaseFragment implements EventCallbackListener {

    public View rootView;
    private  ListView tasksListView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Log.d("mytag", "DoneTasksFragment.onCreate() doneTasksFragment was created");
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
        View header = createHeader();

        tasksListView.setAdapter(adapter);
        tasksListView.removeHeaderView(header);
        tasksListView.addHeaderView(header);
        setTypefaces();
    }


    private View createHeader() {
        ///Log.d("mytag", "DoneTasksFragment.createHeader() header created");
        View header =  getActivity().getLayoutInflater().inflate(R.layout.done_tasks_header, null);
        TextView headerText = (TextView) header.findViewById(R.id.doneTasksHeaderTitle);
        headerText.setTypeface(thinTypeface);
         return header;
    }

    @Override
    public void setTypefaces() {

    }
}
