package ru.timuruktus.febree.JoinPart.LoginPart;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

import java.util.HashMap;

import ru.timuruktus.febree.ContentPart.StepsFragment;
import ru.timuruktus.febree.JoinPart.LoginPart.Interfaces.BaseLoginFragment;
import ru.timuruktus.febree.JoinPart.LoginPart.Interfaces.BaseLoginModel;
import ru.timuruktus.febree.JoinPart.LoginPart.Interfaces.BaseLoginPresenter;
import ru.timuruktus.febree.JoinPart.RegistrationPart.RegistrationFragment;
import ru.timuruktus.febree.MainPart.MainActivity;
import ru.timuruktus.febree.MainPart.MainPresenter;
import ru.timuruktus.febree.ProjectUtils.CustomLoadingDialog;
import ru.timuruktus.febree.R;

import static ru.timuruktus.febree.BaseModel.FIRST_TEXT;
import static ru.timuruktus.febree.BaseModel.SECOND_TEXT;
import static ru.timuruktus.febree.BaseModel.TITLE_COLOR;
import static ru.timuruktus.febree.BaseModel.TITLE_TEXT;


public class LoginPresenter implements BaseLoginPresenter {

    private BaseLoginFragment view;
    private BaseLoginModel model;
    private static final int WAITING_TEXT_OFFSET_APPEARANCE = 3000;
    private static final int ACCOUNT_BLOCKED_ERROR = 3090;
    private static final int WRONG_INFO_ERROR = 3003;
    private static final int EMAIL_NOT_CONFIRMED = 3087;
    private static final boolean STAY_LOGGED_IN = true;

    LoginPresenter(BaseLoginFragment view){
        this.view = view;
        model = new LoginModel(this);
    }


    @Override
    public View.OnClickListener getRegClickListener() {
        return v -> MainPresenter.changeFragment(new RegistrationFragment(),
                MainPresenter.ADD_TO_BACKSTACK,
                MainPresenter.REFRESH,
                MainPresenter.DONT_HIDE_TOOLBAR);
    }

    @Override
    public View.OnClickListener getJoinClickListener() {

        return v -> {
            String login = view.getLogin();
            String password = view.getPassword();
            if(login.equals("") || password.equals("")){
                Toast.makeText(MainActivity.getInstance(), R.string.login_empty_values, Toast.LENGTH_SHORT).show();
                return;
            }
            CustomLoadingDialog dialog = constructDialog();
            Log.d("mytag", "LoginPresenter.getJoinClickListener login = " + login);
            Log.d("mytag", "LoginPresenter.getJoinClickListener password = " + password);
            Backendless.UserService.login(login,
                    password,
                    getJoinCallback(dialog),
                    STAY_LOGGED_IN);
        };
    }

    private AsyncCallback<BackendlessUser> getJoinCallback(CustomLoadingDialog dialog){
        return new AsyncCallback<BackendlessUser>() {
            @Override
            public void handleResponse(BackendlessUser response) {
                dialog.dismiss();
                MainPresenter.changeFragment(new StepsFragment(),
                        MainPresenter.DONT_ADD_TO_BACKSTACK,
                        MainPresenter.REFRESH,
                        MainPresenter.DONT_HIDE_TOOLBAR);
                Toast.makeText(MainActivity.getInstance(), R.string.login_success_message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                dialog.dismiss();
                int errorCode = Integer.valueOf(fault.getCode());
                Log.d("mytag", "Auth Fault. Error code = " + errorCode);
                if(errorCode == WRONG_INFO_ERROR){
                    Toast.makeText(MainActivity.getInstance(), R.string.login_error_wrong_info, Toast.LENGTH_SHORT).show();
                }else if(errorCode == ACCOUNT_BLOCKED_ERROR){
                    Toast.makeText(MainActivity.getInstance(), R.string.login_error_account_blocked, Toast.LENGTH_SHORT).show();
                }else if(errorCode == EMAIL_NOT_CONFIRMED){
                    Toast.makeText(MainActivity.getInstance(), R.string.login_email_not_confirmed, Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(MainActivity.getInstance(), R.string.error, Toast.LENGTH_SHORT).show();
                }
            }
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
                .addTextAfterPause(textForDialog.get(SECOND_TEXT), WAITING_TEXT_OFFSET_APPEARANCE)
                .show();
        return dialog;
    }
}
