package ru.timuruktus.febree.TaskPart;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import ru.timuruktus.febree.LocalPart.Task;
import ru.timuruktus.febree.ProjectUtils.CustomDialog1;
import ru.timuruktus.febree.R;

public class TaskFragment extends Fragment {

    public static final String ARG_TASK = "item_task";
    private Context context;
    private Task currentTask;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(
                R.layout.task_fragment, container, false);
        Bundle args = getArguments();
        context = rootView.getContext();
        currentTask = (Task) args.getSerializable(ARG_TASK);
        String taskNumber = context.getResources().getString(R.string.task_number)
                + currentTask.getNumInStep();
        String taskTitle = currentTask.getTitle();
        String taskText = currentTask.getText();
        int taskDifficulty = currentTask.getPoints();

        ((TextView) rootView.findViewById(R.id.taskNumber)).setText(taskNumber);
        ((TextView) rootView.findViewById(R.id.taskTitle)).setText(taskTitle);
        ((TextView) rootView.findViewById(R.id.taskText)).setText(taskText);

        Button readyButton = (Button) rootView.findViewById(R.id.readyButton);
        readyButton.setOnClickListener(v -> makeConfirmDialog());

        return rootView;
    }

    private void makeConfirmDialog(){
        CustomDialog1 dialog1 = new CustomDialog1();
        String confirmTitle = context.getResources().getString(R.string.task_confirm_title);
        String confirmText = context.getResources().getString(R.string.task_confirm_text);
        String confirmButtonText = context.getResources().getString(R.string.task_confirm_button_text);
        int titleColor = context.getResources().getColor(R.color.task_confirm_title_color);
        int buttonColor = context.getResources().getColor(R.color.task_confirm_button_color);
        dialog1.setTitle(confirmTitle)
                .setTitleColor(titleColor)
                .setFirstText(confirmText)
                .setButtonText(confirmButtonText)
                .setButtonColor(buttonColor)
                .setOnClickListener(v -> {
                    taskCompleted(currentTask);
                    dialog1.dismiss();
                })
                .show();
    }

    private void taskCompleted(Task task){

    }
}
