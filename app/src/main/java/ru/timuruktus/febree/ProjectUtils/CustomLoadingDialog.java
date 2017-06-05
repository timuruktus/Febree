package ru.timuruktus.febree.ProjectUtils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import ru.timuruktus.febree.MainPart.MainActivity;
import ru.timuruktus.febree.R;

import static android.graphics.Color.BLACK;
import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

public class CustomLoadingDialog {



    private Dialog dialog;
    private Button button;
    private TextView firstTextView;
    private TextView secondTextView;
    private TextView titleTextView;
    public static final int DEFAULT_TEXT_OFFSET_APPEARANCE = 5000;


    public CustomLoadingDialog buildDialog(Context context){
        dialog = new Dialog(context, R.style.CustomDialogStyle);
        dialog.setContentView(R.layout.dialog_loading);
        Window window = dialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = MATCH_PARENT;
        lp.height = MATCH_PARENT;
        window.setAttributes(lp);
        window.setGravity(Gravity.CENTER);
        setFirstTextColor(BLACK);
        setSecondTextColor(BLACK);
        return this;
    }

    public CustomLoadingDialog buildDialog(){
        return this.buildDialog(MainActivity.getInstance());
    }

    public CustomLoadingDialog addTextAfterPause(String text, int timeOffset){
        final Handler handler = new Handler();
        final Runnable task = () -> {
            CustomLoadingDialog.this.setSecondText(text);
            CustomLoadingDialog.this.setDismissOnClick(true);
        };
        handler.postDelayed(task, timeOffset);
        return this;
    }


    public CustomLoadingDialog setDismissOnClick(boolean enable){
        View dialogBackground = dialog.findViewById(R.id.dialogBlurBackgroundLoading);
        if(enable){
            dialogBackground.setOnClickListener(v -> dialog.dismiss());
        }else{
            dialogBackground.setOnClickListener(null);
        }
        return this;
    }

    public CustomLoadingDialog setTitle(String text){
        titleTextView = (TextView) dialog.findViewById(R.id.dialogTitleLoading);
        titleTextView.setText(text);
        return this;
    }

    public CustomLoadingDialog setFirstText(String text){
        firstTextView = (TextView) dialog.findViewById(R.id.dialogFirstTextLoading);
        firstTextView.setText(text);
        return this;
    }

    public CustomLoadingDialog setSecondText(String text){
        secondTextView = (TextView) dialog.findViewById(R.id.dialogSecondTextLoading);
        secondTextView.setText(text);
        return this;
    }

    public CustomLoadingDialog setTitleColor(int color){
        titleTextView = (TextView) dialog.findViewById(R.id.dialogTitleLoading);
        titleTextView.setTextColor(color);
        return this;
    }

    public CustomLoadingDialog setFirstTextColor(int color){
        firstTextView = (TextView) dialog.findViewById(R.id.dialogFirstTextLoading);
        firstTextView.setTextColor(color);
        return this;
    }

    public CustomLoadingDialog setSecondTextColor(int color){
        secondTextView  = (TextView) dialog.findViewById(R.id.dialogSecondTextLoading);
        secondTextView.setTextColor(color);
        return this;
    }

    public void show(){
        dialog.show();
    }

    public void dismiss(){
        dialog.dismiss();
    }


}
