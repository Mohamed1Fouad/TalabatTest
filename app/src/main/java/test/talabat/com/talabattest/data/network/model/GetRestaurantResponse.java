package test.talabat.com.talabattest.data.network.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import test.talabat.com.talabattest.data.db.model.Restaurant;

public class GetRestaurantResponse extends BaseResponse {
    @SerializedName("return")
    private
    List<Restaurant> restaurants;


    public List<Restaurant> getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(List<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }
}
