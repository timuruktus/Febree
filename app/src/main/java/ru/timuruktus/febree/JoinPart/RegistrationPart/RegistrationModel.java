package ru.timuruktus.febree.JoinPart.RegistrationPart;

import android.content.Context;
import android.content.res.Resources;

import java.util.HashMap;

import ru.timuruktus.febree.JoinPart.RegistrationPart.Interfaces.BaseRegistrationModel;
import ru.timuruktus.febree.JoinPart.RegistrationPart.Interfaces.BaseRegistrationPresenter;
import ru.timuruktus.febree.MainPart.MainActivity;
import ru.timuruktus.febree.R;

public class RegistrationModel implements BaseRegistrationModel {

    BaseRegistrationPresenter presenter;

    public RegistrationModel(BaseRegistrationPresenter presenter){
        this.presenter = presenter;
    }

    @Override
    public HashMap<String, String> getTextForLoadingDialog() {
        Context context = MainActivity.getInstance();
        Resources resources = context.getResources();
        HashMap<String, String> map = new HashMap<>();
        map.put(FIRST_TEXT, resources.getString(R.string.reg_registration_text1));
        map.put(TITLE_TEXT, resources.getString(R.string.reg_registration_title));
        map.put(SECOND_TEXT, resources.getString(R.string.reg_registration_text2));
        return map;
    }

    @Override
    public HashMap<String, Integer> getColorsForLoadingDialog() {
        Context context = MainActivity.getInstance();
        Resources resources = context.getResources();
        HashMap<String, Integer> map = new HashMap<>();
        map.put(TITLE_COLOR, resources.getColor(R.color.reg_registration_title_color));
        return map;
    }
}
