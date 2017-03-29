package ru.timuruktus.febree.IntroducingPart;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import ru.timuruktus.febree.BaseEvent;
import ru.timuruktus.febree.BaseFragment;
import ru.timuruktus.febree.MainPart.EChangeFragment;
import ru.timuruktus.febree.R;
import ru.timuruktus.febree.WelcomePart.WelcomeFragment;

import static ru.timuruktus.febree.MainPart.MainPresenter.*;

public class IntroducingFragment extends BaseFragment implements View.OnClickListener {

    public View rootView;
    public TextView introducingText, hello;
    public Context context;
    public Button understoodBut;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rootView =
                inflater.inflate(R.layout.introducing_fragment, container, false);

        this.context = rootView.getContext();

        hello = (TextView) rootView.findViewById(R.id.hello);
        introducingText = (TextView) rootView.findViewById(R.id.introducingText);
        understoodBut = (Button) rootView.findViewById(R.id.understoodBut);
        introducingText.setTypeface(Typeface.createFromAsset(context.getAssets(),
                "OpenSans.ttf"));
        startAnim(hello, 0);
        startAnim(introducingText, 2000);
        startAnim(understoodBut, 3000);
        understoodBut.setOnClickListener(this);
        return rootView;
    }

    private void startAnim(View view, long delay){
        ViewCompat.animate(view)
                .setDuration(2000L)
                .alphaBy(0f)
                .alpha(1f)
                .setStartDelay(delay)
                .start();
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.understoodBut){
            EventBus.getDefault().post(new EChangeFragment(new WelcomeFragment(), DONT_ADD_TO_BACKSTACK,
                    HIDE_TOOLBAR, HIDE_MENU));
        }
    }

    @Override
    public void eventCallback(BaseEvent event) {

    }
}
