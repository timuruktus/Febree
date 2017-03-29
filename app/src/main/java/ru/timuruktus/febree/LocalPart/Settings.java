package ru.timuruktus.febree.LocalPart;

import android.content.Context;
import android.content.SharedPreferences;

import ru.timuruktus.febree.BaseEvent;
import ru.timuruktus.febree.BaseModel;

public class Settings implements BaseModel {

    private static final String APP_PREFERENCES = "mysettings";
    private static final String APP_PREFERENCES_FIRST_OPENED = "firstOpen";
    private static final String APP_PREFERENCES_LEVEL = "level";
    private static final String APP_PREFERENCES_POINTS = "points";
    private static SharedPreferences settings;
    public static final int LOW_LEVEL = 0;
    public static final int MEDIUM_LEVEL = 1;
    public static final int HIGH_LEVEL = 2;

    public static boolean isFirstOpened(Context context){
        return getBooleanValue(context, APP_PREFERENCES_FIRST_OPENED, true);
    }

    public static void setFirstOpened(Context context, boolean isFirstOpened){
        writeBooleanValue(context, APP_PREFERENCES_FIRST_OPENED, isFirstOpened);
    }

    public static void setLevel(Context context, int level){
        writeIntValue(context, APP_PREFERENCES_LEVEL, level);
    }

    public static int getLevel(Context context){
        return getIntValue(context, APP_PREFERENCES_LEVEL);
    }

    public static int getPoints(Context context){
        return getIntValue(context, APP_PREFERENCES_POINTS);
    }

    public static void setPoints(Context context, int points){
        writeIntValue(context, APP_PREFERENCES_POINTS, points);
    }

    private static void writeStringValue(String path, String value, Context context){
        settings = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(path, value);
        editor.apply();
    }

    private static void writeIntValue(Context context, String path, int value){
        settings = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(path, value);
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

    private static int getIntValue(Context context, String path){
        return getIntValue(context, path, 0);
    }

    private static int getIntValue(Context context, String path, int defaultValue){
        settings = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        return settings.getInt(path, defaultValue);
    }

    private static boolean getBooleanValue(Context context, String path){
        return getBooleanValue(context, path, false);
    }

    private static boolean getBooleanValue(Context context, String path, boolean defaultValue){
        settings = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        return settings.getBoolean(path, defaultValue);
    }

    @Override
    public void eventCallback(BaseEvent e) {

    }
}
