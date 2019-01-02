package test.talabat.com.talabattest.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import test.talabat.com.talabattest.R;
import test.talabat.com.talabattest.ui.restaurants.RestaurantsListActivity;
import test.talabat.com.talabattest.data.db.model.User;
import test.talabat.com.talabattest.data.network.model.LoginRequest;
import test.talabat.com.talabattest.utils.Utils;
import test.talabat.com.talabattest.view.custom.DialogProgress;

public class LoginActivity extends AppCompatActivity implements LoginView {
    @BindView(R.id.mobileEditText)
    EditText mobileEditText;
    @BindView(R.id.passEditText)
    EditText passEditText;
    private DialogProgress dialogProgress;
    private LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    //init view and presenter
    public void init() {
        ButterKnife.bind(this);
        presenter = new LoginPresenter(this);
        dialogProgress = new DialogProgress(this);

    }

    @OnClick(R.id.loginBtn)
    public void onLoginCLicked() {
        showProgressBar();
        String mobileNumber = mobileEditText.getText().toString();
        String pass = passEditText.getText().toString();
        // Check for the mobile if it's empty or not
        if (mobileNumber.isEmpty()) {
            mobileEditText.setError(getResources().getString(R.string.empty_mobile));
            hideProgressBar();
            return;
        }
        // check for the password empty or not not
        else if (pass.isEmpty()) {
            passEditText.setError(getResources().getString(R.string.empty_password));
            hideProgressBar();
            return;
        }

        //before login check for internet connection and google service to use google maps
        if (Utils.isNetworkConnected(this) && Utils.checkPlayServices(this)) {
            presenter.login(new LoginRequest(mobileNumber,pass,getResources().getString(R.string.access_key),getResources().getString(R.string.access_password)));
        } else if (!Utils.isNetworkConnected(this)) {
            hideProgressBar();
            displayError(getResources().getString(R.string.no_internet_connection));
        } else if (!Utils.checkPlayServices(this)){
            hideProgressBar();
            displayError(getResources().getString(R.string.no_google_serivce));
        }
    }

    @Override
    public void onSuccess(User user) {

        startActivity(new Intent(LoginActivity.this,RestaurantsListActivity.class));
        finish();
    }

    @Override
    public void showToast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgressBar() {
        dialogProgress.show();
    }

    @Override
    public void hideProgressBar() {
        dialogProgress.hide();

    }

    @Override
    public void displayError(String s) {
        showToast(s);
    }
}
