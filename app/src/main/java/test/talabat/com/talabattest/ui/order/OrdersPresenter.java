package test.talabat.com.talabattest.ui.order;


import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;
import test.talabat.com.talabattest.data.network.RetrofitClient;
import test.talabat.com.talabattest.data.network.RetrofitService;
import test.talabat.com.talabattest.data.network.model.GetOrdersResponse;
import test.talabat.com.talabattest.utils.Constants;

public class OrdersPresenter implements OrdersPresenterListener {
    OrdersView view;
    public OrdersPresenter(OrdersView view){
        this.view=view;
    }
    @Override
    public void getOrders (String lang,int restId) {
        view.showProgressBar();
        RetrofitClient.getClient(Constants.BASE_URL).create(RetrofitService.class)
                .getOrder(lang,String.valueOf(restId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(getObserver());
    }

    public DisposableObserver<Response<GetOrdersResponse>> getObserver(){
        return new DisposableObserver<Response<GetOrdersResponse>>() {

            @Override
            public void onNext(@NonNull Response<GetOrdersResponse> getOrdersResponse) {
                view.hideProgressBar();
                view.onLoadOrders(getOrdersResponse.body().getOrders());
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
