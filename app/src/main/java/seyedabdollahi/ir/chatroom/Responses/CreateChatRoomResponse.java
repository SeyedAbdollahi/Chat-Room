package seyedabdollahi.ir.chatroom.Responses;

import com.google.gson.annotations.SerializedName;

public class CreateChatRoomResponse {

    private String createAt;
    @SerializedName("_id")
    private String id;

    public CreateChatRoomResponse() {
    }

    public String getCreateAt() {
        return createAt;
    }

    public String getId() {
        return id;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public void setId(String id) {
        this.id = id;
    }
}
