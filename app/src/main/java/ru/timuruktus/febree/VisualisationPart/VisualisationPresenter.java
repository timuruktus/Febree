package ru.timuruktus.febree.VisualisationPart;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import ru.timuruktus.febree.BasePresenter;
import ru.timuruktus.febree.EventHandler;
import ru.timuruktus.febree.R;


public class VisualisationPresenter implements BasePresenter, EventHandler {


    private VisualisationFragment fragment;
    private  AdRequest adRequest;

    VisualisationPresenter(VisualisationFragment fragment){
        this.fragment = fragment;
        initListener();
        adRequest = new AdRequest.Builder().build();
    }

    @Override
    final public void initListener() {
        EventBus.getDefault().register(this);
    }

    @Override
    final public void unregisterListener() {
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    final public void initAdView(EInitAdmob event){
        event.adView.loadAd(adRequest);
    }
}
