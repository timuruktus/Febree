package ru.timuruktus.febree.ProjectUtils;

import android.content.Context;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Utils {

    public static final long WEEK_IN_SECONDS = 604800;
    public static final long DAY_IN_SECOND = 86400;

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

    public static long getCurrentTimeInSeconds(){
        long timeMillis = System.currentTimeMillis();
        long timeSeconds = TimeUnit.MILLISECONDS.toSeconds(timeMillis);
        return timeSeconds;
    }
}
