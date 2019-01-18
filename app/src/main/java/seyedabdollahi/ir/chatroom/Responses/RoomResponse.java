package seyedabdollahi.ir.chatroom.Responses;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import seyedabdollahi.ir.chatroom.Models.Room;

public class RoomResponse {

    @SerializedName("results")
    private List<Room> rooms;

    public RoomResponse() {
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }
}
