package test.talabat.com.talabattest.ui.restaurants;


import java.util.List;

import test.talabat.com.talabattest.data.db.model.Restaurant;
import test.talabat.com.talabattest.interfaces.BaseView;

public interface RestaurantsListView extends BaseView {

    void onLoadAllRestaurants(List<Restaurant> restaurants);
}
