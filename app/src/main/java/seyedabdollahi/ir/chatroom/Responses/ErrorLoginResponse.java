package seyedabdollahi.ir.chatroom.Responses;

import com.google.gson.annotations.SerializedName;

public class ErrorLoginResponse {

    @SerializedName("error_description")
    private String errorDescription;

    public ErrorLoginResponse() {
    }

    public String getErrorDescription() {
        if(errorDescription.equals("Either username or password is not correct.")){
            return "username or password is not correct.";
        }
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }
}
