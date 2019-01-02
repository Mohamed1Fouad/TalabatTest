package test.talabat.com.talabattest.ui.order_details;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import test.talabat.com.talabattest.R;
import test.talabat.com.talabattest.data.db.model.Order;
import test.talabat.com.talabattest.data.db.model.OrderDetails;
import test.talabat.com.talabattest.view.adapter.OrdersAdapter;
import test.talabat.com.talabattest.view.custom.DialogProgress;

public class OrderDetailsActivity extends AppCompatActivity implements OrdersDetailsView {
    public static final String ORDER_KEY = "order_key";
    public static final String ORDER_DETAILS_KEY = "order_details_key";

    @BindView(R.id.usernameTv)
    TextView usernameTv;
    @BindView(R.id.restaurantTv)
    TextView restaurantsTv;
    @BindView(R.id.priceTv)
    TextView totalPriceTv;

    @BindView(R.id.ordersRecycleView)
    RecyclerView orderDetailsRecyclerView;
    OrdersAdapter ordersAdapter;
    private DialogProgress dialogProgress;
    private OrdersDetailsPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        init();

    }

    //init view and presenter
    public void init() {
        ButterKnife.bind(this);
        presenter = new OrdersDetailsPresenter(this);
        dialogProgress = new DialogProgress(this);
        orderDetailsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        Order order = getIntent().getParcelableExtra(ORDER_KEY);
        List<OrderDetails> orderDetails = (ArrayList<OrderDetails>) getIntent().getSerializableExtra(ORDER_DETAILS_KEY);
        presenter.getOrder(order, orderDetails);
    }


    @Override
    public void showToast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgressBar() {
        dialogProgress.show();
    }

    @Override
    public void hideProgressBar() {
        dialogProgress.hide();

    }

    @Override
    public void displayError(String s) {
        showToast(s);
    }

    @Override
    public void onLoadOrderDetails(Order order, List<OrderDetails> orderDetails) {
        usernameTv.setText(order.getOrder_user());
        restaurantsTv.setText(order.getResturant_name());
        totalPriceTv.setText(getString(R.string.order_price_Egp,order.getOrder_price()));
        ordersAdapter = new OrdersAdapter(this, orderDetails);
        orderDetailsRecyclerView.setAdapter(ordersAdapter);


    }
}
