package test.talabat.com.talabattest.ui.order_details;

import java.util.List;

import test.talabat.com.talabattest.data.db.model.Order;
import test.talabat.com.talabattest.data.db.model.OrderDetails;

public interface OrdersDetailsPresenterListener {
    void getOrder(Order order, List<OrderDetails> orderDetails);
}
