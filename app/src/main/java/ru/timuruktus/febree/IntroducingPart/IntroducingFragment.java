package ru.timuruktus.febree.IntroducingPart;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tmall.ultraviewpager.UltraViewPager;

import ru.timuruktus.febree.BaseFragment;
import ru.timuruktus.febree.R;

public class IntroducingFragment extends Fragment implements BaseFragment  {

    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rootView =
                inflater.inflate(R.layout.introducing_fragment, container, false);
        UltraViewPager ultraViewPager = (UltraViewPager) rootView.findViewById(R.id.ultra_viewpager);
        ultraViewPager.setScrollMode(UltraViewPager.ScrollMode.HORIZONTAL);
        PagerAdapter adapter = new UltraPagerAdapter(false, IntroSlide.initializeData(rootView.getContext()));
        ultraViewPager.setAdapter(adapter);

        ultraViewPager.initIndicator();
        ultraViewPager.getIndicator()
                .setOrientation(UltraViewPager.Orientation.HORIZONTAL)
                .setFocusColor(Color.WHITE)
                .setNormalColor(Color.GRAY)
                .setRadius((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 6, getResources().getDisplayMetrics()))
                .setIndicatorPadding(100)
                .setMargin(0,0,0,60)
                .setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM)
                .build();

        return rootView;
    }

}
