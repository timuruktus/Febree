package ru.timuruktus.febree.WelcomePart;

import android.animation.ValueAnimator;
import android.app.Fragment;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.layer_net.stepindicator.StepIndicator;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import ru.timuruktus.febree.BaseEvent;
import ru.timuruktus.febree.BaseFragment;
import ru.timuruktus.febree.ContentPart.TaskFragment;
import ru.timuruktus.febree.EventCallbackListener;
import ru.timuruktus.febree.LocalPart.Settings;
import ru.timuruktus.febree.MainPart.EChangeFragment;
import ru.timuruktus.febree.R;

public class WelcomeFragment extends BaseFragment implements View.OnClickListener{


    public View rootView;
    String[] questions;
    ArrayList<Integer> answers = new ArrayList<>();
    public TextView question;
    public Button agreeBut, disagreeBut;
    public ImageView welcomeBackground;
    private int counter = 0;
    StepIndicator stepIndicator;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rootView =
                inflater.inflate(R.layout.welcome_fragment, container, false);

        welcomeBackground = (ImageView) rootView.findViewById(R.id.welcomeBackground);
        questions = getResources().getStringArray(R.array.questions);
        question = (TextView) rootView.findViewById(R.id.question);
        question.setTypeface(Typeface.createFromAsset(rootView.getContext().getAssets(),
                "RobotoCondensed-Light.ttf"));
        agreeBut = (Button) rootView.findViewById(R.id.agreeBut);
        agreeBut.setOnClickListener(this);
        disagreeBut = (Button) rootView.findViewById(R.id.disagreeBut);
        disagreeBut.setOnClickListener(this);
        question.setText(questions[counter]);
        stepIndicator = (StepIndicator) rootView.findViewById(R.id.step_indicator);
        stepIndicator.setClickable(false);


        return rootView;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        counter++;
        if(id == R.id.agreeBut){
            answers.add(1);
        }else if(id == R.id.disagreeBut){
            answers.add(0);
        }
        if(counter <= 9) {
            question.setText(questions[counter]);
            stepIndicator.setCurrentStepPosition(counter);
        }else{
            Settings.setFirstOpened(rootView.getContext(), false);
            int totalPoints = 0;
            for(int i : answers){
                totalPoints += i;
            }
            if(totalPoints > 7){
                Settings.setLevel(rootView.getContext(), Settings.MEDIUM_LEVEL);
            }else{
                Settings.setLevel(rootView.getContext(), Settings.LOW_LEVEL);
            }
            EventBus.getDefault().post(new EChangeFragment(new TaskFragment(), false, false, false));
        }
    }

    @Override
    public void eventCallback(BaseEvent event) {

    }
}
