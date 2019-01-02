package test.talabat.com.talabattest.ui.restaurants;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import test.talabat.com.talabattest.R;
import test.talabat.com.talabattest.ui.order.OrdersActivity;
import test.talabat.com.talabattest.data.db.model.Restaurant;
import test.talabat.com.talabattest.interfaces.OnItemClickListener;
import test.talabat.com.talabattest.utils.Utils;
import test.talabat.com.talabattest.view.adapter.RestaurantsAdapter;
import test.talabat.com.talabattest.view.custom.DialogProgress;

public class RestaurantsListActivity extends AppCompatActivity implements RestaurantsListView, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.swipe_container)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.restaurantsRecycleView)
    RecyclerView restaurantsRecyclerView;

    @BindView(R.id.noDataView)
    LinearLayout noDataLinearLayout;

//    @BindView(R.id.titleTextView)
//    public TextView titleTextView;

    private DialogProgress dialogProgress;
    private RestaurantsListPresenter presenter;
    private RestaurantsAdapter restaurantsAdapter;
    private LinearLayoutManager mLayoutManager;

    private boolean isListview = true;

    private final String SWITCH_STATE = "switch_state";
    private final String RESTAURANTS_STATE = "restaurants_state";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

            setContentView(R.layout.activity_restaurants);
            init();


    }

    //init view and presenter
    public void init() {
        ButterKnife.bind(this);
        presenter = new RestaurantsListPresenter(this);
        dialogProgress = new DialogProgress(this);
//        getSupportActionBar().hide();
//        titleTextView.setText(R.string.restaurants);
        mLayoutManager = new LinearLayoutManager(this);
        restaurantsRecyclerView.setLayoutManager(mLayoutManager);

        // SwipeRefreshLayout
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);
        mSwipeRefreshLayout.post(new Runnable() {

            @Override
            public void run() {


                // Fetching data from server
                getDate();
            }
        });

        getDate();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.restaurant_menu, menu);
        menu.findItem(R.id.switchView).setIcon(isListview ? R.drawable.ic_grid : R.drawable.ic_list);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.switchView) {
            isListview = !isListview;
            restaurantsRecyclerView.setLayoutManager(isListview ? new LinearLayoutManager(this) : new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
            restaurantsRecyclerView.setAdapter(restaurantsAdapter);


        }
        invalidateOptionsMenu();

        return super.onOptionsItemSelected(item);
    }

    //check for internet connection then get data
    private void getDate() {
        restaurantsRecyclerView.removeAllViewsInLayout();
        mSwipeRefreshLayout.setRefreshing(true);
        if (Utils.isNetworkConnected(this)) {
            HashMap<String, String> fields = new HashMap<>();
            fields.put("langu", "en");
            presenter.getAllRestaurants(fields);
        } else {
            hideProgressBar();
            displayError(getResources().getString(R.string.no_internet_connection));
            mSwipeRefreshLayout.setRefreshing(false);
            restaurantsRecyclerView.setVisibility(View.GONE);
            noDataLinearLayout.setVisibility(View.VISIBLE);

        }
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
    public void onLoadAllRestaurants(List<Restaurant> restaurants) {
        if (restaurants != null && restaurants.size() > 0) {
            restaurantsRecyclerView.setVisibility(View.VISIBLE);
            noDataLinearLayout.setVisibility(View.GONE);
            restaurantsAdapter = new RestaurantsAdapter(this, restaurants,new  OnItemClickListener() {
                @Override
                public void onItemClick(Restaurant item) {

                    startActivity(new Intent(RestaurantsListActivity.this,OrdersActivity.class).putExtra(OrdersActivity.REST_ID_KEY,item.getRest_id()));
                }
            });
            restaurantsRecyclerView.setAdapter(restaurantsAdapter);
        } else {
            restaurantsRecyclerView.setVisibility(View.GONE);
            noDataLinearLayout.setVisibility(View.VISIBLE);
        }
        mSwipeRefreshLayout.setRefreshing(false);

    }

    @Override
    public void onRefresh() {
        getDate();

    }



}
