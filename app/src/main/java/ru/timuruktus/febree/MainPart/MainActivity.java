package ru.timuruktus.febree.MainPart;

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
    private final String YOUR_APP_ID = "079489C7-78CF-BC51-FF04-055547860300";
    private final String YOUR_SECRET_KEY = "8EFFCEF7-F0BE-C415-FFF3-E459B2957300";
    private final String APP_VERSION = "v1";
    BottomNavigationView navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Backendless.initApp(this, YOUR_APP_ID, YOUR_SECRET_KEY, APP_VERSION);
        //EventBus.getDefault().register(this);
        initAllListeners();
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        fragmentContainer = (RelativeLayout) this.findViewById(R.id.content);
        configureToolbar();
        loadFirstFragment();
        configureBottomNav();


    }

    private void configureCurrentTaskPoints(){
        EventBus.getDefault().post(new AGetTaskById(Settings.getCurrentTaskId(this), configureCurrentTaskListener));
    }

    private void initAllListeners(){
        MainPresenter mainPresenter = new MainPresenter(this);
        DataBase dataBase = new DataBase();
        BackendlessWeb backendlessWeb = new BackendlessWeb();
    }

    private void loadFirstFragment(){
        if(Settings.isFirstOpened(this)){
            openIntroducingFragment();
            Log.d("mytag", "MainActivity.loadFirstFragment() introducingFragment opened");
        }else{
            openHomeFragment();
            configureCurrentTaskPoints();
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
        return Settings.getTimeBetweenLastTaskAndCurrentTime(this) / DAY_IN_SECOND;
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

                    if(Settings.getLevelsDone(MainActivity.this) < 1){
                        Toast.makeText(MainActivity.this, R.string.you_need_1_tasks,
                                Toast.LENGTH_SHORT).show();
                        return false;
                    }else{
                        EventBus.getDefault().post(new EChangeFragment(new DoneTasksFragment(),
                                ADD_TO_BACKSTACK, DONT_HIDE_MENU));
                        return true;
                    }

                case R.id.navigation_visualisation:
                    if(Settings.getLevelsDone(MainActivity.this) < 3){
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
            switch (item.getItemId()) {
                case R.id.menu_DB:
                    if(!Utils.isOnline()){
                        Toast.makeText(MainActivity.this, R.string.needed_to_internet,
                                Toast.LENGTH_SHORT).show();
                    }else{
                        EventBus.getDefault().post(new EDownloadAndRefreshAllTasks());
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
