package seyedabdollahi.ir.chatroom.Data;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import seyedabdollahi.ir.chatroom.Models.ChatRoom;
import seyedabdollahi.ir.chatroom.Responses.CreateChatRoomResponse;

public class CreateChatRoomController {

    ChatRoomAPI.createRoomCallback createRoomCallback;

    public CreateChatRoomController(ChatRoomAPI.createRoomCallback createRoomCallback) {
        this.createRoomCallback = createRoomCallback;
    }

    public void start(String authorization , ChatRoom chatRoom){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ChatRoomAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final ChatRoomAPI chatRoomAPI = retrofit.create(ChatRoomAPI.class);
        Call<CreateChatRoomResponse> call = chatRoomAPI.createChatRoom(authorization , chatRoom);
        call.enqueue(new Callback<CreateChatRoomResponse>() {
            @Override
            public void onResponse(Call<CreateChatRoomResponse> call, Response<CreateChatRoomResponse> response) {
                if (response.isSuccessful()){
                    createRoomCallback.onResponse(true);
                }
                else {
                    createRoomCallback.onResponse(false);
                }
            }

            @Override
            public void onFailure(Call<CreateChatRoomResponse> call, Throwable t) {
                createRoomCallback.onFailure();
            }
        });
    }
}
