package test.talabat.com.talabattest.data.db.model;

import android.os.Parcel;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OrderDetails implements Serializable {

    @SerializedName("prod_name")
    private String prod_name;

    @SerializedName("prod_quantity")
    private int prod_quantity;

    @SerializedName("prod_price")
    private double prod_price;

    @SerializedName("prod_image")
    private String prod_image;


    protected OrderDetails(Parcel in) {
        prod_name = in.readString();
        prod_quantity = in.readInt();
        prod_price = in.readDouble();
        prod_image = in.readString();
    }



    public String getProd_name() {
        return prod_name;
    }

    public void setProd_name(String prod_name) {
        this.prod_name = prod_name;
    }

    public int getProd_quantity() {
        return prod_quantity;
    }

    public void setProd_quantity(int prod_quantity) {
        this.prod_quantity = prod_quantity;
    }

    public double getProd_price() {
        return prod_price;
    }

    public void setProd_price(double prod_price) {
        this.prod_price = prod_price;
    }

    public String getProd_image() {
        return prod_image;
    }

    public void setProd_image(String prod_image) {
        this.prod_image = prod_image;
    }


}
