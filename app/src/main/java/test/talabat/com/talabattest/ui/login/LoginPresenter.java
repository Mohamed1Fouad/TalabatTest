package test.talabat.com.talabattest.ui.login;


import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;
import test.talabat.com.talabattest.data.network.model.LoginRequest;
import test.talabat.com.talabattest.data.network.model.LoginResponse;
import test.talabat.com.talabattest.data.network.RetrofitClient;
import test.talabat.com.talabattest.data.network.RetrofitService;
import test.talabat.com.talabattest.utils.Constants;

public class LoginPresenter implements LoginPresenterListener {
    LoginView view;
    public LoginPresenter(LoginView view){
        this.view=view;
    }
    @Override
    public void login(LoginRequest loginRequest) {
        view.showProgressBar();
        RetrofitClient.getClient(Constants.BASE_URL).create(RetrofitService.class)
                .login(loginRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(getObserver());
    }

    public DisposableObserver<Response<LoginResponse>> getObserver(){
        return new DisposableObserver<Response<LoginResponse>>() {

            @Override
            public void onNext(@NonNull Response<LoginResponse> loginResponse) {
                view.hideProgressBar();
                view.onSuccess(loginResponse.body().getUser());
            }

            @Override
            public void onError(@NonNull Throwable e) {
                e.printStackTrace();
                view.hideProgressBar();
                view.displayError("Error in Login");
            }

            @Override
            public void onComplete() {
                view.hideProgressBar();
            }
        };
    }
}
