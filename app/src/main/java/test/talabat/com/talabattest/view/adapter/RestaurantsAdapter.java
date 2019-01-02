package test.talabat.com.talabattest.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;

import test.talabat.com.talabattest.R;
import test.talabat.com.talabattest.data.db.model.Restaurant;
import test.talabat.com.talabattest.interfaces.OnItemClickListener;


public class RestaurantsAdapter extends RecyclerView.Adapter<RestaurantsAdapter.ViewHolder> {

    List<Restaurant> restaurants;
    LayoutInflater inflater;
    Context context;
    OnItemClickListener listener;
    public RestaurantsAdapter(Context context, List<Restaurant> restaurants,OnItemClickListener listener) {
        this.context = context;
        this.restaurants = restaurants;
        this.listener=listener;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.restaurant_row_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(restaurants.get(position), listener);

        holder.restaurantName.setText(restaurants.get(position).getRest_name());
        holder.restaurantType.setText(restaurants.get(position).getRest_type());

        if (restaurants.get(position).getRest_img() != null && !restaurants.get(position).getRest_img().isEmpty()) {
             Transformation transformation = new RoundedTransformationBuilder()
                    .borderColor(Color.BLACK)
                    .borderWidthDp(3)
                    .cornerRadiusDp(30)
                    .oval(false)
                    .build();


            Picasso.get()
                    .load(restaurants.get(position).getRest_img())
                    .fit()
                    .transform(transformation)
                    .into(holder.restaurantImage);
        }

    }

    @Override
    public int getItemCount() {
        return restaurants.size();
    }

    public List<Restaurant> getRestaurants(){
        return restaurants;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView restaurantName;
        RoundedImageView restaurantImage;
        TextView restaurantType;

        public ViewHolder(View itemView) {
            super(itemView);
            restaurantName = itemView.findViewById(R.id.restaurantTextView);
            restaurantImage = itemView.findViewById(R.id.restaurantImageView);
            restaurantType = itemView.findViewById(R.id.restaurantTypeTextView);

        }
        public void bind(final Restaurant item, final OnItemClickListener listener) {

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }
}

