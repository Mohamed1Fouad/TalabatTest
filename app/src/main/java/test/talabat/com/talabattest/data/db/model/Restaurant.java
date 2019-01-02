package test.talabat.com.talabattest.data.db.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Restaurant implements Serializable,Parcelable {

    @SerializedName("rest_id")
    private int rest_id;

    @SerializedName("rest_name")
    private String rest_name;

    @SerializedName("rest_img")
    private String rest_img;

    @SerializedName("rest_location")
    private String rest_location;

    @SerializedName("rest_type")
    private String rest_type;

    protected Restaurant(Parcel in) {
        rest_id = in.readInt();
        rest_name = in.readString();
        rest_img = in.readString();
        rest_location = in.readString();
        rest_type = in.readString();
    }

    public static final Creator<Restaurant> CREATOR = new Creator<Restaurant>() {
        @Override
        public Restaurant createFromParcel(Parcel in) {
            return new Restaurant(in);
        }

        @Override
        public Restaurant[] newArray(int size) {
            return new Restaurant[size];
        }
    };

    public int getRest_id() {
        return rest_id;
    }

    public void setRest_id(int rest_id) {
        this.rest_id = rest_id;
    }

    public String getRest_name() {
        return rest_name;
    }

    public void setRest_name(String rest_name) {
        this.rest_name = rest_name;
    }

    public String getRest_img() {
        return rest_img;
    }

    public void setRest_img(String rest_img) {
        this.rest_img = rest_img;
    }

    public String getRest_location() {
        return rest_location;
    }

    public void setRest_location(String rest_location) {
        this.rest_location = rest_location;
    }

    public String getRest_type() {
        return rest_type;
    }

    public void setRest_type(String rest_type) {
        this.rest_type = rest_type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(rest_id);
        parcel.writeString(rest_name);
        parcel.writeString(rest_img);
        parcel.writeString(rest_location);
        parcel.writeString(rest_type);

    }
}
