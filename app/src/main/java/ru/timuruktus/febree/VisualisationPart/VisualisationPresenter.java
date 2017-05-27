package ru.timuruktus.febree.VisualisationPart;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;


import ru.timuruktus.febree.BasePresenter;
import ru.timuruktus.febree.EventHandler;
import ru.timuruktus.febree.R;
import rx.Subscription;


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

    }

    @Override
    final public void unregisterListener() {

    }

    final public void initAdView(EInitAdmob event){
        event.adView.loadAd(adRequest);
    }

    @Override
    public void addSubscription(Subscription subscription) {

    }

    @Override
    public void onDestroy() {

    }
}
