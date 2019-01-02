package test.talabat.com.talabattest.data.network.model;

import com.google.gson.annotations.SerializedName;

public class BaseResponse {
    @SerializedName("status")
    private
    int status;
    @SerializedName("sub_message")
    private
    String sub_message;
    @SerializedName("message")
    private String message;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getSub_message() {
        return sub_message;
    }

    public void setSub_message(String sub_message) {
        this.sub_message = sub_message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}