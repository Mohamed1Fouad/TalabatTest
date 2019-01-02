package test.talabat.com.talabattest.ui.restaurants;


import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;
import test.talabat.com.talabattest.data.network.RetrofitClient;
import test.talabat.com.talabattest.data.network.RetrofitService;
import test.talabat.com.talabattest.data.network.model.GetRestaurantResponse;
import test.talabat.com.talabattest.utils.Constants;

public class RestaurantsListPresenter implements RestautantsPresenterListener {
    RestaurantsListView view;
    public RestaurantsListPresenter(RestaurantsListView view){
        this.view=view;
    }
    @Override
    public void getAllRestaurants (Map<String, String> fields) {
        view.showProgressBar();
        RetrofitClient.getClient(Constants.BASE_URL).create(RetrofitService.class)
                .getResturants(fields)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(getObserver());
    }

    public DisposableObserver<Response<GetRestaurantResponse>> getObserver(){
        return new DisposableObserver<Response<GetRestaurantResponse>>() {

            @Override
            public void onNext(@NonNull Response<GetRestaurantResponse> getRestaurantResponse) {
                view.hideProgressBar();
                view.onLoadAllRestaurants(getRestaurantResponse.body().getRestaurants());
            }

            @Override
            public void onError(@NonNull Throwable e) {
                e.printStackTrace();
                view.hideProgressBar();
                view.displayError("Error in loading");
            }

            @Override
            public void onComplete() {
                view.hideProgressBar();
            }
        };
    }
}
