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


import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import devlight.io.library.ArcProgressStackView;
import ru.timuruktus.febree.BaseFragment;
import ru.timuruktus.febree.LocalPart.Settings;
import ru.timuruktus.febree.R;

import static ru.timuruktus.febree.ProjectUtils.Utils.boldItalicTypeface;
import static ru.timuruktus.febree.ProjectUtils.Utils.boldTypeFace;
import static ru.timuruktus.febree.ProjectUtils.Utils.italicTypeface;
import static ru.timuruktus.febree.ProjectUtils.Utils.usualTypeface;


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
    AdView adView_1;
    private VisualisationPresenter presenter;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rootView =
                inflater.inflate(R.layout.visualisation_fragment, container, false);
        presenter = new VisualisationPresenter(this);
        context = rootView.getContext();

        arcProgressStackView = (ArcProgressStackView) rootView.findViewById(R.id.apsv);

        ArrayList<ArcProgressStackView.Model> models = new ArrayList<>();
        colorEasy = ContextCompat.getColor(context, R.color.taskDifficultyEasy);
        colorMedium = ContextCompat.getColor(context, R.color.taskDifficultyMedium);
        colorHard = ContextCompat.getColor(context, R.color.taskDifficultyHard);
        int backColor = ContextCompat.getColor(context, R.color.graphBackground);

        Resources resources = rootView.getResources();

        setPercentages();

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
        completedTaskCount = (TextView) rootView.findViewById(R.id.completedTasksCount);
        skippedTaskCount = (TextView) rootView.findViewById(R.id.skippedTasksCount);
        currentPoints.setText(Settings.getPoints() + "");
        configureColor();
        completedTaskCount.setText(Settings.getLevelsDone() + "");
        skippedTaskCount.setText(Settings.getLevelsSkipped() + "");
        initAdView();
        setTypefaces();

        return rootView;
    }

    private void initAdView(){
        adView_1 = (AdView) rootView.findViewById(R.id.visualisationAdView_1);
        /*
        NOTE: Delete\Place all "//" under tat line to show up\hide advertisement
         */
        EventBus.getDefault().post(new EInitAdmob(adView_1));
    }

    @Override
    final public void onDestroy(){
        super.onDestroy();
        presenter.unregisterListener();
    }

    private void configureColor(){
        long taskDifficulty = Settings.getLevel();
        if(taskDifficulty == Settings.HARD_LEVEL){
            currentPoints.setTextColor(colorHard);
        }else if(taskDifficulty == Settings.MEDIUM_LEVEL){
            currentPoints.setTextColor(colorMedium);
        }else{
            currentPoints.setTextColor(colorEasy);
        }
    }

    private void setPercentages(){
        if(Settings.getLevel() == Settings.EASY_LEVEL){
            easyPercentage = Settings.getPoints() * 100 / Settings.getCurrentLimit();
            mediumPercentage = 0;
            hardPercentage = 0;
        }else if(Settings.getLevel() == Settings.MEDIUM_LEVEL){
            easyPercentage = 100;
            mediumPercentage = Settings.getPoints() * 100 / Settings.getCurrentLimit();;
            hardPercentage = 0;
        }else{
            easyPercentage = 100;
            mediumPercentage = 100;
            hardPercentage = Settings.getPoints() * 100 / Settings.getCurrentLimit();
        }
    }

    @Override
    public void setTypefaces() {
        TextView currentPointsTextView = (TextView) rootView.findViewById(R.id.currentPointsTextView);
        TextView tasksTextView = (TextView) rootView.findViewById(R.id.tasksTextView);
        TextView completedTasksCountTextView = (TextView) rootView.findViewById(R.id.completedTasksCountTextView);
        TextView skippedTasksCountTextView = (TextView) rootView.findViewById(R.id.skippedTasksCountTextView);
        TextView completedTasksCount = (TextView) rootView.findViewById(R.id.completedTasksCount);
        TextView skippedTasksCount = (TextView) rootView.findViewById(R.id.skippedTasksCount);
        TextView doneTasksVisualisation = (TextView) rootView.findViewById(R.id.doneTasksVisualisation);
        // TODO: load right fonts
        currentPoints.setTypeface(boldItalicTypeface);
        completedTaskCount.setTypeface(boldItalicTypeface);
        skippedTaskCount.setTypeface(boldItalicTypeface);
        currentPointsTextView.setTypeface(boldTypeFace);
        tasksTextView.setTypeface(boldTypeFace);
        completedTasksCountTextView.setTypeface(boldTypeFace);
        skippedTasksCountTextView.setTypeface(boldTypeFace);
        completedTasksCount.setTypeface(usualTypeface);
        skippedTasksCount.setTypeface(usualTypeface);
        doneTasksVisualisation.setTypeface(boldTypeFace);
    }
}
