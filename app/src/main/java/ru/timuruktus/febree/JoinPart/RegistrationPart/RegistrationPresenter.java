package ru.timuruktus.febree.JoinPart.RegistrationPart;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

import java.util.HashMap;

import ru.timuruktus.febree.ContentPart.StepsFragment;
import ru.timuruktus.febree.JoinPart.RegistrationPart.Interfaces.BaseRegistrationModel;
import ru.timuruktus.febree.JoinPart.RegistrationPart.Interfaces.BaseRegistrationPresenter;
import ru.timuruktus.febree.JoinPart.RegistrationPart.Interfaces.BaseRegistrationView;
import ru.timuruktus.febree.MainPart.MainActivity;
import ru.timuruktus.febree.MainPart.MainPresenter;
import ru.timuruktus.febree.ProjectUtils.CustomLoadingDialog;
import ru.timuruktus.febree.R;

import static ru.timuruktus.febree.BaseModel.FIRST_TEXT;
import static ru.timuruktus.febree.BaseModel.SECOND_TEXT;
import static ru.timuruktus.febree.BaseModel.TITLE_COLOR;
import static ru.timuruktus.febree.BaseModel.TITLE_TEXT;

public class RegistrationPresenter implements BaseRegistrationPresenter {


    private BaseRegistrationView view;
    private BaseRegistrationModel model;
    private static final int USER_ALREADY_EXISTS = 3033;

    RegistrationPresenter(BaseRegistrationView view){
        this.view = view;
        model = new RegistrationModel(this);
    }


    @Override
    public View.OnClickListener getRegButClickListener() {
        return v-> {
            String email = view.getEmail();
            String login = view.getLogin();
            String passwordConfirm = view.getPassConfirm();
            String password = view.getPassword();
            if (login.equals("") || password.equals("") || email.equals("")) {
                Toast.makeText(MainActivity.getInstance(), R.string.reg_empty_values, Toast.LENGTH_SHORT).show();
                return;
            }
            if(!password.equals(passwordConfirm)){
                Toast.makeText(MainActivity.getInstance(), R.string.reg_pass_confirm_false, Toast.LENGTH_SHORT).show();
                return;
            }
            if(password.length() < 6){
                Toast.makeText(MainActivity.getInstance(), R.string.reg_short_pass, Toast.LENGTH_SHORT).show();
                return;
            }
            CustomLoadingDialog dialog = constructDialog();
            BackendlessUser user = new BackendlessUser();
            user.setPassword(password);
            user.setProperty("email", email);
            user.setProperty("name", login);
            Backendless.UserService.register(user, getRegisterCallback(dialog));
        };
    }

    private CustomLoadingDialog constructDialog(){
        HashMap<String, String> textForDialog = model.getTextForLoadingDialog();
        HashMap<String, Integer> colorsForDialog = model.getColorsForLoadingDialog();
        CustomLoadingDialog dialog = new CustomLoadingDialog();
        dialog.buildDialog()
                .setFirstText(textForDialog.get(FIRST_TEXT))
                .setTitle(textForDialog.get(TITLE_TEXT))
                .setTitleColor(colorsForDialog.get(TITLE_COLOR))
                .setDismissOnClick(false)
                .addTextAfterPause(textForDialog.get(SECOND_TEXT), CustomLoadingDialog.DEFAULT_TEXT_OFFSET_APPEARANCE)
                .show();
        return dialog;
    }

    private AsyncCallback<BackendlessUser> getRegisterCallback(CustomLoadingDialog dialog){
        return new AsyncCallback<BackendlessUser>() {
            @Override
            public void handleResponse(BackendlessUser response) {
                dialog.dismiss();
                MainPresenter.changeFragment(new StepsFragment(),
                        MainPresenter.DONT_ADD_TO_BACKSTACK,
                        MainPresenter.REFRESH,
                        MainPresenter.DONT_HIDE_TOOLBAR);
                Toast.makeText(MainActivity.getInstance(), R.string.reg_success_message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                dialog.dismiss();
                int errorCode = Integer.valueOf(fault.getCode());
                Log.d("mytag", "Auth Fault. Error code = " + errorCode);
                if(errorCode == USER_ALREADY_EXISTS){
                    Toast.makeText(MainActivity.getInstance(), R.string.reg_user_already_exists, Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(MainActivity.getInstance(), R.string.error, Toast.LENGTH_SHORT).show();
            }
        };
    }
}
