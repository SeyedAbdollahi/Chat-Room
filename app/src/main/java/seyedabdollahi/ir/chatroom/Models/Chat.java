package seyedabdollahi.ir.chatroom.Models;

import com.google.gson.annotations.SerializedName;

public class Chat {
    private String username;
    private String text;
    @SerializedName("createdAt")
    private String textTime;
    private String dayTime;

    public Chat() {
    }

    public String getUsername() {
        return username;
    }

    public String getText() {
        return text;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTextTime() {
        return textTime;
    }

    public void setTextTime(String time) {
        this.textTime = time;
    }

    public String getDayTime() {
        return dayTime;
    }

    public void setDayTime(String dayTime) {
        this.dayTime = dayTime;
    }
}
