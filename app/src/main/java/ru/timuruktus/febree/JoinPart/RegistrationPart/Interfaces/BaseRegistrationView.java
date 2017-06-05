package ru.timuruktus.febree.JoinPart.RegistrationPart.Interfaces;

import ru.timuruktus.febree.BaseFragment;

public interface BaseRegistrationView extends BaseFragment {

    String getEmail();
    String getPassword();
    String getLogin();
    String getPassConfirm();
}
