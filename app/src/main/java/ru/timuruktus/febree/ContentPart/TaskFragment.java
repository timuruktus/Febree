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
import ru.timuruktus.febree.LocalPart.AGetNonPassedTasks;
import ru.timuruktus.febree.LocalPart.AGetTaskById;
import ru.timuruktus.febree.LocalPart.Settings;
import ru.timuruktus.febree.LocalPart.Task;
import ru.timuruktus.febree.MainPart.MainActivity;
import ru.timuruktus.febree.ProjectUtils.Utils;
import ru.timuruktus.febree.R;

import static ru.timuruktus.febree.ProjectUtils.Utils.WEEK_IN_SECONDS;

public class TaskFragment extends BaseFragment implements View.OnClickListener {


    public View rootView;
    private TextView currentTaskText, taskDifficulty;
    private Button completeButton, cancelButton;
    private Context context;
    Dialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rootView =
                inflater.inflate(R.layout.task_fragment, container, false);
        this.context = rootView.getContext();
        currentTaskText = (TextView) rootView.findViewById(R.id.currentTaskText);
        taskDifficulty = (TextView) rootView.findViewById(R.id.taskDifficulty);
        completeButton = (Button) rootView.findViewById(R.id.completeButton);
        completeButton.setOnClickListener(this);
        cancelButton = (Button) rootView.findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(this);

        if(Settings.getTaskId(context) == 0){
            pickRandomTask();
        }else{
            loadInterface();
        }

