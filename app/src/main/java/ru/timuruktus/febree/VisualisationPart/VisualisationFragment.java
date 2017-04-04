package ru.timuruktus.febree.VisualisationPart;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.ArrayList;

import devlight.io.library.ArcProgressStackView;
import ru.timuruktus.febree.BaseFragment;
import ru.timuruktus.febree.LocalPart.Settings;
import ru.timuruktus.febree.R;


public class VisualisationFragment extends BaseFragment {

    public View rootView;
    private Context context;
    private ArcProgressStackView arcProgressStackView;
    private TextView currentPoints, completedTaskCount, skippedTaskCount;
    private int colorEasy;
    private int colorMedium;
    private int colorHard;
    private long easyPercentage;
    private long mediumPercentage;
    private long hardPercentage;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rootView =
                inflater.inflate(R.layout.visualisation_fragment, container, false);
        context = rootView.getContext();

        arcProgressStackView = (ArcProgressStackView) rootView.findViewById(R.id.apsv);

        ArrayList<ArcProgressStackView.Model> models = new ArrayList<>();
        colorEasy = ContextCompat.getColor(context, R.color.taskDifficultyEasy);
        colorMedium = ContextCompat.getColor(context, R.color.taskDifficultyMedium);
        colorHard = ContextCompat.getColor(context, R.color.taskDifficultyHard);
        int backColor = ContextCompat.getColor(context, R.color.graphBackground);

        Resources resources = rootView.getResources();

        setPercantages();

        models.add(new ArcProgressStackView.Model(resources.getString(R.string.hard_task),
                hardPercentage,
                backColor,
                colorHard));
        models.add(new ArcProgressStackView.Model(resources.getString(R.string.medium_task),
                mediumPercentage,
                backColor,
                colorMedium));
        models.add(new ArcProgressStackView.Model(resources.getString(R.string.easy_task),
                easyPercentage,
                backColor,
                colorEasy));


        arcProgressStackView.animateProgress();
        arcProgressStackView.setModels(models);


        currentPoints = (TextView) rootView.findViewById(R.id.currentPoints);
        completedTaskCount = (TextView) rootView.findViewById(R.id.completedTaskCount);
        skippedTaskCount = (TextView) rootView.findViewById(R.id.skippedTaskCount);
        currentPoints.setText(Settings.getPoints(context) + "");
        configureColor();
        completedTaskCount.setText(Settings.getLevelsDone(context) + "");
        skippedTaskCount.setText(Settings.getLevelsSkipped(context) + "");

        return rootView;
    }

    private void configureColor(){
        long taskDifficulty = Settings.getLevel(context);
        if(taskDifficulty == Settings.HARD_LEVEL){
            currentPoints.setTextColor(colorHard);
        }else if(taskDifficulty == Settings.MEDIUM_LEVEL){
            currentPoints.setTextColor(colorMedium);
        }else{
            currentPoints.setTextColor(colorEasy);
        }
    }

    private void setPercantages(){
        if(Settings.getLevel(context) == Settings.EASY_LEVEL){
            easyPercentage = Settings.getPoints(context) * 100 / Settings.getCurrentLimit(context);
            mediumPercentage = 0;
            hardPercentage = 0;
        }else if(Settings.getLevel(context) == Settings.MEDIUM_LEVEL){
            easyPercentage = 100;
            mediumPercentage = Settings.getPoints(context) * 100 / Settings.getCurrentLimit(context);;
            hardPercentage = 0;
        }else{
            easyPercentage = 100;
            mediumPercentage = 100;
            hardPercentage = Settings.getPoints(context) * 100 / Settings.getCurrentLimit(context);
        }
    }
}
