package ru.timuruktus.febree.VisualisationPart;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;


import java.util.ArrayList;


import ru.timuruktus.febree.BaseFragment;
import ru.timuruktus.febree.LocalPart.Settings;
import ru.timuruktus.febree.R;

import static ru.timuruktus.febree.ProjectUtils.Utils.boldItalicTypeface;
import static ru.timuruktus.febree.ProjectUtils.Utils.boldTypeFace;
import static ru.timuruktus.febree.ProjectUtils.Utils.italicTypeface;
import static ru.timuruktus.febree.ProjectUtils.Utils.usualTypeface;


public class VisualisationFragment extends BaseFragment {

    public View rootView;
    private Context context;

    private TextView currentPoints, completedTaskCount, skippedTaskCount;
    private int colorEasy;
    private int colorMedium;
    private int colorHard;
    private long easyPercentage;
    private long mediumPercentage;
    private long hardPercentage;
    AdView adView_1;
    private VisualisationPresenter presenter;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rootView =
                inflater.inflate(R.layout.visualisation_fragment, container, false);

        return rootView;
    }


    @Override
    public void setTypefaces() {

    }
}
