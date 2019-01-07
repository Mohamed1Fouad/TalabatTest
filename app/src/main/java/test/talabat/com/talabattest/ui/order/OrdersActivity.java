package test.talabat.com.talabattest.ui.order;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.directions.route.AbstractRouting;
import com.directions.route.Route;
import com.directions.route.RouteException;
import com.directions.route.Routing;
import com.directions.route.RoutingListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import test.talabat.com.talabattest.R;
import test.talabat.com.talabattest.ui.order_details.OrderDetailsActivity;
import test.talabat.com.talabattest.data.db.model.Order;
import test.talabat.com.talabattest.data.db.model.OrderDetails;
import test.talabat.com.talabattest.utils.Constants;
import test.talabat.com.talabattest.utils.Utils;
import test.talabat.com.talabattest.view.custom.DialogProgress;
import test.talabat.com.talabattest.view.custom.MapWrapperLayout;
import test.talabat.com.talabattest.view.custom.OnInfoWindowElemTouchListener;

public class OrdersActivity extends AppCompatActivity implements OrdersView, OnMapReadyCallback, LocationListener, RoutingListener {

    public static final String REST_ID_KEY = "rest_id";

    @BindView(R.id.relativeLayout)
    MapWrapperLayout mapWrapperLayout;

    private DialogProgress dialogProgress;
    private OrdersPresenter presenter;

