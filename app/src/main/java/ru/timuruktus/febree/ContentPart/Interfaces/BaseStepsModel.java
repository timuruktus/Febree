package ru.timuruktus.febree.ContentPart.Interfaces;

import java.util.HashMap;

import ru.timuruktus.febree.LocalPart.Step;
import rx.Observable;

public interface BaseStepsModel {

    Observable<Step> stepsQuery();
    Step getStepByNum(int blockNum, int stepNum);
    HashMap<String, String> getTextFor1Dialog();
    HashMap<String, Integer> getColorsFor1Dialog();
    HashMap<String, String> getTextForLoadingDialog();
    HashMap<String, Integer> getColorsForLoadingDialog();
}
