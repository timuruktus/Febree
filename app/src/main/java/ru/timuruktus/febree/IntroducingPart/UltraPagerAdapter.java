package ru.timuruktus.febree.IntroducingPart;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ru.timuruktus.febree.ContentPart.TaskFragment;
import ru.timuruktus.febree.LocalPart.Settings;
import ru.timuruktus.febree.MainPart.MainPresenter;
import ru.timuruktus.febree.R;


import static ru.timuruktus.febree.ProjectUtils.Utils.*;

public class UltraPagerAdapter extends PagerAdapter {

    private boolean isMultiScr;
    private ArrayList<IntroSlide> slides;

    public UltraPagerAdapter(boolean isMultiScr, ArrayList<IntroSlide> slides) {
        this.isMultiScr = isMultiScr;
        this.slides = slides;
    }

    @Override
    public int getCount() {
        return slides.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Context context = container.getContext();
        RelativeLayout relativeLayout = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.intro_slide, null);
        TextView textView = (TextView) relativeLayout.findViewById(R.id.introText);
        textView.setText(slides.get(position).text);
        textView.setTypeface(thinTypeface);
        ImageView image = (ImageView) relativeLayout.findViewById(R.id.introBackground);
        image.setImageResource(slides.get(position).image);
        Button contBut = (Button) relativeLayout.findViewById(R.id.introContinue);
        contBut.setAlpha(0L);
        contBut.setEnabled(false);
        if(position == getCount() - 1){
            Animation animation = AnimationUtils.loadAnimation(
                    context, R.anim.intro_button_appear);
            contBut.startAnimation(animation);
            contBut.setEnabled(true);
            contBut.setTypeface(boldItalicTypeface);
            contBut.setOnClickListener(v -> {
                MainPresenter.changeFragment(new TaskFragment(), false, true, false);
                Settings.setFirstOpened(false);
            });
            contBut.setAlpha(1L);
        }
        container.addView(relativeLayout);
        return relativeLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        RelativeLayout view = (RelativeLayout) object;
        container.removeView(view);
    }
}