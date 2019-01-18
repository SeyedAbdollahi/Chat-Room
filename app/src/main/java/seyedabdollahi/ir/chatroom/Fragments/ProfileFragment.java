package seyedabdollahi.ir.chatroom.Fragments;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import seyedabdollahi.ir.chatroom.Dialogs.LogoutDialog;
import seyedabdollahi.ir.chatroom.Keys.Actions;
import seyedabdollahi.ir.chatroom.Listeners.OnClickLogoutListener;
import seyedabdollahi.ir.chatroom.MyPreferenceManager;
import seyedabdollahi.ir.chatroom.R;

public class ProfileFragment extends Fragment {

    private TextView username;
    private Button logout;
    private Dialog logoutDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.profile_fragment , container , false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
        configViews();
        username.setText(MyPreferenceManager.getInstance(getActivity()).getUserName());
    }

    private void findViews(View view){
        username = view.findViewById(R.id.username);
        logout = view.findViewById(R.id.logout);
    }

    private void configViews(){
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutDialog = new LogoutDialog(getActivity(), new OnClickLogoutListener() {
                    @Override
                    public void onClickYes() {
                        logoutDialog.dismiss();
                        MyPreferenceManager.getInstance(getActivity()).clearEverything();
                        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(new Intent(Actions.ON_CLICK_BTN_LOGOUT));
                    }

                    @Override
                    public void onClickNo() {
                        logoutDialog.dismiss();
                    }
                });
                logoutDialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // for old Api
                logoutDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                logoutDialog.show();
            }
        });
    }
}
