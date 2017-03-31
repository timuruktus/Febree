package ru.timuruktus.febree.ContentPart;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import ru.timuruktus.febree.R;

public class TaskFragment extends BaseFragment implements View.OnClickListener {


    public View rootView;
    private TextView currentTaskText, taskDifficulty;
    private Button completeButton, cancelButton;
    private Context context;

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

        if(Settings.getTaskId(rootView.getContext()) == 0){
            pickRandomTask();
        }else{
            loadInterface();
        }


        return rootView;
    }

    private void pickRandomTask(){
        EventBus.getDefault().post(new AGetNonPassedTasks(pickRandomTaskListener));
    }

    private void taskCompleted(){

    }

    private ArrayList<Task> getTasksByLevel(ArrayList<Task> tasks){
        ArrayList<Task> answer = new ArrayList<>();
        int currentLevel = Settings.getLevel(context);
        for(Task task : tasks){
            if(task.getLevel() == currentLevel){
                answer.add(task);
            }
        }
        return answer;
    }

    private void loadInterface(){
        EventBus.getDefault().post(new AGetTaskById(Settings.getTaskId(context), loadInterfaceListener));
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


    EventCallbackListener pickRandomTaskListener = new EventCallbackListener() {
        @Override
        public void eventCallback(BaseEvent event) {
            AGetNonPassedTasks currentEvent = (AGetNonPassedTasks) event;
            ArrayList<Task> nonPassedTasks = currentEvent.getTasks();
            ArrayList<Task> availableTasks = getTasksByLevel(nonPassedTasks);
            if(availableTasks.size() == 0){
                if(Settings.getLevel(context) != 2) {
                    Settings.setLevel(rootView.getContext(), Settings.getLevel(context) + 1);
                }else{
                    // TODO Здесь диалог, вы тип все выполнено и вы просто космос
                }
                pickRandomTask();
                return;
            }
            Random random = new Random();
            int newTaskNum = random.nextInt(availableTasks.size());
            Task currentTask = availableTasks.get(newTaskNum);
            Settings.setTaskId(context, currentTask.getUniqueId());
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

    @Override
    public void onClick(View v) {

    }
}
