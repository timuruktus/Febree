package ru.timuruktus.febree.ContentPart;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Random;

import ru.timuruktus.febree.BaseEvent;
import ru.timuruktus.febree.BaseFragment;
import ru.timuruktus.febree.EventCallbackListener;
import ru.timuruktus.febree.LocalPart.AGetNonPassedByLevelTasks;
import ru.timuruktus.febree.LocalPart.AGetNonPassedTasks;
import ru.timuruktus.febree.LocalPart.AGetTaskById;
import ru.timuruktus.febree.LocalPart.Settings;
import ru.timuruktus.febree.LocalPart.Task;
import ru.timuruktus.febree.MainPart.MainPresenter;
import ru.timuruktus.febree.ProjectUtils.Utils;
import ru.timuruktus.febree.R;

import static ru.timuruktus.febree.ProjectUtils.Utils.*;

public class TaskFragment extends BaseFragment implements View.OnClickListener {


    public View rootView;
    private TextView currentTaskText, taskDifficulty, taskPoints, currentTaskTitle;
    private Button completeButton, cancelButton;
    private Context context;
    private Dialog dialog;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rootView =
                inflater.inflate(R.layout.task_fragment, container, false);

        return rootView;
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();

    }

    @Override
    public void setTypefaces(){

    }


}
