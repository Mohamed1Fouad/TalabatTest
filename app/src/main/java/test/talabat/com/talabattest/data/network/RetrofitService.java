package test.talabat.com.talabattest.data.network;


import java.util.Map;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import test.talabat.com.talabattest.data.network.model.GetOrdersResponse;
import test.talabat.com.talabattest.data.network.model.GetRestaurantResponse;
import test.talabat.com.talabattest.data.network.model.LoginRequest;
import test.talabat.com.talabattest.data.network.model.LoginResponse;
import test.talabat.com.talabattest.utils.Constants;

public interface RetrofitService {
    @Headers("Accept: application/json")
    @POST(Constants.LOGIN_URL)
    Observable<Response<LoginResponse>> login(@Body LoginRequest loginRequest);

    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST(Constants.GET_RESTURANTS_URL)
    Observable<Response<GetRestaurantResponse>>  getResturants(@FieldMap Map<String, String> fields);

    @GET(Constants.GET_ORDERS_URL)
    Observable<Response<GetOrdersResponse>> getOrder(@Query("langu") String langu,
                                                     @Query("restId") String restId);
//

}
