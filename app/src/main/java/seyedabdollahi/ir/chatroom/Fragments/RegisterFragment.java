package seyedabdollahi.ir.chatroom.Fragments;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import seyedabdollahi.ir.chatroom.Data.RegisterUserController;
import seyedabdollahi.ir.chatroom.Keys.Actions;
import seyedabdollahi.ir.chatroom.Responses.TokenResponse;
import seyedabdollahi.ir.chatroom.Models.User;
import seyedabdollahi.ir.chatroom.MyPreferenceManager;
import seyedabdollahi.ir.chatroom.R;

public class RegisterFragment extends android.app.Fragment {

    private EditText edtUsername;
    private EditText edtPassword;
    private EditText edtEmail;
    private Button btnRegister;
    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.register_fragment , container , false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
        configViews();
        progressBar.setVisibility(View.INVISIBLE);
        progressBar.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.blue) , PorterDuff.Mode.SRC_IN);
    }

    private void findViews(View view){
        edtUsername = view.findViewById(R.id.username);
        edtPassword = view.findViewById(R.id.password);
        edtEmail = view.findViewById(R.id.email);
        btnRegister = view.findViewById(R.id.btn_register);
        progressBar = view.findViewById(R.id.register_progress_bar);
    }

    private void configViews(){
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    private void registerUser(){
        progressBar.setVisibility(View.VISIBLE);
        RegisterUserController registerUserController = new RegisterUserController(registerUserCallback);
        User user = new User();
        user.setUsername(edtUsername.getText().toString());
        user.setPassword(edtPassword.getText().toString());
        user.setEmail(edtEmail.getText().toString());
        registerUserController.start(user);
    }

    private void loginUser(){
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

    ChatRoomAPI.LoginUserCallback loginUserCallback =
            new ChatRoomAPI.LoginUserCallback() {
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

    ChatRoomAPI.RegisterUserCallback registerUserCallback =
            new ChatRoomAPI.RegisterUserCallback() {
                @Override
                public void onResponse(boolean successful , String errorMessage , User user) {
                    if(successful){
                        loginUser();
                    }else {
                        sendBroadcast(false , errorMessage);
                    }
                }

                @Override
                public void onFailure() {
                    sendBroadcast(false , getString(R.string.error_on_failure));
                }
            };

}
