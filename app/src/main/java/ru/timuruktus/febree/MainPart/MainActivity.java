package ru.timuruktus.febree.MainPart;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.RelativeLayout;
import android.widget.Toast;


import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.Constants;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.backendless.Backendless;


import ru.timuruktus.febree.StepsPart.StepsFragment;
import ru.timuruktus.febree.DonatePart.DonateFragment;
import ru.timuruktus.febree.IntroducingPart.IntroducingFragment;
import ru.timuruktus.febree.LocalPart.DataBase;
import ru.timuruktus.febree.LocalPart.Settings;
import ru.timuruktus.febree.LocalPart.StepConfigurator;
import ru.timuruktus.febree.R;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static android.graphics.Color.BLACK;
import static ru.timuruktus.febree.MainPart.MainPresenter.*;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        BillingProcessor.IBillingHandler{

    RelativeLayout fragmentContainer;
    private MainPresenter mainPresenter;
    private DataBase dataBase;
    private Toolbar toolbar;
    DrawerLayout drawer;
    protected static MainActivity instance;
    protected Menu menu;
    public static NavigationView navigationView;
    public static BillingProcessor bp;
    public static String LICENSE_KEY;

    @Override
    final protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final String APP_ID = "CFF3349B-7FBD-06A1-FFBB-2B9CE809D900";
        final String API_KEY = "8EFFCEF7-F0BE-C415-FFF3-E459B2957300";
        Backendless.initApp(this, APP_ID, API_KEY);
        Settings.initSettings(MainActivity.this);
        if(Settings.isFirstOpened()) {
            StepConfigurator.setFirstLaunchSteps(this);
        }
        instance = this;
        mainPresenter = new MainPresenter(this);
        configureFonts();
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        fragmentContainer = (RelativeLayout) this.findViewById(R.id.content);


        configureToolbar();
        loadFirstFragment();
        LICENSE_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEArvlJ8VfdRuxXrQqufzl7hAiSRlsiiAsoaZDlS/lH2zeZcQBuFcSmV+klQYuXeTh/YQJgAU3zVqUs9a7MYF7ZBe/qWWyZWC0KzkC18DvDBzjnZ8XoysDglpxbXs+bi+Ef0xRFCUpZTjUrxhE0bXvGsbYGzWTIMyo9M7TLcRA6f1aawzAygE9nbl4rAwP2NrB3fZTJF5yojBhIHo1dv9GwIZdG0XDc5F5oc7E7vQxORdZ1p4TOiYezO6fXy4ZsfoLpUEEtiiUpaNrkTc5krTEiN7+DsMUyBRzV8Z80rxjXUnMW6Az05uRu8j3r1n6jyAV7yqo/fDHEOSR6qnDMo3h+gwIDAQAB";
        bp = BillingProcessor.newBillingProcessor(this, LICENSE_KEY, this);
        bp.initialize();

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
            Log.d("mytag", "MainActivity.loadFirstFragment() introducingFragment opened");
        }else{
            openHomeFragment();
            Log.d("mytag", "MainActivity.loadFirstFragment() homeFragment opened");
        }
    }

    private void openIntroducingFragment(){
        changeFragment(new IntroducingFragment(), DONT_ADD_TO_BACKSTACK, REFRESH, HIDE_TOOLBAR);
    }

    private void openHomeFragment(){
        changeFragment(new StepsFragment(), DONT_ADD_TO_BACKSTACK, REFRESH, DONT_HIDE_TOOLBAR);
    }


    private void configureToolbar(){
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(BLACK);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = mainPresenter.getNavigationMenuListener(drawer, toolbar, this);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }



    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public static Activity getInstance() {
        return instance;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_donate){
            changeFragment(new DonateFragment(), DONT_ADD_TO_BACKSTACK, DONT_REFRESH, DONT_HIDE_TOOLBAR);
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        drawer.closeDrawer(GravityCompat.START);
        return mainPresenter.onNavMenuClick(item);
    }

    @Override
    protected void onDestroy() {
        if (bp != null) {
            bp.release();
        }
        mainPresenter.onDestroy();
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!bp.handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onProductPurchased(String productId, TransactionDetails details) {

    }

    @Override
    public void onPurchaseHistoryRestored() {

    }

    @Override
    public void onBillingError(int errorCode, Throwable error) {
        if(errorCode != Constants.BILLING_RESPONSE_RESULT_USER_CANCELED){
            Toast.makeText(this, R.string.error, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBillingInitialized() {

    }
}
