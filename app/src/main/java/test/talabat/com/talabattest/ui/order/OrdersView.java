package test.talabat.com.talabattest.ui.order;

import java.util.List;

import test.talabat.com.talabattest.data.db.model.Order;
import test.talabat.com.talabattest.interfaces.BaseView;

public interface OrdersView extends BaseView {
    void onLoadOrders(List<Order> orders);
}
