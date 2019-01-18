package seyedabdollahi.ir.chatroom.Data;

import com.google.gson.Gson;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import seyedabdollahi.ir.chatroom.Responses.ErrorRegisterResponse;
import seyedabdollahi.ir.chatroom.Models.User;

public class RegisterUserController {

    ChatRoomAPI.RegisterUserCallback registerUserCallback;

    public RegisterUserController(ChatRoomAPI.RegisterUserCallback registerUserCallback) {
        this.registerUserCallback = registerUserCallback;
    }

    public void start(final User user){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ChatRoomAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ChatRoomAPI chatRoomAPI = retrofit.create(ChatRoomAPI.class);
        Call<User> call = chatRoomAPI.registerUser(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()){
                    registerUserCallback.onResponse(true , null ,response.body());
                }else {
                    try{
                        String errorBodyJson = response.errorBody().string();
                        Gson gson = new Gson();
                        ErrorRegisterResponse errorResponse = gson.fromJson(errorBodyJson , ErrorRegisterResponse.class);
                        registerUserCallback.onResponse(false , errorResponse.getMessage() , null);
                    }
                    catch (Exception e){
                        registerUserCallback.onResponse(false , "UnknownError", null);
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                registerUserCallback.onFailure();
            }
        });
    }
}
