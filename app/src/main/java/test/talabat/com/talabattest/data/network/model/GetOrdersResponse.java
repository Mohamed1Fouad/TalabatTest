package test.talabat.com.talabattest.data.network.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import test.talabat.com.talabattest.data.db.model.Order;

public class GetOrdersResponse extends BaseResponse {
    @SerializedName("return")
    private
    List<Order> orders;

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
