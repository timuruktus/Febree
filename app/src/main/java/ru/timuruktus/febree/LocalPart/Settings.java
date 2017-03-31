package ru.timuruktus.febree.LocalPart;

import android.content.Context;
import android.content.SharedPreferences;

import ru.timuruktus.febree.BaseModel;
import ru.timuruktus.febree.ProjectUtils.Utils;

public class Settings implements BaseModel {

    private static final String APP_PREFERENCES = "mySettings";
    private static final String APP_PREFERENCES_FIRST_OPENED = "firstOpen";
    private static final String APP_PREFERENCES_LEVEL = "level";
    private static final String APP_PREFERENCES_POINTS = "points";
    private static final String APP_PREFERENCES_LEVELS_DONE = "levelsDone";
    private static final String APP_PREFERENCES_LEVELS_SKIPPED = "levelsSkipped";
    private static final String APP_PREFERENCES_CURRENT_TASK_ID = "currentTaskId";
    private static final String APP_PREFERENCES_LAST_TASK_TIME = "lastTaskTime";
    private static SharedPreferences settings;

    public static final long LOW_LEVEL = 0;
    public static final long MEDIUM_LEVEL = 1;
    public static final long HIGH_LEVEL = 2;

    public static final long LOW_LIMIT = 199;
    public static final long MEDIUM_LIMIT = 399;
    public static final long HIGH_LIMIT = 999;


    /*
    UNDER THIS LINE- FIRST APP OPEN METHODS
     */

    public static void setFirstOpened(Context context, boolean isFirstOpened){
        writeBooleanValue(context, APP_PREFERENCES_FIRST_OPENED, isFirstOpened);
    }

    public static boolean isFirstOpened(Context context){
        return getBooleanValue(context, APP_PREFERENCES_FIRST_OPENED, true);
    }

    /*
    UNDER THIS LINE- SET AND CHANGING LEVELS METHODS
     */

    public static void setLevel(Context context, long level){
        writeIntValue(context, APP_PREFERENCES_LEVEL, level);
    }

    public static void increaseLevel(Context context){
        long level = getLevel(context);
        writeIntValue(context, APP_PREFERENCES_LEVEL, level + 1);
    }

    public static long getLevel(Context context){
        return getLongValue(context, APP_PREFERENCES_LEVEL);
    }


    /*
    UNDER THIS LINE- USER POINTS METHODS
     */

    public static void setPoints(Context context, long points){
        writeIntValue(context, APP_PREFERENCES_POINTS, points);
    }

    public static void changePoints(Context context, long change){
        long points = getPoints(context);
        setPoints(context, points + change);
    }

    public static long getCurrentLimit(Context context){
        long currentLevel = getLevel(context);
        if(currentLevel == HIGH_LEVEL){
            return HIGH_LIMIT;
        }else if(currentLevel == MEDIUM_LEVEL){
            return MEDIUM_LIMIT;
        }else{
            return LOW_LIMIT;
        }
    }

    public static long getPoints(Context context){
        return getLongValue(context, APP_PREFERENCES_POINTS);
    }



    /*
    UNDER THIS LINE- DONE LEVELS METHODS
     */

    public static void setLevelsDone(Context context, long levels){
        writeIntValue(context, APP_PREFERENCES_LEVELS_DONE, levels);
    }

    public static void incrementLevelsDone(Context context){
        long level = getLevelsDone(context);
        writeIntValue(context, APP_PREFERENCES_LEVELS_DONE, ++level);
    }

    public static long getLevelsDone(Context context){
        return getLongValue(context, APP_PREFERENCES_LEVELS_DONE);
    }

    /*
    UNDER THIS LINE- SKIPPED LEVELS METHODS
     */

    public static void setLevelsSkipped(Context context, long levels){
        writeIntValue(context, APP_PREFERENCES_LEVELS_SKIPPED, levels);
    }

    public static void incrementLevelsSkipped(Context context){
        long level = getLevelsSkipped(context);
        writeIntValue(context, APP_PREFERENCES_LEVELS_SKIPPED, ++level);
    }

    public static long getLevelsSkipped(Context context){
        return getLongValue(context, APP_PREFERENCES_LEVELS_SKIPPED);
    }

    /*
    UNDER THIS LINE- TASK ID METHODS
     */

    public static void setTaskId(Context context, long uniqueId){
        writeIntValue(context, APP_PREFERENCES_CURRENT_TASK_ID, uniqueId);
    }

    public static long getTaskId(Context context){
        return getLongValue(context, APP_PREFERENCES_CURRENT_TASK_ID);
    }

    /*
    UNDER THIS LINE- TASKS TIME METHODS
     */

    public static void setLastTaskTime(Context context, long sec){
        writeIntValue(context, APP_PREFERENCES_LAST_TASK_TIME, sec);
    }

    public static long getTimeBetweenLastAndCurrentTask(Context context){
        long lastTaskTime = getLastTaskTime(context);
        long answer = Utils.getCurrentTimeInSeconds() - lastTaskTime;
        if(answer <= 0){
            return 704800;
        }else{
            return answer;
        }
    }

    public static long getLastTaskTime(Context context){
        return getLongValue(context, APP_PREFERENCES_LAST_TASK_TIME);
    }

    /*
    UNDER THIS LINE- SUPPORTING METHODS. USE ONLY IS THIS CLASS
     */

    private static void writeStringValue(String path, String value, Context context){
        settings = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(path, value);
        editor.apply();
    }

    private static void writeIntValue(Context context, String path, long value){
        settings = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putLong(path, value);
        editor.apply();
    }

    private static void writeBooleanValue(Context context, String path, boolean value){
        settings = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(path, value);
        editor.apply();
    }

    private static String getStringValue(Context context, String path){
        return getStringValue(context, path, "");
    }

    private static String getStringValue(Context context, String path, String defaultValue){
        settings = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        return settings.getString(path, defaultValue);
    }

    private static long getLongValue(Context context, String path){
        return getLongValue(context, path, 0);
    }

    private static long getLongValue(Context context, String path, long defaultValue){
        settings = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        return settings.getLong(path, defaultValue);
    }

    private static boolean getBooleanValue(Context context, String path){
        return getBooleanValue(context, path, false);
    }

    private static boolean getBooleanValue(Context context, String path, boolean defaultValue){
        settings = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        return settings.getBoolean(path, defaultValue);
    }

}
