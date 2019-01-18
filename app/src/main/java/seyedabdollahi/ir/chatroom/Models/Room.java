package seyedabdollahi.ir.chatroom.Models;

import com.google.gson.annotations.SerializedName;

public class Room {
    @SerializedName("_id")
    private String id;
    private String name;

    public Room() {
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
