package ru.timuruktus.febree.JoinPart.RegistrationPart.Interfaces;

import java.util.HashMap;

import ru.timuruktus.febree.BaseModel;

public interface BaseRegistrationModel extends BaseModel {

    HashMap<String, String> getTextForLoadingDialog();
    HashMap<String, Integer> getColorsForLoadingDialog();
}
