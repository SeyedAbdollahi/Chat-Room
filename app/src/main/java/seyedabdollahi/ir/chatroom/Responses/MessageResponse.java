package seyedabdollahi.ir.chatroom.Responses;

import com.google.gson.annotations.SerializedName;

public class MessageResponse {
    private String createdAt;
    @SerializedName("_id")
    private String id;

    public String getCreatedAt() {
        return createdAt;
    }

    public String getId() {
        return id;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void setId(String id) {
        this.id = id;
    }
}
