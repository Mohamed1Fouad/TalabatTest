package test.talabat.com.talabattest.data.db.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Order implements  Parcelable {

    @SerializedName("order_id")
    private int order_id;

    @SerializedName("order_user")
    private String order_user;

    @SerializedName("order_price")
    private double order_price;

    @SerializedName("user_location")
    private String user_location;

    @SerializedName("resturant_name")
    private String resturant_name;

    @SerializedName("order_details")
    private List<OrderDetails> order_details;

    protected Order(Parcel in) {
        order_id = in.readInt();
        order_user = in.readString();
        order_price = in.readDouble();
        user_location = in.readString();
        resturant_name = in.readString();
    }

    public static final Creator<Order> CREATOR = new Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel in) {
            return new Order(in);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public String getOrder_user() {
        return order_user;
    }

    public void setOrder_user(String order_user) {
        this.order_user = order_user;
    }

    public double getOrder_price() {
        return order_price;
    }

    public void setOrder_price(double order_price) {
        this.order_price = order_price;
    }

    public String getUser_location() {
        return user_location;
    }

    public void setUser_location(String user_location) {
        this.user_location = user_location;
    }

    public String getResturant_name() {
        return resturant_name;
    }

    public void setResturant_name(String resturant_name) {
        this.resturant_name = resturant_name;
    }

    public List<OrderDetails> getOrder_details() {
        return order_details;
    }

    public void setOrder_details(List<OrderDetails> order_details) {
        this.order_details = order_details;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(order_id);
        parcel.writeString(order_user);
        parcel.writeDouble(order_price);
        parcel.writeString(user_location);
        parcel.writeString(resturant_name);
    }
}
