package ru.timuruktus.febree.JoinPart.LoginPart;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import ru.timuruktus.febree.JoinPart.LoginPart.Interfaces.BaseLoginFragment;
import ru.timuruktus.febree.JoinPart.LoginPart.Interfaces.BaseLoginPresenter;
import ru.timuruktus.febree.JoinPart.RegistrationPart.Interfaces.BaseRegistrationPresenter;
import ru.timuruktus.febree.JoinPart.RegistrationPart.RegistrationFragment;
import ru.timuruktus.febree.JoinPart.RegistrationPart.RegistrationPresenter;
import ru.timuruktus.febree.MainPart.MainPresenter;
import ru.timuruktus.febree.R;

public class LoginFragment extends Fragment implements BaseLoginFragment {


    private View rootView;
    private BaseLoginPresenter presenter;
    private static final int NAV_MENU_ID = R.id.nav_login;
    private Context context;


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(
                R.layout.login_fragment, container, false);
        context = rootView.getContext();
        presenter = new LoginPresenter(this);
        TextView goToRegistration = (TextView) rootView.findViewById(R.id.goToRegistration);
        goToRegistration.setOnClickListener(presenter.getRegClickListener());
        Button loginJoin = (Button) rootView.findViewById(R.id.loginJoin);
        loginJoin.setOnClickListener(presenter.getJoinClickListener());


        return rootView;
    }


    @Override
    public void onResume() {
        super.onResume();
        String title = context.getResources().getString(R.string.login_fragment);
        MainPresenter.changeToolbarTitle(title);
        MainPresenter.changeToolbarVisibility(MainPresenter.DONT_HIDE_TOOLBAR);
        MainPresenter.setCheckedItemInNavMenu(NAV_MENU_ID);
    }

    @Override
    public String getLogin() {
        EditText loginView = (EditText) rootView.findViewById(R.id.loginLoginEditText);
        return loginView.getText().toString();
    }

    @Override
    public String getPassword() {
        EditText passView = (EditText) rootView.findViewById(R.id.loginPassEditText);
        return passView.getText().toString();
    }
}
