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

import static ru.timuruktus.febree.ProjectUtils.Utils.*;

class DoneTasksAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private ArrayList<Task> tasks;

    DoneTasksAdapter(Context context, ArrayList<Task> tasks) {
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
        taskText.setTypeface(usualTypeface);
        TextView doneTaskEnd = (TextView) view.findViewById(R.id.doneTaskEnd);
        doneTaskEnd.setTypeface(blackTypeface);

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
        task = null;
        taskText = null;
        doneTaskEnd = null;
        difficultyColor = null;
        return view;
    }

    // товар по позиции
    private Task getTask(int position) {
        return ((Task) getItem(position));
    }

}
