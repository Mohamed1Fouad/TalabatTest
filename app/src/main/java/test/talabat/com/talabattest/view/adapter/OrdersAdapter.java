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
import test.talabat.com.talabattest.data.db.model.OrderDetails;


public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.ViewHolder> {

    List<OrderDetails> orderDetails;
    LayoutInflater inflater;
    Context context;
     public OrdersAdapter(Context context, List<OrderDetails> orderDetails) {
        this.context = context;
        this.orderDetails = orderDetails;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_row_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.orderNameTv.setText(orderDetails.get(position).getProd_name());
        holder.orderPriceTv.setText(String.valueOf(orderDetails.get(position).getProd_price()));
        holder.orderQuantityTv.setText(String.valueOf(orderDetails.get(position).getProd_quantity()));

        if (orderDetails.get(position).getProd_image() != null && !orderDetails.get(position).getProd_image().isEmpty()) {
             Transformation transformation = new RoundedTransformationBuilder()
                    .borderColor(Color.BLACK)
                    .borderWidthDp(3)
                    .cornerRadiusDp(30)
                    .oval(false)
                    .build();


            Picasso.get()
                    .load(orderDetails.get(position).getProd_image())
                    .fit()
                    .transform(transformation)
                    .into(holder.orderIv);
        }

    }

    @Override
    public int getItemCount() {
        return orderDetails.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        TextView orderNameTv;
        TextView orderPriceTv;
        TextView orderQuantityTv;

        RoundedImageView orderIv;

        public ViewHolder(View itemView) {
            super(itemView);
            orderNameTv = itemView.findViewById(R.id.nameTv);
            orderPriceTv = itemView.findViewById(R.id.priceTv);
            orderQuantityTv = itemView.findViewById(R.id.quantityTv);
            orderIv = itemView.findViewById(R.id.orderImageView);
        }

    }
}

