package ru.timuruktus.febree.TaskPart;

import android.app.Fragment;
import android.content.Context;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.List;

import ru.timuruktus.febree.LocalPart.Settings;
import ru.timuruktus.febree.LocalPart.Step;
import ru.timuruktus.febree.LocalPart.Task;
import ru.timuruktus.febree.ProjectUtils.CustomDialog1;
import ru.timuruktus.febree.R;

public class TaskFragment extends Fragment {

    public static final String ARG_NUM_IN_BLOCK = "item_blockNum";
    public static final String ARG_STEP_NUM = "item_stepNum";
    public static final String ARG_POSITION = "item_position_in_step";
    public static final String ARG_ADAPTER = "item_adapter";
    public static final String ARG_CURRENT_TASK = "item_currentTask";
    private Context context;
    private Task currentTask;
    private View rootView;
    private int positionInStep;
    private int blockNum, stepNum;
    private Step currentStep;
    private TasksFragmentAdapter adapter;


    static TaskFragment newInstance(Task currentTask, int blockNum, int stepNum, int position, TasksFragmentAdapter adapter) {
        TaskFragment pageFragment = new TaskFragment();
        pageFragment.blockNum = blockNum;
        pageFragment.stepNum = stepNum;
        pageFragment.positionInStep = position;
        pageFragment.adapter = adapter;
        pageFragment.currentTask = currentTask;
        return pageFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentStep = findStepByNumber(blockNum, stepNum);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(
                R.layout.task_fragment, container, false);
        context = rootView.getContext();
        String taskNumber = context.getResources().getString(R.string.task_number)
                + " " + ++positionInStep;
        String taskTitle = currentTask.getTitle();
        String taskText = currentTask.getText();
        int taskDifficulty = currentTask.getPoints();

        TextView taskNumberView = (TextView) rootView.findViewById(R.id.taskNumber);
        taskNumberView.setText(taskNumber);
        TextView taskTitleView = (TextView) rootView.findViewById(R.id.taskTitle);
        taskTitleView.setText(taskTitle);
        TextView taskTextView = (TextView) rootView.findViewById(R.id.taskText);
        taskTextView.setText(taskText);

        Button readyButton = (Button) rootView.findViewById(R.id.readyButton);

        if(!currentTask.isPassed()) {
            readyButton.setOnClickListener(v -> makeConfirmDialog());
        }else{
            ImageView taskCrown = (ImageView) rootView.findViewById(R.id.taskCrown);
            taskCrown.setVisibility(View.VISIBLE);
            String textForButton = context.getResources().getString(R.string.task_completed);
            readyButton.setText(textForButton);
            readyButton.setEnabled(false);
            readyButton.setBackgroundResource(R.drawable.task_button_background_disabled);
            int titleColor = context.getResources().getColor(R.color.task_title_color_passed);
            taskTitleView.setTextColor(titleColor);
        }

        return rootView;
    }

    private void makeConfirmDialog(){
        CustomDialog1 dialog1 = new CustomDialog1();
        String confirmTitle = context.getResources().getString(R.string.task_confirm_title);
        String confirmText = context.getResources().getString(R.string.task_confirm_text);
        String confirmButtonText = context.getResources().getString(R.string.task_confirm_button_text);
        int titleColor = context.getResources().getColor(R.color.task_confirm_title_color);
        dialog1.buildDialog(context)
                .setDismissOnClick(true)
                .setTitle(confirmTitle)
                .setTitleColor(titleColor)
                .setFirstText(confirmText)
                .setButtonText(confirmButtonText)
                .setOnClickListener(v -> {
                    taskCompleted();
                    dialog1.dismiss();
                })
                .show();
    }

    private void taskCompleted(){
        currentStep.setCompletedTasks(1 + currentStep.getCompletedTasks());
        Log.d("mytag", "Num of completed tasks = " + currentStep.getCompletedTasks());
        if(1 + currentStep.getCompletedTasks() == currentStep.getNumOfTasks()){
            currentStep.setStatus(Step.STATUS_COMPLETED);
        }
        currentTask.setPassed(true);
        adapter.refreshData(positionInStep);
        Log.d("mytag", "Points before " + Settings.getPoints());
        Settings.changePoints(currentTask.getPoints());
        Log.d("mytag", "Points after " + Settings.getPoints());
        currentTask.save();
        currentStep.save();

    }

    private Step findStepByNumber(int blockNum, int stepNum){
        List<Step> step = Select.from(Step.class)
                .where(Condition.prop("block").eq(blockNum))
                .where(Condition.prop("id_in_block").eq(stepNum))
                .list();
        return step.get(0);
    }
}
