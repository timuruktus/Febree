package ru.timuruktus.febree.ContentPart;

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
import android.widget.TextView;

import ru.timuruktus.febree.BaseFragment;
import ru.timuruktus.febree.LocalPart.Settings;
import ru.timuruktus.febree.LocalPart.Step;
import ru.timuruktus.febree.MainPart.MainActivity;
import ru.timuruktus.febree.MainPart.MainPresenter;
import ru.timuruktus.febree.ProjectUtils.CustomDialog1;
import ru.timuruktus.febree.R;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static ru.timuruktus.febree.LocalPart.StepCreator.BLOCK_COUNT;
import static ru.timuruktus.febree.LocalPart.StepCreator.BLOCK_STEPS_COUNT;

public class StepsFragment extends BaseFragment{


    public View rootView;
    private ImageView[][] taskIcons = new ImageView[BLOCK_COUNT][BLOCK_STEPS_COUNT];
    private TextView[][] taskNames = new TextView[BLOCK_COUNT][BLOCK_STEPS_COUNT];
    private Context context;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rootView =
                inflater.inflate(R.layout.blocks_fragment, container, false);

        initAllViews();
        MainActivity.showSplashScreen();

        context = rootView.getContext();

        stepQuery()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        step -> setImageAndText(step, context),
                        throwable -> Log.d("mytag", "StepsFragment.onCreateView() error =" + throwable),
                        MainActivity::hideSplashScreen);

        return rootView;
    }

    //TODO: Показывать диалог с загрузкой и надписью "Подождите, загружаем Ваши задания" и загружать задания по сети
    //TODO: СНАЧАЛА ПУСТЬ ЗАДАНИЯ СОХРАНЯЮТСЯ В БД, ПОТОМ МБ БУДЕМ БРАТЬ ТОЛЬКО ИЗ СЕТИ И БД СТАНЕТ ПЛАТНОЙ

    private void setImageAndText(Step step, Context context){
        int block = step.getBlock();
        int stepInBlock = step.getIdInBlock();
        taskIcons[block][stepInBlock].setImageResource(step.getFullPath(context));
        Drawable drawable = taskIcons[block][stepInBlock].getDrawable();
        if(drawable instanceof Animatable){
            ((Animatable) drawable).start();
        }
        taskNames[block][stepInBlock].setText(step.getName());

    }

    @Override
    public void onResume() {
        super.onResume();
        String title = context.getResources().getString(R.string.steps_fragment);
        MainPresenter.changeToolbarTitle(title);
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

        for(int block = 0; block < BLOCK_COUNT; block++){
            for(int step = 0; step < BLOCK_STEPS_COUNT; step++){
                taskIcons[block][step].setOnClickListener(getImageListener(block, step));
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
    private View.OnClickListener getImageListener(int block, int step){
        return v -> showTasks(block, step);
    }

    private void showTasks(int block, int step){
        if(block == 0 && step == 0 && Settings.isFirstOpenedStep()){
            String firstText = context.getResources().getString(R.string.first_step_opening_first_text);
            String secondText = context.getResources().getString(R.string.first_step_opening_second_text);
            String title = context.getResources().getString(R.string.first_step_opening_title);
            String buttonText = context.getResources().getString(R.string.first_step_opening_button_text);
            int titleColor = context.getResources().getColor(R.color.first_step_opening_title_color);
            int secondTextColor = context.getResources().getColor(R.color.first_step_opening_second_text_color);
            CustomDialog1 dialog = new CustomDialog1();
            dialog.buildDialog(context)
                    .setTitle(title)
                    .setFirstText(firstText)
                    .setSecondText(secondText)
                    .setButtonText(buttonText)
                    .setTitleColor(titleColor)
                    .setSecondTextColor(secondTextColor)
                    .setOnClickListener(v -> {
                        Settings.setFirstOpenedStep(false);
                        showTasks(block, step);
                        dialog.dismiss();
                    })
                    .show();
        }
    }

    @Override
    public void setTypefaces(){

    }


}
