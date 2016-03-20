package biz.growapp.baseandroidproject.network.response.base;

import com.google.gson.annotations.SerializedName;

public class ServerError {
    private String code;
    @SerializedName("name")
    private String message;
    private String stack;

    public ServerError(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }

    public String getStack() {
        return stack;
    }
}
