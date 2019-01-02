package test.talabat.com.talabattest.ui.order_details;

import java.util.List;

import test.talabat.com.talabattest.data.db.model.Order;
import test.talabat.com.talabattest.data.db.model.OrderDetails;
import test.talabat.com.talabattest.interfaces.BaseView;

public interface OrdersDetailsView extends BaseView {
    void onLoadOrderDetails(Order order, List<OrderDetails> orderDetails);
}
