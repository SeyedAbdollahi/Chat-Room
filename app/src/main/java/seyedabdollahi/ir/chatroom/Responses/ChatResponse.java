package seyedabdollahi.ir.chatroom.Responses;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import seyedabdollahi.ir.chatroom.Models.Chat;

public class ChatResponse {
    @SerializedName("results")
    private List<Chat> chats;

    public List<Chat> getChats() {
        return chats;
    }

    public void setChats(List<Chat> chats) {
        this.chats = chats;
    }
}
