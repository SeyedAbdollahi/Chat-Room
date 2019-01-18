package seyedabdollahi.ir.chatroom.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;

import seyedabdollahi.ir.chatroom.Listeners.OnClickLogoutListener;
import seyedabdollahi.ir.chatroom.R;

public class LogoutDialog extends Dialog{

    OnClickLogoutListener listener;
    private int width;
    private int height;
    private Button yesBtn;
    private Button noBtn;

    public LogoutDialog(@NonNull Context context, OnClickLogoutListener listener) {
        super(context);
        this.listener = listener;
        setWidthHeight(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logout_dialog);
        findViews();
        configViews();
        getWindow().setLayout(width , height);
    }

    private void setWidthHeight(Context context){
        width = (int) (context.getResources().getDisplayMetrics().widthPixels * 0.90);
        height = (int) (context.getResources().getDisplayMetrics().heightPixels * 0.26);
    }

    private void findViews(){
        yesBtn = findViewById(R.id.btn_yes);
        noBtn = findViewById(R.id.btn_no);
    }

    private void configViews(){
        yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickYes();
            }
        });
        noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickNo();
            }
        });
    }
}
