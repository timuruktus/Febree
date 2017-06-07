package ru.timuruktus.febree.MainPart;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;


import java.util.HashMap;

import ru.timuruktus.febree.BasePresenter;
import ru.timuruktus.febree.ContentPart.StepsFragment;
import ru.timuruktus.febree.DonatePart.DonateFragment;
import ru.timuruktus.febree.JoinPart.LoginPart.LoginFragment;
import ru.timuruktus.febree.LocalPart.Settings;
import ru.timuruktus.febree.ProjectUtils.Utils;
import ru.timuruktus.febree.R;

import static android.app.FragmentTransaction.TRANSIT_FRAGMENT_FADE;
import static android.support.v4.widget.DrawerLayout.LOCK_MODE_LOCKED_CLOSED;
import static android.support.v4.widget.DrawerLayout.LOCK_MODE_UNLOCKED;

public class MainPresenter implements BasePresenter{

    private static MainActivity mainActivity;
    private static FragmentTransaction fragmentTransaction;
    private static FragmentManager fragmentManager;
    private static String currentFragmentTag = null;
    public static final String ARG_INFO = "Info";

    public static final boolean DONT_REFRESH = false;
    public static final boolean REFRESH = true;
    public static final boolean DONT_ADD_TO_BACKSTACK = false;
    public static final boolean ADD_TO_BACKSTACK = true;
    public static final boolean HIDE_MENU = true;
    public static final boolean DONT_HIDE_MENU = false;
    public static final boolean HIDE_TOOLBAR = true;
    public static final boolean DONT_HIDE_TOOLBAR = false;

    MainPresenter(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        fragmentManager = mainActivity.getFragmentManager();
    }



    public static void changeFragmentWithInfo(Fragment fragment, boolean addToBackStack, boolean refresh,
                                      boolean hideToolbar, HashMap<String, Integer> info){
        //String fragmentTag = fragment.getClass().toString();
        //Log.d("mytag", "MainPresenter.changeFragmentWithInfo() fragmentTag = " + fragmentTag);
        //if(refresh) currentFragmentTag = null;
        //if(!backStackIsNull()) {
        //    FragmentManager.BackStackEntry lastEntry = fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount() - 1);
        //    currentFragmentTag = lastEntry.getName();
        //}
        //if(currentFragmentTag != null) {
        //    if(fragmentTag.equals(currentFragmentTag) && !refresh) {
        //        Log.d("mytag", "MainPresenter.changeFragmentWithInfo() return in refresh check");
        //        return;
        //    }
        // }
        //Log.d("mytag", "MainPresenter.changeFragmentWithInfo() currentFragmentTag = " + currentFragmentTag);
        fragmentTransaction = fragmentManager.beginTransaction();
        if(addToBackStack) {
            fragmentTransaction.addToBackStack(fragment.getClass().toString());
        }else {
            fragmentManager.popBackStack();
        }
        if(info != null) {
            Bundle args = new Bundle();
            args.putSerializable(ARG_INFO, info);
            fragment.setArguments(args);
        }
        changeToolbarVisibility(hideToolbar);
        fragmentTransaction.setTransition(TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.replace(R.id.content, fragment);
        fragmentTransaction.commit();

    }

    private static boolean backStackIsNull(){
        return fragmentManager.getBackStackEntryCount() == 0;
    }

    public static void setCheckedItemInNavMenu(int resId){
        mainActivity.navigationView.setCheckedItem(resId);
    }

    public static void changeFragment(Fragment fragment, boolean addToBackStack, boolean refresh,
                                      boolean hideToolbar){
        changeFragmentWithInfo(fragment, addToBackStack, refresh, hideToolbar, null);
    }

    public static void changeToolbarTitle(String text){
        mainActivity.getSupportActionBar().setTitle(text);

    }

    public static void changeToolbarVisibility(boolean hideToolbar){
        RelativeLayout fragmentContainer = (RelativeLayout)
                mainActivity.findViewById(R.id.content);
        ProgressBar levelBar = (ProgressBar) mainActivity.findViewById(R.id.levelBar);
        CoordinatorLayout.LayoutParams layoutParams = new CoordinatorLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if(!hideToolbar){
            levelBar.setVisibility(View.VISIBLE);
            mainActivity.getSupportActionBar().show();
            int totalMargin = Utils.convertDpToPx(56, mainActivity);
            layoutParams.setMargins(0,totalMargin,0,0);
            fragmentContainer.setLayoutParams(layoutParams);
            mainActivity.drawer.setDrawerLockMode(LOCK_MODE_UNLOCKED);
            mainActivity.getSupportActionBar().setSubtitle(Settings.getLevel() + " LVL");
        }else{
            levelBar.setVisibility(View.INVISIBLE);
            mainActivity.getSupportActionBar().hide();
            layoutParams.setMargins(0,0,0,0);
            fragmentContainer.setLayoutParams(layoutParams);
            mainActivity.drawer.setDrawerLockMode(LOCK_MODE_LOCKED_CLOSED);
        }

    }

    void enableToolbarButton(int resId, boolean enabled){
        mainActivity.menu.findItem(resId).setEnabled(enabled).setVisible(enabled);
        //mainActivity.menu.findItem(resId).set;
    }

    ActionBarDrawerToggle getNavigationMenuListener(DrawerLayout drawer, Toolbar toolbar,
                                                              Activity activity){
        return new ActionBarDrawerToggle(activity, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close){
            @Override
            public void onDrawerStateChanged(int newState) {
                super.onDrawerStateChanged(newState);
                MenuItem menuExit = mainActivity.navigationView.getMenu().findItem(R.id.nav_exit);
                MenuItem menuLogin = mainActivity.navigationView.getMenu().findItem(R.id.nav_login);
                if(Settings.checkIfUserLogged()){
                    menuLogin.setVisible(false);
                    menuExit.setVisible(true);
                }else{
                    menuLogin.setVisible(true);
                    menuExit.setVisible(false);
                }
            }
        };
    }

    boolean onNavMenuClick(MenuItem item){
        int id = item.getItemId();
        if(id == R.id.nav_exit){
            Settings.exitUser();
            mainActivity.drawer.closeDrawer(GravityCompat.START);
            changeFragment(new StepsFragment(), DONT_ADD_TO_BACKSTACK, REFRESH, DONT_HIDE_TOOLBAR);
            return false;
        }else if(id == R.id.nav_login){
            changeFragment(new LoginFragment(), ADD_TO_BACKSTACK, DONT_REFRESH, DONT_HIDE_TOOLBAR);
        }else if(id == R.id.nav_home){
            changeFragment(new StepsFragment(), ADD_TO_BACKSTACK, DONT_REFRESH, DONT_HIDE_TOOLBAR);
        }else if(id == R.id.nav_donate){
            changeFragment(new DonateFragment(), ADD_TO_BACKSTACK, DONT_REFRESH, DONT_HIDE_TOOLBAR);
        }
        return true;
    }

    void onDestroy(){
        currentFragmentTag = null;
    }


}
