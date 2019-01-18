package seyedabdollahi.ir.chatroom.Data;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import seyedabdollahi.ir.chatroom.Keys.Backtory;
import seyedabdollahi.ir.chatroom.Models.Chat;
import seyedabdollahi.ir.chatroom.Models.ChatId;
import seyedabdollahi.ir.chatroom.Responses.ChatResponse;
import seyedabdollahi.ir.chatroom.Models.ChatRoom;
import seyedabdollahi.ir.chatroom.Responses.CreateChatRoomResponse;
import seyedabdollahi.ir.chatroom.Models.Message;
import seyedabdollahi.ir.chatroom.Models.Room;
import seyedabdollahi.ir.chatroom.Responses.MessageResponse;
import seyedabdollahi.ir.chatroom.Responses.RoomResponse;
import seyedabdollahi.ir.chatroom.Responses.TokenResponse;
import seyedabdollahi.ir.chatroom.Models.User;

public interface ChatRoomAPI {
    String BASE_URL = Backtory.BASE_URL;

    //****************************************************************************
    @Headers(Backtory.AUTHENTICATION_ID)
    @POST(Backtory.POST_REGISTER_USER)
    Call<User> registerUser(@Body User user);
    //****************************************************************************
    @Headers({Backtory.AUTHENTICATION_ID , Backtory.AUTHENTICATION_KEY})
    @FormUrlEncoded
    @POST(Backtory.POST_LOGIN_USER)
    Call<TokenResponse> loginUser(
            @Field("username") String username ,
            @Field("password") String password
    );
    //****************************************************************************
    @Headers(Backtory.OBJECT_STORAGE_ID)
    @POST(Backtory.POST_GET_ROOMS)
    Call<RoomResponse> getRooms(
            @Header("Authorization") String authorization
    );
    //****************************************************************************
    @Headers(Backtory.OBJECT_STORAGE_ID)
    @POST(Backtory.POST_CREATE_CHAT_ROOM)
    Call<CreateChatRoomResponse> createChatRoom(
      @Header("Authorization") String authorization ,
      @Body ChatRoom chatRoom
    );
    //****************************************************************************
    @Headers(Backtory.OBJECT_STORAGE_ID)
    @POST(Backtory.POST_GET_CHATS)
    Call<ChatResponse> getChats(
      @Header("Authorization") String authorization ,
      @Body ChatId chatId
    );
    //****************************************************************************
    @Headers(Backtory.OBJECT_STORAGE_ID)
    @POST(Backtory.POST_SEND_MESSAGE)
    Call<MessageResponse> sendMessage(
            @Header("Authorization") String authorization ,
            @Body Message message
    );

    interface RegisterUserCallback{
        void onResponse(boolean successful , String errorMessage ,User user);
        void onFailure();
    }

    interface LoginUserCallback{
        void onResponse(boolean successful , String errorDescription , TokenResponse tokenResponse);
        void onFailure();
    }

    interface getRoomsCallback{
        void onResponse(boolean successful , List<Room> roomList);
        void onFailure();
    }

    interface createRoomCallback{
        void onResponse(boolean successful);
        void onFailure();
    }

    interface getChatCallback{
        void onResponse(boolean successful , List<Chat> chats);
        void onFailure();
    }

    interface sendMessageCallback{
        void onResponse(boolean successful , MessageResponse messageResponse);
        void onFailure();
    }
}
