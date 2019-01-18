package seyedabdollahi.ir.chatroom.Keys;

public class Backtory {
    //************************************************************************************************************
    public static final String BASE_URL = "https://api.backtory.com/";
    public static final String AUTHENTICATION_ID = "X-Backtory-Authentication-Id:5a1d4b3de4b0afa16474fabd";
    public static final String AUTHENTICATION_KEY = "X-Backtory-Authentication-Key:5a1d4b3de4b0ce09cd4655c8";
    public static final String OBJECT_STORAGE_ID = "X-Backtory-Object-Storage-Id:5a1d4b3de4b03ffa047badf5";
    //************************************************************************************************************
    public static final String POST_REGISTER_USER = "auth/users";
    public static final String POST_LOGIN_USER = "auth/login";
    public static final String POST_GET_ROOMS = "object-storage/classes/query/Room";
    public static final String POST_CREATE_CHAT_ROOM = "object-storage/classes/Room";
    public static final String POST_GET_CHATS = "object-storage/classes/query/Message";
    public static final String POST_SEND_MESSAGE = "object-storage/classes/Message";
}
