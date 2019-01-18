package seyedabdollahi.ir.chatroom.Fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import seyedabdollahi.ir.chatroom.Keys.Actions;
import seyedabdollahi.ir.chatroom.Keys.Keys;
import seyedabdollahi.ir.chatroom.R;

public class WelcomeFragment extends Fragment {

    private Button btnLogin;
    private Button btnRegister;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.welcome_fragment , container , false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
        configViews();
    }

    private void findViews(View view){
        btnLogin = view.findViewById(R.id.welcome_btn_login);
        btnRegister = view.findViewById(R.id.welcome_btn_register);
    }

    private void configViews(){
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(new Intent(Actions.ON_CLICK_BTN_LOGIN));
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(new Intent(Actions.ON_CLICK_BTN_REGISTER));
            }
        });
    }
}
