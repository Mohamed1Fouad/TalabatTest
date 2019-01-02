package test.talabat.com.talabattest.data.network.model;

import com.google.gson.annotations.SerializedName;

import test.talabat.com.talabattest.data.db.model.User;

public class LoginResponse extends BaseResponse {
    @SerializedName("return")
    private
    User user;


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
