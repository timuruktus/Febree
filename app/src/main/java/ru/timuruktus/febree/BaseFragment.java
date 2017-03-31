package ru.timuruktus.febree;

import android.app.Fragment;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

public abstract class BaseFragment extends Fragment {

    @Override
    public void onResume(){
        super.onResume();
        //EventBus.getDefault().post(new EOnFragmentChanged(this));
    }

}