        return rootView;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.cancelButton){
            showCancelDialog();
        }else if(id == R.id.completeButton){
            showSureDialog();
        }
    }

    /*
    UNDER THAT LINE- MAIN METHODS
     */

    private void pickRandomTask(){
        EventBus.getDefault().post(new AGetNonPassedTasks(pickRandomTaskListener));
    }

    private void taskComplete(){
        EventBus.getDefault().post(new AGetTaskById(Settings.getTaskId(context), completeTaskListener));
    }

    private void taskSkip(){
        EventBus.getDefault().post(new AGetTaskById(Settings.getTaskId(context), skipTaskListener));
    }

    private void loadInterface(){
        EventBus.getDefault().post(new AGetTaskById(Settings.getTaskId(context), loadInterfaceListener));
    }

    /*
    UNDER THAT LINE- HELPFUL METHODS
     */
    private ArrayList<Task> getTasksByLevel(ArrayList<Task> tasks){
        ArrayList<Task> answer = new ArrayList<>();
        long currentLevel = Settings.getLevel(context);
        for(Task task : tasks){
            if(task.getLevel() == currentLevel){
                answer.add(task);
            }
        }
        return answer;
    }

    private void setDifficultyText(int level){
        if(level == 0){
            taskDifficulty.setText(context.getResources().getString(R.string.difficulty_low));
            taskDifficulty.setTextColor(ResourcesCompat.
                    getColor(getResources(), R.color.taskDifficultyLow, null));
        }else if(level == 1){
            taskDifficulty.setText(context.getResources().getString(R.string.difficulty_medium));
            taskDifficulty.setTextColor(ResourcesCompat.
                    getColor(getResources(), R.color.taskDifficultyMedium, null));
        }else{
            taskDifficulty.setText(context.getResources().getString(R.string.difficulty_high));
            taskDifficulty.setTextColor(ResourcesCompat.
                    getColor(getResources(), R.color.taskDifficultyHigh, null));
        }
    }

    private boolean moreThanWeek(){
        return Settings.getTimeBetweenLastAndCurrentTask(context) >= WEEK_IN_SECONDS;
    }

    /*
    UNDER THAT LINE- EVENT LISTENERS
     */


    EventCallbackListener pickRandomTaskListener = new EventCallbackListener() {
        @Override
        public void eventCallback(BaseEvent event) {
            AGetNonPassedTasks currentEvent = (AGetNonPassedTasks) event;
            ArrayList<Task> nonPassedTasks = currentEvent.getTasks();
            ArrayList<Task> availableTasks = getTasksByLevel(nonPassedTasks);
            if(availableTasks.size() == 0){
                if(Settings.getLevel(context) != 2) {
                    Settings.setLevel(rootView.getContext(), Settings.getLevel(context) + 1);
                    pickRandomTask();
                }else{
                    showAllCompleteDialog();
                    return;
                }
                return;
            }
            Random random = new Random();
            int newTaskNum = random.nextInt(availableTasks.size());
            Task currentTask = availableTasks.get(newTaskNum);
            Settings.setTaskId(context, currentTask.getUniqueId());
            Settings.setLastTaskTime(context, Utils.getCurrentTimeInSeconds());
            loadInterface();
        }
    };

    EventCallbackListener loadInterfaceListener = new EventCallbackListener() {
        @Override
        public void eventCallback(BaseEvent event) {
            if(event instanceof AGetTaskById){
                AGetTaskById currentEvent = (AGetTaskById) event;
                Task currentTask = currentEvent.getTask();
                currentTaskText.setText(currentTask.getText());
                int taskDifficulty = currentTask.getLevel();
                setDifficultyText(taskDifficulty);
            }
        }
    };

    EventCallbackListener skipTaskListener = new EventCallbackListener() {
        @Override
        public void eventCallback(BaseEvent event) {
            if(event instanceof AGetTaskById){
                AGetTaskById currentEvent = (AGetTaskById) event;
                Task currentTask = currentEvent.getTask();
                currentTask.setPassed(true);
                currentTask.save();
                Settings.incrementLevelsSkipped(context);
                pickRandomTask();
            }
        }
    };

    EventCallbackListener completeTaskListener = new EventCallbackListener() {
        @Override
        public void eventCallback(BaseEvent event) {
            if(event instanceof AGetTaskById){
                AGetTaskById currentEvent = (AGetTaskById) event;
                Task currentTask = currentEvent.getTask();
                currentTask.setPassed(true);
                Settings.incrementLevelsDone(context);
                if(moreThanWeek()){
                    Settings.changePoints(context, currentTask.getPoints() - 3);
                }
                currentTask.save();
                if(Settings.getPoints(context) > Settings.getCurrentLimit(context)
                        && Settings.getLevel(context) != Settings.HIGH_LEVEL){
                    Settings.increaseLevel(context);
                }
                pickRandomTask();
            }
        }
    };

    /*
    UNDER THAT LINE- BUTTON DIALOGS LISTENERS
     */

    public View.OnClickListener getCancelButOKListener(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                taskSkip();
            }
        };
    }

    public View.OnClickListener getDismissOnClickListener(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        };
    }

    public View.OnClickListener getSureOnClickListener(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskComplete();
                dialog.dismiss();
            }
        };
    }

    /*
    UNDER THAT LINE- ALL DIALOGS IN THAT FRAGMENT
     */

    private void showCancelDialog(){
        String text = context.getResources().getString(R.string.cancel_dialog_text);
        String title = context.getResources().getString(R.string.cancel_dialog_title);
        String buttonTextOk = context.getResources().getString(R.string.cancel_dialog_ok);
        String buttonTextCancel = context.getResources().getString(R.string.cancel_dialog_cancel);
        setDialogWith2But(text, title, buttonTextOk, buttonTextCancel,
                getCancelButOKListener(), getDismissOnClickListener());
        dialog.show();
    }

    private void showAllCompleteDialog(){
        String text = context.getResources().getString(R.string.all_dialog_text);
        String title = context.getResources().getString(R.string.all_dialog_title);
        String buttonText = context.getResources().getString(R.string.all_dialog_ok);
        setDialogWith1But(text, title, buttonText, getDismissOnClickListener());
        dialog.show();
    }

    private void showSureDialog(){
        String text = context.getResources().getString(R.string.sure_dialog_text);
        String title = context.getResources().getString(R.string.sure_dialog_title);
        String buttonText = context.getResources().getString(R.string.sure_dialog_ok);
        setDialogWith1But(text, title, buttonText, getSureOnClickListener());
        dialog.show();
    }


    /*
    UNDER THAT LINE- SECURE METHOD. DON'T CHANGE IT. THEY CREATES DIALOGS
     */
    private void setDialogWith1But(String text, String title, String buttonText,
                                   View.OnClickListener listener){
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog_1_button);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView textView = (TextView) dialog.findViewById(R.id.dialogText1);
        TextView titleView = (TextView) dialog.findViewById(R.id.dialogTitle1);
        textView.setText(text);
        titleView.setText(title);
        TextView dialogOK = (TextView) dialog.findViewById(R.id.dialogOK1);
        dialogOK.setOnClickListener(listener);
        dialogOK.setText(buttonText);
    }

    private void setDialogWith2But(String text, String title, String buttonTextOk,
                                   String buttonTextCancel,
                                   View.OnClickListener listenerOk,
                                   View.OnClickListener listenerCancel){
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog_2_buttons);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView textView = (TextView) dialog.findViewById(R.id.dialogText2);
        TextView titleView = (TextView) dialog.findViewById(R.id.dialogTitle2);

        textView.setText(text);
        titleView.setText(title);

        TextView dialogOK = (TextView) dialog.findViewById(R.id.dialogOK2);
        dialogOK.setOnClickListener(listenerOk);
        dialogOK.setText(buttonTextOk);
        TextView dialogCancel = (TextView) dialog.findViewById(R.id.dialogCancel2);
        dialogCancel.setOnClickListener(listenerCancel);
        dialogCancel.setText(buttonTextCancel);
    }

}
