package test.talabat.com.talabattest.ui.order_details;


import java.util.List;

import test.talabat.com.talabattest.data.db.model.Order;
import test.talabat.com.talabattest.data.db.model.OrderDetails;

public class OrdersDetailsPresenter implements OrdersDetailsPresenterListener {
    OrdersDetailsView view;
    public OrdersDetailsPresenter(OrdersDetailsView view){
        this.view=view;
    }
    @Override
    public void getOrder (Order order, List<OrderDetails> orderDetails) {
        view.onLoadOrderDetails(order,orderDetails);
    }

}
