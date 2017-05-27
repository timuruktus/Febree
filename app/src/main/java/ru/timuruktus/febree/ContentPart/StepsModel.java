package ru.timuruktus.febree.ContentPart;

import android.content.Context;
import android.content.res.Resources;

import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.ArrayList;
import java.util.HashMap;

import ru.timuruktus.febree.ContentPart.Interfaces.BaseStepsModel;
import ru.timuruktus.febree.LocalPart.Step;
import ru.timuruktus.febree.MainPart.MainActivity;
import ru.timuruktus.febree.R;
import rx.Observable;
import rx.schedulers.Schedulers;

public class StepsModel implements BaseStepsModel {

    public static final String FIRST_TEXT = "firstText";
    public static final String SECOND_TEXT = "secondText";
    public static final String TITLE_TEXT = "titleText";
    public static final String BUTTON_TEXT = "buttonText";
    public static final String TITLE_COLOR = "titleColor";
    public static final String SECOND_TEXT_COLOR = "secondTextColor";

    public Observable<Step> stepsQuery(){
        return Observable.from(Step.listAll(Step.class))
                .subscribeOn(Schedulers.io());
    }

    public Step getStepByNum(int blockNum, int stepNum){
        ArrayList<Step> steps = (ArrayList<Step>) Select.from(Step.class)
                .where(Condition.prop("block").eq(blockNum))
                .where(Condition.prop("id_in_block").eq(stepNum))
                .list();
        return steps.get(0);
    }

    @Override
    public HashMap<String, String> getTextFor1Dialog() {
        Context context = MainActivity.getContext();
        Resources resources = context.getResources();
        HashMap<String, String> map = new HashMap<>();
        map.put(FIRST_TEXT, resources.getString(R.string.first_step_opening_first_text));
        map.put(SECOND_TEXT, resources.getString(R.string.first_step_opening_second_text));
        map.put(TITLE_TEXT, resources.getString(R.string.first_step_opening_title));
        map.put(BUTTON_TEXT, resources.getString(R.string.first_step_opening_button_text));
        return map;
    }

    @Override
    public HashMap<String, Integer> getColorsFor1Dialog() {
        Context context = MainActivity.getContext();
        Resources resources = context.getResources();
        HashMap<String, Integer> map = new HashMap<>();
        map.put(TITLE_COLOR, resources.getColor(R.color.first_step_opening_title_color));
        map.put(SECOND_TEXT_COLOR, resources.getColor(R.color.first_step_opening_second_text_color));
        return map;
    }

    @Override
    public HashMap<String, String> getTextForLoadingDialog() {
        Context context = MainActivity.getContext();
        Resources resources = context.getResources();
        HashMap<String, String> map = new HashMap<>();
        map.put(FIRST_TEXT, resources.getString(R.string.steps_loading_text));
        map.put(TITLE_TEXT, resources.getString(R.string.steps_loading_title));
        return map;
    }

    @Override
    public HashMap<String, Integer> getColorsForLoadingDialog() {
        Context context = MainActivity.getContext();
        Resources resources = context.getResources();
        HashMap<String, Integer> map = new HashMap<>();
        map.put(TITLE_COLOR, resources.getColor(R.color.steps_loading_dialog_title_color));
        return map;
    }


}
