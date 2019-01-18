package seyedabdollahi.ir.chatroom.Data;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import seyedabdollahi.ir.chatroom.Models.Message;
import seyedabdollahi.ir.chatroom.Responses.MessageResponse;

public class SendMessageController {
    ChatRoomAPI.sendMessageCallback sendMessageCallback;

    public SendMessageController(ChatRoomAPI.sendMessageCallback sendMessageCallback) {
        this.sendMessageCallback = sendMessageCallback;
    }

    public void start(String authorization , Message message){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ChatRoomAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ChatRoomAPI chatRoomAPI = retrofit.create(ChatRoomAPI.class);
        Call<MessageResponse> call = chatRoomAPI.sendMessage(authorization , message);
        call.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                if (response.isSuccessful()){
                    sendMessageCallback.onResponse(true , response.body());
                }else {
                    sendMessageCallback.onResponse(false , null);
                }
            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
                sendMessageCallback.onFailure();
            }
        });
    }
}
