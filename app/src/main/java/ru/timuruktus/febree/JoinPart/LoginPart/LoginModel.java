package ru.timuruktus.febree.JoinPart.LoginPart;

import android.content.Context;
import android.content.res.Resources;

import java.util.HashMap;

import ru.timuruktus.febree.JoinPart.LoginPart.Interfaces.BaseLoginModel;
import ru.timuruktus.febree.JoinPart.LoginPart.Interfaces.BaseLoginPresenter;
import ru.timuruktus.febree.MainPart.MainActivity;
import ru.timuruktus.febree.R;

import static ru.timuruktus.febree.BaseModel.FIRST_TEXT;
import static ru.timuruktus.febree.BaseModel.SECOND_TEXT;
import static ru.timuruktus.febree.BaseModel.TITLE_COLOR;
import static ru.timuruktus.febree.BaseModel.TITLE_TEXT;

public class LoginModel implements BaseLoginModel {

    private BaseLoginPresenter presenter;

    public LoginModel(BaseLoginPresenter presenter){
        this.presenter = presenter;
    }


    @Override
    public HashMap<String, String> getTextForLoadingDialog() {
        Context context = MainActivity.getInstance();
        Resources resources = context.getResources();
        HashMap<String, String> map = new HashMap<>();
        map.put(FIRST_TEXT, resources.getString(R.string.login_joining_text1));
        map.put(TITLE_TEXT, resources.getString(R.string.login_joining_title));
        map.put(SECOND_TEXT, resources.getString(R.string.login_joining_text2));
        return map;
    }

    @Override
    public HashMap<String, Integer> getColorsForLoadingDialog() {
        Context context = MainActivity.getInstance();
        Resources resources = context.getResources();
        HashMap<String, Integer> map = new HashMap<>();
        map.put(TITLE_COLOR, resources.getColor(R.color.login_joining_title_color));
        return map;
    }
}
