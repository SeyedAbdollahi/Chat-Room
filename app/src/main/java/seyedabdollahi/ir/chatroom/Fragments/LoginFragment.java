package seyedabdollahi.ir.chatroom.Fragments;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import seyedabdollahi.ir.chatroom.Data.ChatRoomAPI;
import seyedabdollahi.ir.chatroom.Data.LoginUserController;
import seyedabdollahi.ir.chatroom.Keys.Actions;
import seyedabdollahi.ir.chatroom.Responses.TokenResponse;
import seyedabdollahi.ir.chatroom.MyPreferenceManager;
import seyedabdollahi.ir.chatroom.R;

public class LoginFragment extends Fragment {

    private Button btnLogin;
    private EditText edtUsername;
    private EditText edtPassword;
    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.login_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
        configViews();
        progressBar.setVisibility(View.INVISIBLE);
        progressBar.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.blue) , PorterDuff.Mode.SRC_IN);
    }

    private void findViews(View view) {
        btnLogin = view.findViewById(R.id.login_btn_login);
        edtUsername = view.findViewById(R.id.login_edt_username);
        edtPassword = view.findViewById(R.id.login_edt_password);
        progressBar =view.findViewById(R.id.login_progress_bar);
    }

    private void configViews() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });
    }

    private void loginUser() {
        progressBar.setVisibility(View.VISIBLE);
        LoginUserController loginUserController = new LoginUserController(loginUserCallback);
        loginUserController.start(
                edtUsername.getText().toString(),
                edtPassword.getText().toString()
        );
    }

    private void sendBroadcast(boolean successful , String error){
        progressBar.setVisibility(View.INVISIBLE);
        Intent intent = new Intent(Actions.LOGIN_STATUS);
        intent.putExtra("successful" , successful);
        if (!successful){
            intent.putExtra("error" , error);
        }
        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
    }

    ChatRoomAPI.LoginUserCallback loginUserCallback = new ChatRoomAPI.LoginUserCallback() {
        @Override
        public void onResponse(boolean successful, String errorDescription, TokenResponse tokenResponse) {
            if(successful){
                MyPreferenceManager.getInstance(getActivity()).putUserName(edtUsername.getText().toString());
                MyPreferenceManager.getInstance(getActivity()).putAccessToken("Bearer " + tokenResponse.getAccessToken());
                sendBroadcast(true , null);
            }
            else {
                sendBroadcast(false , errorDescription);
            }
        }

        @Override
        public void onFailure() {
            sendBroadcast(false , getString(R.string.error_on_failure));
        }
    };
}
