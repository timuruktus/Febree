package ru.timuruktus.febree.DonatePart;

import android.content.Context;
import android.content.res.Resources;

import java.util.HashMap;

import ru.timuruktus.febree.DonatePart.Interfaces.BaseDonateModel;
import ru.timuruktus.febree.DonatePart.Interfaces.BaseDonatePresenter;
import ru.timuruktus.febree.DonatePart.Interfaces.BaseDonateView;
import ru.timuruktus.febree.MainPart.MainActivity;
import ru.timuruktus.febree.R;

import static ru.timuruktus.febree.BaseModel.BUTTON_TEXT;
import static ru.timuruktus.febree.BaseModel.FIRST_TEXT;
import static ru.timuruktus.febree.BaseModel.SECOND_TEXT;
import static ru.timuruktus.febree.BaseModel.SECOND_TEXT_COLOR;
import static ru.timuruktus.febree.BaseModel.TITLE_COLOR;
import static ru.timuruktus.febree.BaseModel.TITLE_TEXT;

public class DonateModel implements BaseDonateModel {

    private BaseDonatePresenter presenter;

    DonateModel(BaseDonatePresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public HashMap<String, String> getTextFor1Dialog() {
        Context context = MainActivity.getInstance();
        Resources resources = context.getResources();
        HashMap<String, String> map = new HashMap<>();
        map.put(FIRST_TEXT, resources.getString(R.string.donate_buy_block_1_text));
        map.put(TITLE_TEXT, resources.getString(R.string.donate_buy_block_1_title));
        map.put(BUTTON_TEXT, resources.getString(R.string.donate_buy_block_1_button));
        return map;
    }

    @Override
    public HashMap<String, Integer> getColorsFor1Dialog() {
        Context context = MainActivity.getInstance();
        Resources resources = context.getResources();
        HashMap<String, Integer> map = new HashMap<>();
        map.put(TITLE_COLOR, resources.getColor(R.color.donate_buy_block_1_title));
        return map;
    }
}
