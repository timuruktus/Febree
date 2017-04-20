package ru.timuruktus.febree.VisualisationPart;

import android.view.View;

import com.google.android.gms.ads.AdView;

import ru.timuruktus.febree.BaseEvent;


public class EInitAdmob implements BaseEvent {

    public EInitAdmob(AdView adView) {
        this.adView = adView;
    }

    public AdView adView;


}
