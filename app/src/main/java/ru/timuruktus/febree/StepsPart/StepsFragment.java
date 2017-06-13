package ru.timuruktus.febree.StepsPart;

import android.app.Fragment;
import android.content.Context;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import ru.timuruktus.febree.StepsPart.Interfaces.BaseStepsFragment;
import ru.timuruktus.febree.StepsPart.Interfaces.BaseStepsPresenter;
import ru.timuruktus.febree.LocalPart.Step;
import ru.timuruktus.febree.MainPart.MainPresenter;
import ru.timuruktus.febree.R;


import static ru.timuruktus.febree.LocalPart.StepConfigurator.BLOCK_COUNT;
import static ru.timuruktus.febree.LocalPart.StepConfigurator.BLOCK_STEPS_COUNT;


public class StepsFragment extends Fragment implements BaseStepsFragment{


    public View rootView;
    private ImageView[][] taskIcons = new ImageView[BLOCK_COUNT][BLOCK_STEPS_COUNT];
    private TextView[][] taskNames = new TextView[BLOCK_COUNT][BLOCK_STEPS_COUNT];
    private Context context;
    private BaseStepsPresenter presenter;
    private ProgressBar levelbar;
    private static final int NAV_MENU_ID = R.id.nav_home;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rootView =
                inflater.inflate(R.layout.steps_fragment, container, false);
        Log.d("mytag", "Creating new fragment");
        context = rootView.getContext();
        presenter = new StepsPresenter(this);
        initAllViews();
        //MainActivity.showSplashScreen();
        presenter.onCreate();
        return rootView;
    }

    @Override
    public void setImageAndText(Step step){
        int blockNum = step.getBlock();
        int stepInBlockNum = step.getIdInBlock();
        taskIcons[blockNum][stepInBlockNum].setImageResource(step.getFullPath(context));
        Drawable drawable = taskIcons[blockNum][stepInBlockNum].getDrawable();
        if(drawable instanceof Animatable){
            ((Animatable) drawable).start();
        }
        taskNames[blockNum][stepInBlockNum].setText(step.getName());

    }

    @Override
    public void setLevelBarProgress(int progress) {
        levelbar.setProgress(progress);
    }

    @Override
    public void onResume() {
        super.onResume();
        String title = context.getResources().getString(R.string.steps_fragment);
        MainPresenter.changeToolbarTitle(title);
        MainPresenter.changeToolbarVisibility(MainPresenter.DONT_HIDE_TOOLBAR);
        MainPresenter.setCheckedItemInNavMenu(NAV_MENU_ID);
    }

    private void initAllViews(){
        levelbar = (ProgressBar) getActivity().findViewById(R.id.levelBar);

        taskIcons[0][0] = (ImageView) rootView.findViewById(R.id.step11);
        taskIcons[0][1] = (ImageView) rootView.findViewById(R.id.step12);
        taskIcons[0][2] = (ImageView) rootView.findViewById(R.id.step13);
        taskIcons[0][3] = (ImageView) rootView.findViewById(R.id.step14);
        taskIcons[0][4] = (ImageView) rootView.findViewById(R.id.step15);
        taskIcons[1][0] = (ImageView) rootView.findViewById(R.id.step21);
        taskIcons[1][1] = (ImageView) rootView.findViewById(R.id.step22);
        taskIcons[1][2] = (ImageView) rootView.findViewById(R.id.step23);
        taskIcons[1][3] = (ImageView) rootView.findViewById(R.id.step24);
        taskIcons[1][4] = (ImageView) rootView.findViewById(R.id.step25);

        for(int blockNum = 0; blockNum < BLOCK_COUNT; blockNum++){
            for(int stepNum = 0; stepNum < BLOCK_STEPS_COUNT; stepNum++){
                taskIcons[blockNum][stepNum].setOnClickListener(getImageListener(blockNum, stepNum));
            }
        }

        taskNames[0][0] = (TextView) rootView.findViewById(R.id.step11Name);
        taskNames[0][1] = (TextView) rootView.findViewById(R.id.step12Name);
        taskNames[0][2] = (TextView) rootView.findViewById(R.id.step13Name);
        taskNames[0][3] = (TextView) rootView.findViewById(R.id.step14Name);
        taskNames[0][4] = (TextView) rootView.findViewById(R.id.step15Name);
        taskNames[1][0] = (TextView) rootView.findViewById(R.id.step21Name);
        taskNames[1][1] = (TextView) rootView.findViewById(R.id.step22Name);
        taskNames[1][2] = (TextView) rootView.findViewById(R.id.step23Name);
        taskNames[1][3] = (TextView) rootView.findViewById(R.id.step24Name);
        taskNames[1][4] = (TextView) rootView.findViewById(R.id.step25Name);
    }

    @NonNull
    private View.OnClickListener getImageListener(int blockNum, int stepNum){
        return v -> presenter.onStepClick(blockNum, stepNum);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d("mytag", "onDestroy() in StepsFragment");
        //presenter.onDestroy();
        //presenter = null;
    }


}