    private int restaurantId = 0;
    private LocationManager locManager;
    private GoogleMap googleMap;
    private ArrayList<Marker> markers;
    private Location currentLocation;
    private ArrayList<Polyline> polylines;
    private static final int[] COLORS = new int[]{R.color.colorPrimaryDark, R.color.colorPrimary, R.color.yellow, R.color.colorAccent, R.color.primary_dark_material_light};
    private LatLng start, end;
    ArrayList<Order> orders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        init();

    }

    //init view and presenter
    public void init() {
        ButterKnife.bind(this);
        presenter = new OrdersPresenter(this);
        dialogProgress = new DialogProgress(this);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapView);
        mapFragment.getMapAsync(this);
        markers = new ArrayList<>();
        restaurantId = getIntent().getIntExtra(REST_ID_KEY, 0);
        polylines = new ArrayList<>();

    }

    @Override
    public void onLoadOrders(List<Order> orders) {
        if (orders != null && orders.size() > 0) {
            this.orders = new ArrayList<>(orders);
            for (Order order : orders) {
                String latlong[] = order.getUser_location().split(", ");
                LatLng loc = new LatLng(Double.valueOf(latlong[0]), Double.valueOf(latlong[1]));
                Marker newMarker = googleMap.addMarker(new MarkerOptions().position(loc)
                        .title(order.getResturant_name()));
                newMarker.setTag(order.getOrder_id());
                markers.add(newMarker);
            }
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
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        if (Utils.checkLocationPermission(this))
            this.googleMap.setMyLocationEnabled(true);

        initInfoWindows();
        initLocation();
        presenter.getOrders("en", restaurantId);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Utils.checkLocationPermission(this) && locManager != null)
            locManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0,
                    0, this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (locManager != null)
            locManager.removeUpdates(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case Constants.LOCATION_PERMISSIONS_REQUEST: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                    initInfoWindows();
                    initLocation();

                } else {
                    showToast(getString(R.string.error_location));
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }


        }
    }

    @Override
    public void onLocationChanged(Location location) {
        currentLocation = location;
        if (currentLocation == null)
            showToast(getString(R.string.error_location));
        else
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), 5));

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    public void initLocation() {
        if (Utils.checkLocationPermission(this)) {
            boolean gps_enabled, network_enabled;
            locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            gps_enabled = locManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            network_enabled = locManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);


            if (gps_enabled) {
                locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
            }
            if (network_enabled) {
                locManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);

//            locManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0,
//                    0, this);

                currentLocation = locManager
                        .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);


                if (currentLocation == null) {
                    showToast(getString(R.string.error_location));

                } else {
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), 5));

                }


            }
        }
    }

        //initialize order's info popup
        public void initInfoWindows () {
            Button deleteBtn, directionBtn, detailsBtn;
            final ViewGroup infoWindow;
            final OnInfoWindowElemTouchListener deleteBtnListener, directionBtnListener, detailsBtnListener;

            mapWrapperLayout.init(this.googleMap, Utils.getPixelsFromDp(this, 50));
            infoWindow = (ViewGroup) getLayoutInflater().inflate(R.layout.map_marker_info_window, null);
            deleteBtn = infoWindow.findViewById(R.id.deleteBtn);
            directionBtn = infoWindow.findViewById(R.id.directionBtn);
            detailsBtn = infoWindow.findViewById(R.id.detailsBtn);

            deleteBtnListener = new OnInfoWindowElemTouchListener(deleteBtn,
                    getResources().getDrawable(R.drawable.rounded_button_yellow_shape),
                    getResources().getDrawable(R.drawable.rounded_button_yellow_shape)) {
                @Override
                protected void onClickConfirmed(View v, Marker marker) {
                    for (Order order : orders) {
                        if ((Integer) marker.getTag() == order.getOrder_id()) {
                            orders.remove(order);
                            break;
                        }
                    }

                    markers.remove(marker);
//                ArrayList<Marker> updatedMarkers = new ArrayList<>(markers);
                    googleMap.clear();
                    markers.clear();
                    //redraw markers after delete selected one

                    for (Order order : orders) {
                        String latlong[] = order.getUser_location().split(", ");
                        LatLng loc = new LatLng(Double.valueOf(latlong[0]), Double.valueOf(latlong[1]));
                        Marker newMarker = googleMap.addMarker(new MarkerOptions().position(loc)
                                .title(order.getResturant_name()));
                        newMarker.setTag(order.getOrder_id());
                        markers.add(newMarker);
                    }

                    showToast(getString(R.string.order_finished, marker.getTitle()));
                }
            };

            directionBtnListener = new OnInfoWindowElemTouchListener(directionBtn,
                    getResources().getDrawable(R.drawable.rounded_button_yellow_shape),
                    getResources().getDrawable(R.drawable.rounded_button_yellow_shape)) {
                @Override
                protected void onClickConfirmed(View v, Marker marker) {
                    // Here we can perform some action triggered after clicking the button

                    if (currentLocation != null) {
                        route(marker.getPosition());
                        marker.hideInfoWindow();
                    } else {
                        showToast(getString(R.string.error_location));
                    }
                }
            };

            detailsBtnListener = new OnInfoWindowElemTouchListener(detailsBtn,
                    getResources().getDrawable(R.drawable.rounded_button_yellow_shape),
                    getResources().getDrawable(R.drawable.rounded_button_yellow_shape)) {
                @Override
                protected void onClickConfirmed(View v, Marker marker) {
                    // Here we can perform some action triggered after clicking the button
                    for (Order order : orders) {
                        if ((Integer) marker.getTag() == order.getOrder_id()) {
                            Intent intent = new Intent(OrdersActivity.this, OrderDetailsActivity.class);
                            intent.putExtra(OrderDetailsActivity.ORDER_KEY, order);
                            ArrayList<OrderDetails> orderDetails = new ArrayList<>(order.getOrder_details());
                            intent.putExtra(OrderDetailsActivity.ORDER_DETAILS_KEY, orderDetails);

                            startActivity(intent);
                            break;
                        }
                    }
                }
            };
            deleteBtn.setOnTouchListener(deleteBtnListener);
            directionBtn.setOnTouchListener(directionBtnListener);
            detailsBtn.setOnTouchListener(detailsBtnListener);

            this.googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                @Override
                public View getInfoWindow(Marker marker) {
                    return null;
                }

                @Override
                public View getInfoContents(Marker marker) {
                    // Setting up the infoWindow with current's marker info
                    deleteBtnListener.setMarker(marker);
                    directionBtnListener.setMarker(marker);
                    detailsBtnListener.setMarker(marker);
                    // We must call this to set the current marker and infoWindow references
                    // to the MapWrapperLayout
                    mapWrapperLayout.setMarkerWithInfoWindow(marker, infoWindow);
                    return infoWindow;
                }
            });
        }


        public void route (LatLng end){
            start = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
            this.end = end;
            if (start == null || end == null) {
                if (start == null) {
                    showToast("Please choose a starting point.");
                }
                if (end == null) {
                    showToast("Please choose a destination.");
                }
            } else {
                showProgressBar();
                Routing routing = new Routing.Builder()
                        .travelMode(AbstractRouting.TravelMode.DRIVING)
                        .withListener(this)
                        .alternativeRoutes(true)
                        .waypoints(start, end)
                        .key(getString(R.string.google_maps_key))
                        .build();
                routing.execute();
            }
        }

        @Override
        public void onRoutingFailure (RouteException e){
            hideProgressBar();
            if (e != null && e.getStatusCode() == "ZERO_RESULTS") {
                showToast("no direction for This location is so far");
            } else if (e == null) {
                showToast("Something went wrong, Try again");
            } else {
                showToast("Error: " + e.getMessage());
            }
        }

        @Override
        public void onRoutingStart () {
            googleMap.clear();
            markers.clear();
//            for (Marker marker1 : markers) {
//                googleMap.addMarker(new MarkerOptions().position(marker1.getPosition())
//                        .title(marker1.getTitle()));
//            }

            for (Order order : orders) {
                String latlong[] = order.getUser_location().split(", ");
                LatLng loc = new LatLng(Double.valueOf(latlong[0]), Double.valueOf(latlong[1]));
                Marker newMarker = googleMap.addMarker(new MarkerOptions().position(loc)
                        .title(order.getResturant_name()));
                newMarker.setTag(order.getOrder_id());
                markers.add(newMarker);
            }
        }

        @Override
        public void onRoutingSuccess (ArrayList < Route > route,int shortestRouteIndex){

            LatLng start = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
            hideProgressBar();

            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(start, 10));


            if (polylines.size() > 0) {
                for (Polyline poly : polylines) {
                    poly.remove();
                }
            }

            polylines = new ArrayList<>();
            //add route(s) to the map.
            for (int i = 0; i < route.size(); i++) {

                //In case of more than 5 alternative routes
                int colorIndex = i % COLORS.length;

                PolylineOptions polyOptions = new PolylineOptions();
                polyOptions.color(getResources().getColor(COLORS[colorIndex]));
                polyOptions.width(10 + i * 3);
                polyOptions.addAll(route.get(i).getPoints());
                Polyline polyline = googleMap.addPolyline(polyOptions);
                polylines.add(polyline);
            }


        }


        @Override
        public void onRoutingCancelled () {
            Log.i("routing", "Routing was cancelled.");

        }
    }
