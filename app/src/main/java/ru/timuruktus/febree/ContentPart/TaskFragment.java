package ru.timuruktus.febree.ContentPart;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ru.timuruktus.febree.BaseFragment;
import ru.timuruktus.febree.LocalPart.Step;
import ru.timuruktus.febree.MainPart.MainActivity;
import ru.timuruktus.febree.R;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class TaskFragment extends BaseFragment implements View.OnClickListener {


    public View rootView;
    private ImageView[][] taskIcons;
    private TextView[][] taskNames;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rootView =
                inflater.inflate(R.layout.task_fragment, container, false);

        initAllViews();
        MainActivity.showSplashScreen();



        stepQuery()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        step -> {
                            setImagesAndTexts(step);
                        },
                        throwable -> Log.d("mytag", "TaskFragment.onCreateView() error =" + throwable),
                        MainActivity::hideSplashScreen);

        return rootView;
    }

    //TODO: отдельный метод, сам устанавливающий иконки. Передавать в него только Step

    private void setImagesAndTexts(Step step){
        Log.d("mytag", "Step block = " + step.getBlock() +
                " and step number in block = " + step.getIdInBlock());


    }

    private Observable<Step> stepQuery(){
        return Observable.from(Step.listAll(Step.class))
                .subscribeOn(Schedulers.io());
    }

    private void initAllViews(){
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


    @Override
    public void onClick(View v) {
        int id = v.getId();

    }

    @Override
    public void setTypefaces(){

    }


}
