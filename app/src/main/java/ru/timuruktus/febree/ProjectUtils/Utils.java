package ru.timuruktus.febree.ProjectUtils;

import android.content.Context;

public class Utils {

    private static int convertDpToPx(int pixels, Context context){
        float dp = context.getResources().getDisplayMetrics().density;
        return (int) dp * pixels;
    }
}
