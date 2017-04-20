package ru.timuruktus.febree.MainPart;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.backendless.Backendless;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.analytics.FirebaseAnalytics;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import ru.timuruktus.febree.BaseEvent;
import ru.timuruktus.febree.ContentPart.TaskFragment;
import ru.timuruktus.febree.DoneTasksPart.DoneTasksFragment;
import ru.timuruktus.febree.EventCallbackListener;
import ru.timuruktus.febree.IntroducingPart.IntroducingFragment;
import ru.timuruktus.febree.LocalPart.AGetTaskById;
import ru.timuruktus.febree.LocalPart.DataBase;
import ru.timuruktus.febree.LocalPart.Settings;
import ru.timuruktus.febree.LocalPart.Task;
import ru.timuruktus.febree.ProjectUtils.Utils;
import ru.timuruktus.febree.R;
import ru.timuruktus.febree.VisualisationPart.VisualisationFragment;
import ru.timuruktus.febree.WebPart.BackendlessWeb;
import ru.timuruktus.febree.WebPart.EDownloadAndRefreshAllTasks;

import static ru.timuruktus.febree.MainPart.MainPresenter.*;
import static ru.timuruktus.febree.ProjectUtils.Utils.DAY_IN_SECOND;

public class MainActivity extends AppCompatActivity {

