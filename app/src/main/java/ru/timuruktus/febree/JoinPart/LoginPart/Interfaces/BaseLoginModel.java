package ru.timuruktus.febree.JoinPart.LoginPart.Interfaces;

import java.util.HashMap;

public interface BaseLoginModel {

    HashMap<String, String> getTextForLoadingDialog();
    HashMap<String, Integer> getColorsForLoadingDialog();
}
