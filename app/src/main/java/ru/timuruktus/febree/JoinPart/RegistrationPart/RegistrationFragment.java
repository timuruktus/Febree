package ru.timuruktus.febree.JoinPart.RegistrationPart;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import ru.timuruktus.febree.MainPart.MainPresenter;
import ru.timuruktus.febree.R;
import ru.timuruktus.febree.JoinPart.RegistrationPart.Interfaces.BaseRegistrationPresenter;
import ru.timuruktus.febree.JoinPart.RegistrationPart.Interfaces.BaseRegistrationView;

public class RegistrationFragment extends Fragment implements BaseRegistrationView {


    private View rootView;
    private BaseRegistrationPresenter presenter;
    private static final int NAV_MENU_ID = R.id.nav_login;
    private Context context;


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(
                R.layout.registration_fragment, container, false);
        context = rootView.getContext();
        presenter = new RegistrationPresenter(this);
        Button regConfirmRegistration = (Button) rootView.findViewById(R.id.regConfirmRegistration);
        regConfirmRegistration.setOnClickListener(presenter.getRegButClickListener());


        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        String title = context.getResources().getString(R.string.reg_fragment);
        MainPresenter.changeToolbarTitle(title);
        MainPresenter.changeToolbarVisibility(MainPresenter.DONT_HIDE_TOOLBAR);
        MainPresenter.setCheckedItemInNavMenu(NAV_MENU_ID);
    }

    @Override
    public String getEmail() {
        EditText regEmailEditText = (EditText) rootView.findViewById(R.id.regEmailEditText);
        return regEmailEditText.getText().toString();
    }

    @Override
    public String getPassword() {
        EditText regPassEditText = (EditText) rootView.findViewById(R.id.regPassEditText);
        return regPassEditText.getText().toString();
    }

    @Override
    public String getLogin() {
        EditText regLoginEditText = (EditText) rootView.findViewById(R.id.regLoginEditText);
        return regLoginEditText.getText().toString();
    }

    @Override
    public String getPassConfirm() {
        EditText regConfirmPassEditText = (EditText) rootView.findViewById(R.id.regConfirmPassEditText);
        return regConfirmPassEditText.getText().toString();
    }
}
