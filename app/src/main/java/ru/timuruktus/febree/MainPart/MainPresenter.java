package ru.timuruktus.febree.MainPart;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;


import ru.timuruktus.febree.BaseEvent;
import ru.timuruktus.febree.BasePresenter;
import ru.timuruktus.febree.R;

import static android.app.FragmentTransaction.TRANSIT_FRAGMENT_FADE;

public class MainPresenter implements BasePresenter {

    private MainActivity mainActivity;
    private FragmentTransaction fragmentTransaction;
    private FragmentManager fragmentManager;
    public static final boolean DONT_ADD_TO_BACKSTACK = false;
    public static final boolean ADD_TO_BACKSTACK = true;
    public static final boolean HIDE_MENU = true;
    public static final boolean DONT_HIDE_MENU = false;
    public static final boolean HIDE_TOOLBAR = true;
    public static final boolean DONT_HIDE_TOOLBAR = false;

    public MainPresenter(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        EventBus.getDefault().register(this);
        fragmentManager = mainActivity.getFragmentManager();
    }

    @Subscribe
    public void changeFragment(EChangeFragment event){
        Fragment fragment = event.getFragment();
        if(fragment.equals(getCurrentFragment())) return;
        fragmentTransaction = fragmentManager.beginTransaction();
        if(event.isAddToBackStack()) fragmentTransaction.addToBackStack(null);
        fragmentTransaction.setTransition(TRANSIT_FRAGMENT_FADE);
        setNewFragment(fragment);
        hideNavigationMenu(event.isHideMenu());
        fragmentTransaction.commit();
    }

    private Fragment getCurrentFragment(){
       return mainActivity.getFragmentManager().findFragmentById(R.id.content);
    }

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

}
