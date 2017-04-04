package ru.timuruktus.febree.DoneTasksPart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ru.timuruktus.febree.LocalPart.Settings;
import ru.timuruktus.febree.LocalPart.Task;
import ru.timuruktus.febree.R;

public class DoneTasksAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Task> tasks;

    DoneTasksAdapter(Context context, ArrayList<Task> tasks) {
        this.context = context;
        this.tasks = tasks;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return tasks.size();
    }

    @Override
    public Object getItem(int position) {
        return tasks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.done_task, parent, false);
        }

        Task task = getTask(position);

        TextView taskText = (TextView) view.findViewById(R.id.doneTaskText);
        ImageView difficultyColor = (ImageView) view.findViewById(R.id.difficultyColor);
        int taskDifficulty = task.getLevel();
        taskText.setText(task.getText());
        if(taskDifficulty == Settings.HARD_LEVEL){
            difficultyColor.setBackgroundResource(R.color.taskDifficultyHard);
        }else if(taskDifficulty == Settings.MEDIUM_LEVEL){
            difficultyColor.setBackgroundResource(R.color.taskDifficultyMedium);
        }else{
            difficultyColor.setBackgroundResource(R.color.taskDifficultyEasy);
        }


        return view;
    }

    // товар по позиции
    Task getTask(int position) {
        return ((Task) getItem(position));
    }

}
