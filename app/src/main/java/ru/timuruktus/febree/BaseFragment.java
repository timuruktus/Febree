package ru.timuruktus.febree;

import android.app.Fragment;
import android.util.Log;


public abstract class BaseFragment extends Fragment {

    @Override
    public void onResume(){
        super.onResume();
        //EventBus.getDefault().post(new EOnFragmentChanged(this));
    }

    public abstract void setTypefaces();

}
