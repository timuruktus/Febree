package ru.timuruktus.febree.ProjectUtils;

import android.content.Context;

import java.io.IOException;

public class Utils {

    private static int convertDpToPx(int pixels, Context context){
        float dp = context.getResources().getDisplayMetrics().density;
        return (int) dp * pixels;
    }

    public static boolean isOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        } catch (IOException e){ e.printStackTrace(); }
        catch (InterruptedException e){ e.printStackTrace(); }

        return false;
    }
}