    RelativeLayout fragmentContainer;
    private final String APP_ID = "CFF3349B-7FBD-06A1-FFBB-2B9CE809D900";
    private final String API_KEY = "8EFFCEF7-F0BE-C415-FFF3-E459B2957300";
    BottomNavigationView navigation;
    private MainPresenter mainPresenter;
    private DataBase dataBase;
    private BackendlessWeb backendlessWeb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Backendless.initApp(this, APP_ID, API_KEY);
        //EventBus.getDefault().register(this);
        initAllListeners();
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        fragmentContainer = (RelativeLayout) this.findViewById(R.id.content);
        MobileAds.initialize(getApplicationContext(), "ca-app-pub-1766130558963175~1477951647");
        configureToolbar();
        loadFirstFragment();
        configureBottomNav();
        Utils.initTypefaces(this);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        detachAllListeners();
    }


    private void configureCurrentTaskPoints(){
        EventBus.getDefault().post(new AGetTaskById(Settings.getCurrentTaskId(), configureCurrentTaskListener));
    }

    private void initAllListeners(){
        Log.d("mytag", "MainActivity.initAllListeners() listeners initialised");
        mainPresenter = new MainPresenter(this);
        dataBase = new DataBase();
        backendlessWeb = new BackendlessWeb();
        Settings.initSettings(MainActivity.this);
    }

    private void detachAllListeners(){
        mainPresenter.unregisterListener();
        dataBase.unregisterListener();
        backendlessWeb.unregisterListener();
    }

    private void loadFirstFragment(){
        if(Settings.isFirstOpened()){
            openIntroducingFragment();
            Log.d("mytag", "MainActivity.loadFirstFragment() introducingFragment opened");
        }else{
            openHomeFragment();
            configureCurrentTaskPoints();
            //if(Utils.isOnline()){
            //    EventBus.getDefault().post(new EDownloadAndRefreshAllTasks());
            //}
            Log.d("mytag", "MainActivity.loadFirstFragment() homeFragment opened");
        }
    }

    private void openIntroducingFragment(){
        getSupportActionBar().hide();
        setContentFullscreen();
        EventBus.getDefault().post(new EChangeFragment(new IntroducingFragment(), DONT_ADD_TO_BACKSTACK,
                HIDE_TOOLBAR, HIDE_MENU));
    }

    private void openHomeFragment(){
        EventBus.getDefault().post(new EChangeFragment(new TaskFragment(), DONT_ADD_TO_BACKSTACK,
                DONT_HIDE_TOOLBAR, DONT_HIDE_MENU));
    }

    public void setContentFullscreen(){
        LinearLayout.LayoutParams layoutParams =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.weight = 0;
        fragmentContainer.setLayoutParams(layoutParams);
    }

    public void setContentNotFullscreen(){
        LinearLayout.LayoutParams layoutParams =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.weight = 1;
        fragmentContainer.setLayoutParams(layoutParams);
    }


    private void configureBottomNav(){
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private void configureToolbar(){
        //getActionBar().setHideOnContentScrollEnabled(true);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.toolbar);
        TextView toolbarText = (TextView) findViewById(R.id.toolbarText);
        toolbarText.setTypeface(Typeface.createFromAsset(getAssets(), "Roboto-Regular.ttf"));
        getSupportActionBar().hide();
    }

    private long daysAfterLastTask(){
        long answer = Settings.getTimeBetweenLastTaskAndCurrentTime() / DAY_IN_SECOND;
        if(answer > 0){
            Settings.setLastTaskTime(Utils.getCurrentTimeInSeconds());
        }
        return answer;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    EventBus.getDefault().post(new EChangeFragment(new TaskFragment(),
                            ADD_TO_BACKSTACK, DONT_HIDE_MENU));
                    return true;
                case R.id.navigation_dashboard:

                    if(Settings.getLevelsDone() < 1){
                        Toast.makeText(MainActivity.this, R.string.you_need_1_tasks,
                                Toast.LENGTH_SHORT).show();
                        return false;
                    }else{
                        EventBus.getDefault().post(new EChangeFragment(new DoneTasksFragment(),
                                ADD_TO_BACKSTACK, DONT_HIDE_MENU));
                        return true;
                    }

                case R.id.navigation_visualisation:
                    if(Settings.getLevelsDone() < 3){
                        Toast.makeText(MainActivity.this, R.string.you_need_3_tasks,
                                Toast.LENGTH_SHORT).show();
                        return false;
                    }else{
                        EventBus.getDefault().post(new EChangeFragment(new VisualisationFragment(),
                                ADD_TO_BACKSTACK, DONT_HIDE_MENU));
                        return true;
                    }

                case R.id.navigation_more:
                    TextView popupAnchor = (TextView) findViewById(R.id.popupAnchor);
                    PopupMenu popupMenu = new PopupMenu(MainActivity.this, popupAnchor);
                    popupMenu.inflate(R.menu.popup_menu);
                    popupMenu.setOnMenuItemClickListener(popupMenuListener);
                    popupMenu.show();
                    break;
            }
            return false;
        }

    };

    PopupMenu.OnMenuItemClickListener popupMenuListener = new PopupMenu.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            Context context = MainActivity.this;
            switch (item.getItemId()) {
                case R.id.menu_DB:
                    if(!Utils.isOnline()){
                        Toast.makeText(context, R.string.needed_to_internet,
                                Toast.LENGTH_SHORT).show();
                    }else{
                        EventBus.getDefault().post(new EDownloadAndRefreshAllTasks());
                    }
                    return true;

                case R.id.menu_level:
                    long currentLevel = Settings.getLevel();
                    if(currentLevel == Settings.EASY_LEVEL){
                        Toast.makeText(context, R.string.your_level_is_minimal,
                                Toast.LENGTH_SHORT).show();
                    }else{
                        Settings.decreaseLevel();
                        Settings.setPoints(Settings.getCurrentLimit() - 400);
                        loadFirstFragment();
                        Settings.setCurrentTaskId(0);
                    }
                    return true;

                default:
                    return false;
            }
        }
    };

    EventCallbackListener configureCurrentTaskListener = new EventCallbackListener() {
        @Override
        public void eventCallback(BaseEvent event) {
            AGetTaskById currentEvent = (AGetTaskById) event;
            Task currentTask = currentEvent.getTask();
            currentTask.setPoints(currentTask.getPoints() - daysAfterLastTask());
            currentTask.save();
        }
    };

}
