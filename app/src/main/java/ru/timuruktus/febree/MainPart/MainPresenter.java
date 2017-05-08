package ru.timuruktus.febree.MainPart;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.design.widget.CoordinatorLayout;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;


import java.util.concurrent.TimeUnit;

import ru.timuruktus.febree.BasePresenter;
import ru.timuruktus.febree.EventHandler;
import ru.timuruktus.febree.ProjectUtils.Utils;
import ru.timuruktus.febree.R;
import rx.functions.Action1;

import static android.app.FragmentTransaction.TRANSIT_FRAGMENT_FADE;
import static android.support.v4.widget.DrawerLayout.LOCK_MODE_LOCKED_CLOSED;
import static android.support.v4.widget.DrawerLayout.LOCK_MODE_UNLOCKED;

public class MainPresenter implements BasePresenter{

    private static MainActivity mainActivity;
    private static FragmentTransaction fragmentTransaction;
    private static FragmentManager fragmentManager;
    private static Class currentFragmentClass = null;
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

    public static void changeFragment(Fragment fragment, boolean addToBackStack, boolean refresh,
                                      boolean hideToolbar){
        Class fragmentClass = fragment.getClass();
        if(currentFragmentClass != null) {
            if (fragmentClass.equals(currentFragmentClass) && !refresh) {
                //Log.d("mytag", "MainPresenter.changeFragment() already that fragment");
                return;
            }
        }
        currentFragmentClass = fragment.getClass();
        fragmentTransaction = fragmentManager.beginTransaction();
        if(addToBackStack) fragmentTransaction.addToBackStack(null);
        changeToolbarVisibility(hideToolbar);
        fragmentTransaction.setTransition(TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.replace(R.id.content, fragment);
        fragmentTransaction.commit();
    }

    public static void changeToolbarVisibility(boolean hideToolbar){
        RelativeLayout fragmentContainer = (RelativeLayout)
                mainActivity.findViewById(R.id.content);
        CoordinatorLayout.LayoutParams layoutParams = new CoordinatorLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if(!hideToolbar){
            mainActivity.getSupportActionBar().show();
            int totalMargin = Utils.convertDpToPx(56, mainActivity);
            layoutParams.setMargins(0,totalMargin,0,0);
            fragmentContainer.setLayoutParams(layoutParams);
            mainActivity.drawer.setDrawerLockMode(LOCK_MODE_UNLOCKED);
        }else{
            mainActivity.getSupportActionBar().hide();
            layoutParams.setMargins(0,0,0,0);
            fragmentContainer.setLayoutParams(layoutParams);
            mainActivity.drawer.setDrawerLockMode(LOCK_MODE_LOCKED_CLOSED);
        }

    }

}
