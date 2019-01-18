package seyedabdollahi.ir.chatroom.Responses;

public class ErrorRegisterResponse {
    private String name;
    private String message;

    public ErrorRegisterResponse() {
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
