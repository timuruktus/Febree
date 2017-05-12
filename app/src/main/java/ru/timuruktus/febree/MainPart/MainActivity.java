package ru.timuruktus.febree.MainPart;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.backendless.Backendless;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.analytics.FirebaseAnalytics;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;


import ru.timuruktus.febree.ContentPart.TaskFragment;
import ru.timuruktus.febree.EventCallbackListener;
import ru.timuruktus.febree.IntroducingPart.IntroducingFragment;
import ru.timuruktus.febree.LocalPart.AGetNonPassedByLevelTasks;
import ru.timuruktus.febree.LocalPart.AGetTaskById;
import ru.timuruktus.febree.LocalPart.DataBase;
import ru.timuruktus.febree.LocalPart.Settings;
import ru.timuruktus.febree.LocalPart.StepCreator;
import ru.timuruktus.febree.LocalPart.Task;
import ru.timuruktus.febree.ProjectUtils.Utils;
import ru.timuruktus.febree.R;
import ru.timuruktus.febree.VisualisationPart.VisualisationFragment;
import ru.timuruktus.febree.WebPart.BackendlessWeb;
import ru.timuruktus.febree.WebPart.EDownloadAndRefreshAllTasks;

import static android.graphics.Color.BLACK;
import static ru.timuruktus.febree.MainPart.MainPresenter.*;
import static ru.timuruktus.febree.ProjectUtils.Utils.DAY_IN_SECOND;

public class MainActivity extends AppCompatActivity {

    RelativeLayout fragmentContainer;
    private MainPresenter mainPresenter;
    private DataBase dataBase;
    private BackendlessWeb backendlessWeb;
    private Toolbar toolbar;
    DrawerLayout drawer;
    private static ImageView splashScreen;

    @Override
    final protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final String APP_ID = "CFF3349B-7FBD-06A1-FFBB-2B9CE809D900";
        final String API_KEY = "8EFFCEF7-F0BE-C415-FFF3-E459B2957300";
        Backendless.initApp(this, APP_ID, API_KEY);
        Settings.initSettings(MainActivity.this);
        if(Settings.isFirstOpened()) {
            StepCreator.setFirstLaunchSteps(this);
        }
        mainPresenter = new MainPresenter(this);

        //initAllListeners();
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        fragmentContainer = (RelativeLayout) this.findViewById(R.id.content);
        //MobileAds.initialize(getApplicationContext(), "ca-app-pub-1766130558963175~1477951647");
        splashScreen = (ImageView) findViewById(R.id.splashScreen);

        configureToolbar();
        loadFirstFragment();
        Utils.initTypefaces(this);
    }

    public static void showSplashScreen(){
        splashScreen.setVisibility(View.VISIBLE);
    }

    public static void hideSplashScreen(){
        splashScreen.setVisibility(View.INVISIBLE);
    }


    private void configureCurrentTaskPoints(){
        EventBus.getDefault().post(new AGetTaskById(Settings.getCurrentTaskId(), configureCurrentTaskListener));
    }

    private void initAllListeners(){
        //Log.d("mytag", "MainActivity.initAllListeners() listeners initialised");

        dataBase = new DataBase();
        backendlessWeb = new BackendlessWeb();

    }

    private void loadFirstFragment(){
        if(Settings.isFirstOpened()){
            openIntroducingFragment();
            //Log.d("mytag", "MainActivity.loadFirstFragment() introducingFragment opened");
        }else{
            openHomeFragment();
            configureCurrentTaskPoints();
            if(Utils.isOnline()){
                EventBus.getDefault().post(new EDownloadAndRefreshAllTasks());
            }
            //Log.d("mytag", "MainActivity.loadFirstFragment() homeFragment opened");
        }
    }

    private void openIntroducingFragment(){
        mainPresenter.changeToolbarVisibility(false);
        changeFragment(new IntroducingFragment(), false, true, true);
    }

    private void openHomeFragment(){
        changeFragment(new TaskFragment(), false, true, false);
    }


    private void configureToolbar(){
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(BLACK);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }

    private long daysAfterLastTask(){
        long answer = Settings.getTimeBetweenLastTaskAndCurrentTime() / DAY_IN_SECOND;
        if(answer > 0){
            Settings.setLastTaskTime(Utils.getCurrentTimeInSeconds());
        }
        return answer;
    }

    final EventCallbackListener checkAvailableTasksListener = (event) -> {
        AGetNonPassedByLevelTasks currentEvent = (AGetNonPassedByLevelTasks) event;
        ArrayList<Task> availableTasks = currentEvent.getTasks();
        Context context = MainActivity.this;
        if(availableTasks.size() == 0){
            Toast.makeText(context, R.string.has_no_tasks_for_you,
                    Toast.LENGTH_SHORT).show();
        }else{
            Settings.decreaseLevel();
            Settings.setPoints(Settings.getCurrentLimit() - 400);
            Settings.setCurrentTaskId(0);
            loadFirstFragment();
        }
    };

    final EventCallbackListener configureCurrentTaskListener = (event) -> {
        AGetTaskById currentEvent = (AGetTaskById) event;
        Task currentTask = currentEvent.getTask();
        currentTask.setPoints(currentTask.getPoints() - daysAfterLastTask());
        currentTask.save();
    };
}
