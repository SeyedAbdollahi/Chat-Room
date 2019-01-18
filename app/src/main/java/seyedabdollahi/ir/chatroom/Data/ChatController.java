package seyedabdollahi.ir.chatroom.Data;

import android.support.annotation.NonNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import seyedabdollahi.ir.chatroom.Models.ChatId;
import seyedabdollahi.ir.chatroom.Responses.ChatResponse;

public class ChatController {

    private ChatRoomAPI.getChatCallback chatCallback;

    public ChatController(ChatRoomAPI.getChatCallback chatCallback) {
        this.chatCallback = chatCallback;
    }

    public void start(String authorization , ChatId chatId){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ChatRoomAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ChatRoomAPI chatRoomAPI = retrofit.create(ChatRoomAPI.class);
        Call<ChatResponse> call = chatRoomAPI.getChats(authorization , chatId);
        call.enqueue(new Callback<ChatResponse>() {
            @Override
            public void onResponse(Call<ChatResponse> call, Response<ChatResponse> response) {
                if(response.isSuccessful()){
                    chatCallback.onResponse(true , response.body().getChats());
                }else {
                    chatCallback.onResponse(false , null);
                }
            }

            @Override
            public void onFailure(Call<ChatResponse> call, Throwable t) {
                chatCallback.onFailure();
            }
        });
    }
}
