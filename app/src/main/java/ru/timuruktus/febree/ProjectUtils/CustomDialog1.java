package ru.timuruktus.febree.ProjectUtils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import ru.timuruktus.febree.R;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

public class CustomDialog1 {

    private Dialog dialog;
    private Button button;
    private TextView firstTextView;
    private TextView secondTextView;
    private TextView titleTextView;


    public CustomDialog1 buildDialog(Context context){
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_with_1_button);
        View dialogBackground = dialog.findViewById(R.id.dialogBlurBackground1);
        dialogBackground.setOnClickListener(v -> dialog.dismiss());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = MATCH_PARENT;
        //lp.height = MATCH_PARENT;
        dialog.getWindow().setAttributes(lp);
        return this;
    }

    public CustomDialog1 setOnClickListener(View.OnClickListener listener){
        button.setOnClickListener(listener);
        return this;
    }

    public CustomDialog1 setTitle(String text){
        titleTextView = (TextView) dialog.findViewById(R.id.dialogTitle1);
        titleTextView.setText(text);
        return this;
    }

    public CustomDialog1 setFirstText(String text){
        firstTextView = (TextView) dialog.findViewById(R.id.dialogFirstText1);
        firstTextView.setText(text);
        return this;
    }

    public CustomDialog1 setSecondText(String text){
        secondTextView = (TextView) dialog.findViewById(R.id.dialogSecondText1);
        secondTextView.setText(text);
        return this;
    }

    public CustomDialog1 setButtonText(String text){
        button = (Button) dialog.findViewById(R.id.dialogButton1);
        button.setText(text);
        return this;
    }

    public CustomDialog1 setTitleColor(int color){
        titleTextView = (TextView) dialog.findViewById(R.id.dialogTitle1);
        titleTextView.setTextColor(color);
        return this;
    }

    public CustomDialog1 setFirstTextColor(int color){
        firstTextView = (TextView) dialog.findViewById(R.id.dialogFirstText1);
        firstTextView.setTextColor(color);
        return this;
    }

    public CustomDialog1 setSecondTextColor(int color){
        secondTextView  = (TextView) dialog.findViewById(R.id.dialogSecondText1);
        secondTextView.setTextColor(color);
        return this;
    }

    public CustomDialog1 setButtonBackground(int resId){
        button = (Button) dialog.findViewById(R.id.dialogButton1);
        button.setBackgroundResource(resId);
        return this;
    }

    public void show(){
        dialog.show();
    }

    public void dismiss(){
        dialog.dismiss();
    }



}
