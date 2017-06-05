package ru.timuruktus.febree.DonatePart;

import android.view.View;

import java.util.HashMap;

import ru.timuruktus.febree.DonatePart.Interfaces.BaseDonateModel;
import ru.timuruktus.febree.DonatePart.Interfaces.BaseDonatePresenter;
import ru.timuruktus.febree.DonatePart.Interfaces.BaseDonateView;
import ru.timuruktus.febree.LocalPart.Settings;
import ru.timuruktus.febree.MainPart.MainActivity;
import ru.timuruktus.febree.ProjectUtils.CustomDialog1;
import ru.timuruktus.febree.R;

import static ru.timuruktus.febree.BaseModel.BUTTON_TEXT;
import static ru.timuruktus.febree.BaseModel.FIRST_TEXT;
import static ru.timuruktus.febree.BaseModel.SECOND_TEXT;
import static ru.timuruktus.febree.BaseModel.SECOND_TEXT_COLOR;
import static ru.timuruktus.febree.BaseModel.TITLE_COLOR;
import static ru.timuruktus.febree.BaseModel.TITLE_TEXT;

public class DonatePresenter implements BaseDonatePresenter {

    private BaseDonateView view;
    private BaseDonateModel model;

    DonatePresenter(BaseDonateView view) {
        this.view = view;
        model = new DonateModel(this);
    }


    @Override
    public View.OnClickListener getBlocksListener() {
        return v -> {
            int id = v.getId();
            if(id == R.id.donateBlock){
                openBuyingDialog();
            }
        };
    }

    private void openBuyingDialog(){
        HashMap<String, String> textForDialog = model.getTextFor1Dialog();
        HashMap<String, Integer> colorsForDialog = model.getColorsFor1Dialog();
        buildBuyingDialog(textForDialog, colorsForDialog);
    }

    public void buildBuyingDialog(HashMap<String, String> textForDialog,
                                  HashMap<String, Integer> colorsForDialog){
        CustomDialog1 dialog = new CustomDialog1();
        dialog.buildDialog(MainActivity.getInstance())
                .setTitle(textForDialog.get(TITLE_TEXT))
                .setFirstText(textForDialog.get(FIRST_TEXT))
                .setButtonText(textForDialog.get(BUTTON_TEXT))
                .setTitleColor(colorsForDialog.get(TITLE_COLOR))
                .setDismissOnClick(true)
                .setOnClickListener(v -> {
                    MainActivity.bp.subscribe(MainActivity.getInstance(), "premium_account_1_month");
                    dialog.dismiss();
                })
                .show();
    }
}
