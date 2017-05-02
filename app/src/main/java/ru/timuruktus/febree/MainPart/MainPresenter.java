package ru.timuruktus.febree.MainPart;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;


import ru.timuruktus.febree.BaseEvent;
import ru.timuruktus.febree.BasePresenter;
import ru.timuruktus.febree.EventHandler;
import ru.timuruktus.febree.R;

import static android.app.FragmentTransaction.TRANSIT_FRAGMENT_FADE;

public class MainPresenter implements BasePresenter, EventHandler {

    private MainActivity mainActivity;
    private FragmentTransaction fragmentTransaction;
    private FragmentManager fragmentManager;
    private Fragment currentFragment;
    public static final boolean DONT_ADD_TO_BACKSTACK = false;
    public static final boolean ADD_TO_BACKSTACK = true;
    public static final boolean HIDE_MENU = true;
    public static final boolean DONT_HIDE_MENU = false;
    public static final boolean HIDE_TOOLBAR = true;
    public static final boolean DONT_HIDE_TOOLBAR = false;

    MainPresenter(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        fragmentManager = mainActivity.getFragmentManager();
        initListener();
    }

    @Subscribe
    final public void changeFragment(EChangeFragment event){
        Fragment fragment = event.getFragment();
        Class fragmentClass = fragment.getClass();
        if(currentFragment != null) {
            if (fragmentClass.equals(currentFragment.getClass())) {
                //Log.d("mytag", "MainPresenter.changeFragment() already that fragment");
                return;
            }
        }
        currentFragment = fragment;
        fragmentTransaction = fragmentManager.beginTransaction();
        if(event.isAddToBackStack()) fragmentTransaction.addToBackStack(null);
        fragmentTransaction.setTransition(TRANSIT_FRAGMENT_FADE);
        setNewFragment(fragment);
        hideNavigationMenu(event.isHideMenu());
        fragmentTransaction.commit();
    }

    //private Fragment getCurrentFragment(){
    //   return fragmentManager.findFragmentById(R.id.content);
    //}

    private void hideNavigationMenu(boolean hide){
        if(hide){
            mainActivity.setContentFullscreen();
        }else{
            mainActivity.setContentNotFullscreen();
        }
    }

    private void setNewFragment(Fragment fragment){
        fragmentTransaction.replace(R.id.content, fragment);
    }

    @Override
    final public void initListener() {
        EventBus.getDefault().register(this);
    }

    @Override
    final public void unregisterListener() {
        EventBus.getDefault().unregister(this);
    }
}
