package seyedabdollahi.ir.chatroom.Data;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import seyedabdollahi.ir.chatroom.Models.Room;
import seyedabdollahi.ir.chatroom.Responses.RoomResponse;

public class RoomsController {
    ChatRoomAPI.getRoomsCallback getRoomsCallback;

    public RoomsController(ChatRoomAPI.getRoomsCallback getRoomsCallback) {
        this.getRoomsCallback = getRoomsCallback;
    }

    public void start(String authorization){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ChatRoomAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ChatRoomAPI chatRoomAPI = retrofit.create(ChatRoomAPI.class);
        Call<RoomResponse> call = chatRoomAPI.getRooms(authorization);
        call.enqueue(new Callback<RoomResponse>() {
            @Override
            public void onResponse(Call<RoomResponse> call, Response<RoomResponse> response) {
                if (response.isSuccessful()){
                    for (Room rooms : response.body().getRooms()){
                        if (rooms.getName() == null){
                            rooms.setName("No_name");
                        }
                    }
                    getRoomsCallback.onResponse(true , response.body().getRooms());
                }
                else {
                    getRoomsCallback.onResponse(false , null);
                }
            }
            @Override
            public void onFailure(Call<RoomResponse> call, Throwable t) {
                getRoomsCallback.onFailure();
            }
        });
    }
}
