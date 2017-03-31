package ru.timuruktus.febree.MainPart;

import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.Window;

import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.backendless.Backendless;

import org.greenrobot.eventbus.EventBus;

import ru.timuruktus.febree.ContentPart.TaskFragment;
import ru.timuruktus.febree.IntroducingPart.IntroducingFragment;
import ru.timuruktus.febree.LocalPart.DataBase;
import ru.timuruktus.febree.LocalPart.Settings;
import ru.timuruktus.febree.R;
import ru.timuruktus.febree.WebPart.BackendlessWeb;

import static ru.timuruktus.febree.MainPart.MainPresenter.*;

public class MainActivity extends AppCompatActivity {

    RelativeLayout fragmentContainer;
    private final String YOUR_APP_ID = "079489C7-78CF-BC51-FF04-055547860300";
    private final String YOUR_SECRET_KEY = "8EFFCEF7-F0BE-C415-FFF3-E459B2957300";
    private final String APP_VERSION = "v1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Backendless.initApp(this, YOUR_APP_ID, YOUR_SECRET_KEY, APP_VERSION);
        initAllListeners();
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        fragmentContainer = (RelativeLayout) this.findViewById(R.id.content);
        configureToolbar();
        loadFirstFragment();

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
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private void configureToolbar(){
        //getActionBar().setHideOnContentScrollEnabled(true);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.toolbar);
        TextView toolbarText = (TextView) findViewById(R.id.toolbarText);
        toolbarText.setTypeface(Typeface.createFromAsset(getAssets(), "Roboto-Regular.ttf"));
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:

                    return true;
                case R.id.navigation_dashboard:

                    return true;
                case R.id.navigation_news:

                    return true;
            }
            return false;
        }

    };

}
