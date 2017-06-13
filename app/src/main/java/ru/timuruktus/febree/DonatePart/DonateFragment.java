package ru.timuruktus.febree.DonatePart;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import ru.timuruktus.febree.StepsPart.StepsFragment;
import ru.timuruktus.febree.DonatePart.Interfaces.BaseDonatePresenter;
import ru.timuruktus.febree.DonatePart.Interfaces.BaseDonateView;
import ru.timuruktus.febree.LocalPart.Settings;
import ru.timuruktus.febree.MainPart.MainPresenter;
import ru.timuruktus.febree.R;

public class DonateFragment extends Fragment implements BaseDonateView {

    private View rootView;
    private BaseDonatePresenter presenter;
    private static final int NAV_MENU_ID = R.id.nav_donate;
    private Context context;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(
                R.layout.donate_fragment, container, false);
        context = rootView.getContext();

        presenter = new DonatePresenter(this);
        RelativeLayout donateBlock = (RelativeLayout) rootView.findViewById(R.id.donateBlock);
        donateBlock.setOnClickListener(presenter.getBlocksListener());


        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(!Settings.checkIfUserLogged()){
            MainPresenter.changeFragment(new StepsFragment(),
                    MainPresenter.DONT_ADD_TO_BACKSTACK,
                    MainPresenter.DONT_REFRESH,
                    MainPresenter.DONT_HIDE_TOOLBAR);
            Log.d("mytag", "DonateFragment.onCreate() should be closed" );
            return;
        }
        String title = context.getResources().getString(R.string.donate_fragment);
        MainPresenter.changeToolbarTitle(title);
        MainPresenter.changeToolbarVisibility(MainPresenter.DONT_HIDE_TOOLBAR);
        MainPresenter.setCheckedItemInNavMenu(NAV_MENU_ID);
    }
}
