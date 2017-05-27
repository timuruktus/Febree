package ru.timuruktus.febree.MainPart;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;


import com.backendless.Backendless;


import java.util.ArrayList;


import ru.timuruktus.febree.ContentPart.StepsFragment;
import ru.timuruktus.febree.EventCallbackListener;
import ru.timuruktus.febree.IntroducingPart.IntroducingFragment;
import ru.timuruktus.febree.LocalPart.DataBase;
import ru.timuruktus.febree.LocalPart.Settings;
import ru.timuruktus.febree.LocalPart.StepCreator;
import ru.timuruktus.febree.LocalPart.Task;
import ru.timuruktus.febree.ProjectUtils.Utils;
import ru.timuruktus.febree.R;
import ru.timuruktus.febree.WebPart.BackendlessWeb;
import ru.timuruktus.febree.WebPart.EDownloadAndRefreshAllTasks;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

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
    protected static MainActivity instance;

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
        instance = this;
        mainPresenter = new MainPresenter(this);
        configureFonts();
        //initAllListeners();
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        fragmentContainer = (RelativeLayout) this.findViewById(R.id.content);
        //MobileAds.initialize(getApplicationContext(), "ca-app-pub-1766130558963175~1477951647");
        splashScreen = (ImageView) findViewById(R.id.splashScreen);

        configureToolbar();
        loadFirstFragment();
    }

    public static void showSplashScreen(){
        changeToolbarVisibility(true);
        splashScreen.setVisibility(View.VISIBLE);
    }

    public static void hideSplashScreen(){
        changeToolbarVisibility(false);
        splashScreen.setVisibility(View.INVISIBLE);
    }

    private void configureFonts(){
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Roboto-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }

    private void loadFirstFragment(){
        if(Settings.isFirstOpened()){
            openIntroducingFragment();
            //Log.d("mytag", "MainActivity.loadFirstFragment() introducingFragment opened");
        }else{
            openHomeFragment();
            //Log.d("mytag", "MainActivity.loadFirstFragment() homeFragment opened");
        }
    }

    private void openIntroducingFragment(){
        changeFragment(new IntroducingFragment(), false, true, true);
    }

    private void openHomeFragment(){
        changeFragment(new StepsFragment(), false, true, false);
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


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public static Context getContext() {
        return instance;
    }
}
