package test.talabat.com.talabattest.data.network.model;

public class LoginRequest  {
    final String mobile;
    final String password;
    final String access_key;
    final String access_password;

    public LoginRequest(String mobile, String password, String access_key, String access_password) {
        this.mobile = mobile;
        this.password = password;
        this.access_key = access_key;
        this.access_password = access_password;

    }
}
