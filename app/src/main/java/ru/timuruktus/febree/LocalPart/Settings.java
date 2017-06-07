package ru.timuruktus.febree.LocalPart;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.util.Log;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.local.UserTokenStorageFactory;

import ru.timuruktus.febree.BaseModel;
import ru.timuruktus.febree.MainPart.MainActivity;
import ru.timuruktus.febree.ProjectUtils.CustomDialog1;
import ru.timuruktus.febree.ProjectUtils.Utils;
import ru.timuruktus.febree.R;

public class Settings implements BaseModel {

    private static final String APP_PREFERENCES = "mySettings";
    private static final String APP_PREFERENCES_FIRST_OPENED = "firstOpen";
    private static final String APP_PREFERENCES_FIRST_OPENED_STEP = "firstOpenStep";
    private static final String APP_PREFERENCES_LEVEL = "level";
    private static final String APP_PREFERENCES_POINTS = "points";
    private static final String APP_PREFERENCES_LEVELS_DONE = "levelsDone";
    private static final String APP_PREFERENCES_LEVELS_SKIPPED = "levelsSkipped";
    private static final String APP_PREFERENCES_CURRENT_TASK_ID = "currentTaskId";
    private static final String APP_PREFERENCES_LAST_TASK_TIME = "lastTaskTime";
    private static final String APP_PREFERENCES_USER_LOGGED = "userLogged";
    private static SharedPreferences settings;

    public static void initSettings(Context con){
        settings = con.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
    }

    /**
     * UNDER THIS LINE- 3-LVL METHODS
     * 3-lvl methods is used to make some calculations and other operations.
     * This methods use ONLY 2-lvl methods
     */

    public static boolean checkIfUserLogged(){
        return Backendless.UserService.CurrentUser() != null;
    }

    public static void exitUser(){
        BackendlessUser user = Backendless.UserService.CurrentUser();
        if(user != null){
            Backendless.UserService.logout(getLogoutCallback());
        }else{
            Log.d("mytag", "Unable to logout. User didn't log in");
        }
    }

    private static AsyncCallback<Void> getLogoutCallback(){
        return new AsyncCallback<Void>() {
            @Override
            public void handleResponse(Void response) {

            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Log.d("mytag", fault.toString());
            }
        };
    }

    public static void increaseLevel(){
        long level = getLevel();
        writeLongValue(APP_PREFERENCES_LEVEL, ++level);
        if(level == 2){
            StepConfigurator.openStepsInBlock(0);
        }else if(level == 4){
            StepConfigurator.openStepsInBlock(1);
        }
    }

    public static void decreaseLevel(){
        long level = getLevel();
        writeLongValue(APP_PREFERENCES_LEVEL, --level);
    }

    public static void changePoints(long change){
        long pointsBefore = getPoints();
        setPoints(pointsBefore + change);
        long pointsAfter = pointsBefore + change;
        if(pointsAfter >= getCurrentLevelLimit()){
            increaseLevel();
            makeLevelIncreaseDialog();
        }

    }

    public static long getCurrentLevelLimit(){
        long currentLevel = getLevel();
        return currentLevel * 100;
    }

    public static void makeLevelIncreaseDialog(){
        if(getLevel() == 2){
            CustomDialog1 dialog = new CustomDialog1();
            Context context = MainActivity.getInstance();
            Resources resources = context.getResources();
            String title = resources.getString(R.string.complete_level_1_title);
            String text = resources.getString(R.string.complete_level_1_text);
            String buttonText = resources.getString(R.string.complete_level_1_button);
            int titleColor = resources.getColor(R.color.complete_level_1_title_color);
            dialog.buildDialog(context)
                    .setDismissOnClick(true)
                    .setTitle(title)
                    .setFirstText(text)
                    .setButtonText(buttonText)
                    .setTitleColor(titleColor)
                    .setOnClickListener(v -> dialog.dismiss())
                    .show();
        }else{
            CustomDialog1 dialog = new CustomDialog1();
            Context context = MainActivity.getInstance();
            Resources resources = context.getResources();
            String title = resources.getString(R.string.complete_level_title);
            String text = resources.getString(R.string.complete_level_text);
            String buttonText = resources.getString(R.string.complete_level_button);
            int titleColor = resources.getColor(R.color.complete_level_title_color);
            dialog.buildDialog(context)
                    .setDismissOnClick(true)
                    .setTitle(title)
                    .setFirstText(text)
                    .setButtonText(buttonText)
                    .setTitleColor(titleColor)
                    .setOnClickListener(v -> dialog.dismiss())
                    .show();
        }
    }


    public static void incrementLevelsDone(){
        long level = getLevelsDone();
        writeLongValue(APP_PREFERENCES_LEVELS_DONE, ++level);
    }


    public static void incrementLevelsSkipped(){
        long level = getLevelsSkipped();
        writeLongValue(APP_PREFERENCES_LEVELS_SKIPPED, ++level);
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

    /**
     * UNDER THIS LINE- 2-LVL METHODS
     * 2-lvl methods is used to specify values to write and read.
     */

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
    UNDER THIS LINE- FIRST APP OPEN METHODS
     */

    public static void setFirstOpenedStep(boolean isFirstOpened){
        writeBooleanValue(APP_PREFERENCES_FIRST_OPENED_STEP, isFirstOpened);
    }

    public static boolean isFirstOpenedStep(){
        return getBooleanValue(APP_PREFERENCES_FIRST_OPENED_STEP, true);
    }

    /*
    UNDER THIS LINE- SET AND CHANGING LEVELS METHODS
     */

    public static void setLevel(long level){
        writeLongValue(APP_PREFERENCES_LEVEL, level);
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

    public static long getPoints(){
        return getLongValue(APP_PREFERENCES_POINTS);
    }

    /*
    UNDER THIS LINE- DONE LEVELS METHODS
     */

    public static void setLevelsDone(long levels){
        writeLongValue(APP_PREFERENCES_LEVELS_DONE, levels);
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


    public static long getLastTaskTime(){
        return getLongValue(APP_PREFERENCES_LAST_TASK_TIME);
    }

    /**
     * UNDER THIS LINE- 1-LVL METHODS
     * 1-lvl methods is used to write and read data from SharedReference.
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



}
