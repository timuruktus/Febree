package ru.timuruktus.febree.MainPart;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import ru.timuruktus.febree.BasePresenter;
import ru.timuruktus.febree.EventHandler;
import ru.timuruktus.febree.ProjectUtils.Utils;
import ru.timuruktus.febree.R;
import rx.Subscription;
import rx.functions.Action1;

import static android.app.FragmentTransaction.TRANSIT_FRAGMENT_FADE;
import static android.support.v4.widget.DrawerLayout.LOCK_MODE_LOCKED_CLOSED;
import static android.support.v4.widget.DrawerLayout.LOCK_MODE_UNLOCKED;

public class MainPresenter implements BasePresenter{

    private static MainActivity mainActivity;
    private static FragmentTransaction fragmentTransaction;
    private static FragmentManager fragmentManager;
    private static Class currentFragmentClass = null;
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
        Class fragmentClass = fragment.getClass();
        if(currentFragmentClass != null) {
            if (fragmentClass.equals(currentFragmentClass) && !refresh) {
                //Log.d("mytag", "MainPresenter.changeFragment() already that fragment");
                return;
            }
        }
        currentFragmentClass = fragment.getClass();
        fragmentTransaction = fragmentManager.beginTransaction();
        if(addToBackStack) {
            fragmentTransaction.addToBackStack(null);
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
        }else{
            levelBar.setVisibility(View.INVISIBLE);
            mainActivity.getSupportActionBar().hide();
            layoutParams.setMargins(0,0,0,0);
            fragmentContainer.setLayoutParams(layoutParams);
            mainActivity.drawer.setDrawerLockMode(LOCK_MODE_LOCKED_CLOSED);
        }

    }

    @Override
    public void addSubscription(Subscription subscription) {

    }

    @Override
    public void onDestroy() {

    }
}
