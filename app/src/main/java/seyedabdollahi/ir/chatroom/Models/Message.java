package seyedabdollahi.ir.chatroom.Models;

public class Message {
    private String roomId;
    private String text;
    private String username;

    public Message() {
    }

    public String getRoomId() {
        return roomId;
    }

    public String getText() {
        return text;
    }

    public String getUsername() {
        return username;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
