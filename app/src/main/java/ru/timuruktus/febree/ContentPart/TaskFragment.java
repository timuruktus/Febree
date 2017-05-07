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
        this.context = rootView.getContext();

        currentTaskText = (TextView) rootView.findViewById(R.id.currentTaskText);
        currentTaskTitle = (TextView) rootView.findViewById(R.id.currentTaskTitle);

        taskDifficulty = (TextView) rootView.findViewById(R.id.taskDifficulty);
        taskPoints = (TextView) rootView.findViewById(R.id.taskPoints);
        completeButton = (Button) rootView.findViewById(R.id.completeButton);
        completeButton.setOnClickListener(this);
        cancelButton = (Button) rootView.findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(this);

        if(Settings.getCurrentTaskId() == 0 || Settings.getCurrentTaskId() == -1){
            pickRandomTask();
        }else{
            loadInterface();
        }

        setTypefaces();

        return rootView;
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.cancelButton){
            if(Settings.getCurrentTaskId() != -1) {
                showCancelDialog();
            }else{
                showAllCompleteDialog();
            }
        }else if(id == R.id.completeButton){
            if(Settings.getCurrentTaskId() != -1) {
                showSureDialog();
            }else{
                showAllCompleteDialog();
            }
        }
    }

    /*
    UNDER THAT LINE- MAIN METHODS
     */


    @Override
    public void setTypefaces(){
        TextView difficultyTextView = (TextView) rootView.findViewById(R.id.difficultyTextView);
        TextView pointsTextView = (TextView) rootView.findViewById(R.id.pointsTextView);
        currentTaskTitle = (TextView) rootView.findViewById(R.id.currentTaskTitle);
        currentTaskTitle.setTypeface(usualTypeface);
        currentTaskText.setTypeface(thinTypeface);
        taskDifficulty.setTypeface(boldItalicTypeface);
        taskPoints.setTypeface(boldItalicTypeface);
        completeButton.setTypeface(usualTypeface);
        cancelButton.setTypeface(usualTypeface);
        difficultyTextView.setTypeface(italicTypeface);
        pointsTextView.setTypeface(italicTypeface);
    }

    private void pickRandomTask(){
        EventBus.getDefault().post(new AGetNonPassedByLevelTasks(pickRandomTaskListener, Settings.getLevel()));
    }

    private void taskComplete(){
        EventBus.getDefault().post(new AGetTaskById(Settings.getCurrentTaskId(), completeTaskListener));
    }

    private void taskSkip(){
        EventBus.getDefault().post(new AGetTaskById(Settings.getCurrentTaskId(), skipTaskListener));
    }

    private void loadInterface(){
        EventBus.getDefault().post(new AGetTaskById(Settings.getCurrentTaskId(), loadInterfaceListener));
    }

    /*
    UNDER THAT LINE- HELPFUL METHODS
     */
    private ArrayList<Task> getTasksByCurrentLevel(ArrayList<Task> tasks){
        ArrayList<Task> answer = new ArrayList<>();
        long currentLevel = Settings.getLevel();
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
                    getColor(getResources(), R.color.taskDifficultyEasy, null));
        }else if(level == 1){
            taskDifficulty.setText(context.getResources().getString(R.string.difficulty_medium));
            taskDifficulty.setTextColor(ResourcesCompat.
                    getColor(getResources(), R.color.taskDifficultyMedium, null));
        }else{
            taskDifficulty.setText(context.getResources().getString(R.string.difficulty_high));
            taskDifficulty.setTextColor(ResourcesCompat.
                    getColor(getResources(), R.color.taskDifficultyHard, null));
        }
    }

    private void showSomeMessage(){
        int tasksCompleted = (int) Settings.getLevelsDone();
        String text;
        switch (tasksCompleted){
            case 1:
                text = context.getResources().getString(R.string.after_1_task);
                showDialogAfterTask(text);
                break;
            case 2:
                text = context.getResources().getString(R.string.after_2_task);
                showDialogAfterTask(text);
                break;
            case 3:
                text = context.getResources().getString(R.string.after_3_task);
                showDialogAfterTask(text);
                break;
            case 5:
                text = context.getResources().getString(R.string.after_5_task);
                showDialogAfterTask(text);
                break;
            case 6:
                text = context.getResources().getString(R.string.after_6_task);
                showDialogAfterTask(text);
                break;
            case 7:
                text = context.getResources().getString(R.string.after_7_task);
                showDialogAfterTask(text);
                break;
            case 9:
                text = context.getResources().getString(R.string.after_9_task);
                showDialogAfterTask(text);
                break;
        }
    }

    private boolean moreThanWeek(){
        return Settings.getTimeBetweenLastTaskAndCurrentTime() >= WEEK_IN_SECONDS;
    }



    /*
    UNDER THAT LINE- EVENT LISTENERS
     */


    EventCallbackListener pickRandomTaskListener = new EventCallbackListener() {
        @Override
        public void eventCallback(BaseEvent event) {
            AGetNonPassedByLevelTasks currentEvent = (AGetNonPassedByLevelTasks) event;
            ArrayList<Task> availableTasks = currentEvent.getTasks();
            if(availableTasks.size() == 0){
                if(Settings.getLevel() != 2) {
                    Settings.increaseLevel();
                    pickRandomTask();
                }else{
                    Task task = new Task(3,"",false,0,-1,0,false);
                    Settings.setCurrentTaskId(-1);
                    task.save();
                    return;
                }
                return;
            }
            Random random = new Random();
            int newTaskNum = random.nextInt(availableTasks.size());
            Task currentTask = availableTasks.get(newTaskNum);
            Settings.setCurrentTaskId(currentTask.getUniqueId());
            Settings.setLastTaskTime(Utils.getCurrentTimeInSeconds());
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
                taskPoints.setText(currentTask.getPoints() + "");
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
                currentTask.setSkipped(true);
                currentTask.save();
                Settings.incrementLevelsSkipped();
                pickRandomTask();
            }
        }
    };

    EventCallbackListener completeTaskListener = new EventCallbackListener() {
        @Override
        public void eventCallback(BaseEvent event) {
            AGetTaskById currentEvent = (AGetTaskById) event;
            Task currentTask = currentEvent.getTask();
            currentTask.setPassed(true);
            Settings.incrementLevelsDone();
            Settings.changePoints(currentTask.getPoints());
            currentTask.save();
            if(Settings.getPoints() > Settings.getCurrentLimit()
                    && Settings.getLevel() != Settings.HARD_LEVEL){
                Settings.increaseLevel();
            }
            pickRandomTask();
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
                dialog.dismiss();
                taskComplete();
                showSomeMessage();
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

    private void showDialogAfterTask(String text){
        String title = context.getResources().getString(R.string.all_dialog_title);
        String buttonText = context.getResources().getString(R.string.all_dialog_ok);
        setDialogWith1But(text, title, buttonText, getDismissOnClickListener());
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
