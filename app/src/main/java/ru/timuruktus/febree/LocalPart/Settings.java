package ru.timuruktus.febree.LocalPart;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.analytics.FirebaseAnalytics;

import ru.timuruktus.febree.BaseModel;
import ru.timuruktus.febree.ProjectUtils.Utils;

import static weborb.util.ThreadContext.context;

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

    public static final long EASY_LEVEL = 0;
    public static final long MEDIUM_LEVEL = 1;
    public static final long HARD_LEVEL = 2;

    public static final long EASY_LIMIT = 499;
    public static final long MEDIUM_LIMIT = 1499;
    public static final long HARD_LIMIT = 15000;

    public static void initSettings(Context context){
        settings = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
    }


    /*
    UNDER THIS LINE- FIRST APP OPEN METHODS
     */

    public static void setFirstOpened(boolean isFirstOpened){
        writeBooleanValue(APP_PREFERENCES_FIRST_OPENED, isFirstOpened);
    }

    public static boolean isFirstOpened(){
        return getBooleanValue(APP_PREFERENCES_FIRST_OPENED, true);
    }

    /*
    UNDER THIS LINE- SET AND CHANGING LEVELS METHODS
     */

    public static void setLevel(long level){
        writeLongValue(APP_PREFERENCES_LEVEL, level);
    }

    public static void increaseLevel(){
        long level = getLevel();
        writeLongValue(APP_PREFERENCES_LEVEL, ++level);
    }

    public static void decreaseLevel(){
        long level = getLevel();
        writeLongValue(APP_PREFERENCES_LEVEL, --level);
    }

    public static long getLevel(){
        return getLongValue(APP_PREFERENCES_LEVEL);
    }


    /*
    UNDER THIS LINE- USER POINTS METHODS
     */

    public static void setPoints(long points){
        writeLongValue(APP_PREFERENCES_POINTS, points);
    }

    public static void changePoints(long change){
        long points = getPoints();
        setPoints(points + change);
    }

    public static long getCurrentLimit(){
        long currentLevel = getLevel();
        if(currentLevel == HARD_LEVEL){
            return HARD_LIMIT;
        }else if(currentLevel == MEDIUM_LEVEL){
            return MEDIUM_LIMIT;
        }else{
            return EASY_LIMIT;
        }
    }

    public static long getPoints(){
        return getLongValue(APP_PREFERENCES_POINTS);
    }



    /*
    UNDER THIS LINE- DONE LEVELS METHODS
     */

    public static void setLevelsDone(long levels){
        writeLongValue(APP_PREFERENCES_LEVELS_DONE, levels);
    }

    public static void incrementLevelsDone(){
        long level = getLevelsDone();
        writeLongValue(APP_PREFERENCES_LEVELS_DONE, ++level);
    }

    public static long getLevelsDone(){
        return getLongValue(APP_PREFERENCES_LEVELS_DONE);
    }

    /*
    UNDER THIS LINE- SKIPPED LEVELS METHODS
     */

    public static void setLevelsSkipped(long levels){
        writeLongValue(APP_PREFERENCES_LEVELS_SKIPPED, levels);
    }

    public static void incrementLevelsSkipped(){
        long level = getLevelsSkipped();
        writeLongValue(APP_PREFERENCES_LEVELS_SKIPPED, ++level);
    }

    public static long getLevelsSkipped(){
        return getLongValue(APP_PREFERENCES_LEVELS_SKIPPED);
    }

    /*
    UNDER THIS LINE- TASK ID METHODS
     */

    public static void setCurrentTaskId(long uniqueId){
        writeLongValue(APP_PREFERENCES_CURRENT_TASK_ID, uniqueId);
    }

    public static long getCurrentTaskId(){
        return getLongValue(APP_PREFERENCES_CURRENT_TASK_ID);
    }

    /*
    UNDER THIS LINE- TASKS TIME METHODS
     */

    public static void setLastTaskTime(long sec){
        writeLongValue(APP_PREFERENCES_LAST_TASK_TIME, sec);
    }

    public static long getTimeBetweenLastTaskAndCurrentTime(){
        long lastTaskTime = getLastTaskTime();
        long answer = Utils.getCurrentTimeInSeconds() - lastTaskTime;
        Log.d("mytag", "Settings.getTimeBetweenLastTaskAndCurrentTime() answer = " + answer);
        if(answer < 0){
            return 704800;
        }else{
            return answer;
        }
    }

    public static long getLastTaskTime(){
        return getLongValue(APP_PREFERENCES_LAST_TASK_TIME);
    }

    /*
    UNDER THIS LINE- SUPPORTING METHODS. USE ONLY IS THIS CLASS
     */

    private static void writeStringValue(String path, String value){
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(path, value);
        editor.apply();
    }

    private static void writeLongValue(String path, long value){
        SharedPreferences.Editor editor = settings.edit();
        editor.putLong(path, value);
        editor.apply();
    }

    private static void writeBooleanValue(String path, boolean value){
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(path, value);
        editor.apply();
    }

    private static String getStringValue(String path){
        return getStringValue(path, "");
    }

    private static String getStringValue(String path, String defaultValue){
        return settings.getString(path, defaultValue);
    }

    private static long getLongValue(String path){
        return getLongValue(path, 0);
    }

    private static long getLongValue(String path, long defaultValue){
        return settings.getLong(path, defaultValue);
    }

    private static boolean getBooleanValue(String path){
        return getBooleanValue(path, false);
    }

    private static boolean getBooleanValue(String path, boolean defaultValue){
        return settings.getBoolean(path, defaultValue);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void unregisterListener() {

    }
}
