package com.example.quanla.quannet.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.directions.route.Route;
import com.directions.route.RouteException;
import com.directions.route.Routing;
import com.directions.route.RoutingListener;
import com.example.quanla.quannet.R;
import com.example.quanla.quannet.adapters.CustomInfoAdapter;
import com.example.quanla.quannet.database.DbContextHot;
import com.example.quanla.quannet.database.models.GameRoom;
import com.example.quanla.quannet.events.ActivityReplaceEvent;
import com.example.quanla.quannet.events.MoveToMap;
import com.example.quanla.quannet.events.MoveToMapEvent;
import com.example.quanla.quannet.events.ReplaceFragmentEvent;
import com.example.quanla.quannet.fragments.HotFragment;
import com.example.quanla.quannet.fragments.NewFragment;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import static com.example.quanla.quannet.R.id.map;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, GoogleMap.OnMarkerClickListener, RoutingListener, OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private GoogleMap mMap;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    LocationRequest mLocationRequest;
    private GameRoom gameRoom;
    private MoveToMap moveToMap;


    private List<Polyline> polylines;
    private double mLatitude;
    private double mLongitude;
    private final String TAG = "Test";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(map);
        mapFragment.getMapAsync(this);

        polylines = new ArrayList<>();



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.near) {

            // Handle the camera action
        } else if (id == R.id.top) {
            HotFragment hotFragment = new HotFragment();
            replaceFragment(hotFragment, false);

        } else if (id == R.id.sale) {

        } else if (id == R.id.newone) {
            NewFragment newFragment = new NewFragment();
            replaceFragment(newFragment, false);

        } else if (id == R.id.account) {

        } else if (id == R.id.setting) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onRoutingFailure(RouteException e) {

    }

    @Override
    public void onRoutingStart() {

    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(sticky = true)
    public void getMarker(MoveToMapEvent activityReplaceEvent){
        gameRoom = activityReplaceEvent.getGameRoom();
        moveToMap = activityReplaceEvent.getMoveToMap();
        EventBus.getDefault().removeAllStickyEvents();

    }

    @Override
    public void onRoutingSuccess(ArrayList<Route> arrayList, int index) {
        polylines = new ArrayList<>();
        //add route(s) to the fl.
        for (int i = 0; i <arrayList.size(); i++) {

            //In case of more than 5 alternative routes

            PolylineOptions polyOptions = new PolylineOptions();
            polyOptions.width(10 + i * 3);
            polyOptions.addAll(arrayList.get(i).getPoints());
            Polyline polyline = mMap.addPolyline(polyOptions);
            polyline.setColor(Color.parseColor("#2962FF"));
            polylines.add(polyline);
        }
    }

    @Override
    public void onRoutingCancelled() {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }

        mLatitude = location.getLatitude();
        mLongitude = location.getLongitude();
        //Place current location marker
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        mCurrLocationMarker = mMap.addMarker(markerOptions);

        //move fl camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));

        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
        Location start = new Location("Start");
        start.setLatitude(mLatitude);
        start.setLongitude(mLongitude);
        moveToMap=MoveToMap.FROMNEARME;
        setGGMap();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        for(Polyline polyline: polylines){
            polyline.remove();
        }
        Log.d(TAG, "clicked");
        LatLng latLng = marker.getPosition();
        direction(new com.example.quanla.quannet.database.models.GameRoom(latLng.latitude, latLng.longitude));
        for (com.example.quanla.quannet.database.models.GameRoom l : DbContextHot.instance.getAllRooms()) {
            if (l.getLatitude() == latLng.latitude && l.getLongitude() == latLng.longitude) {
                Location start = new Location("Start");
                start.setLatitude(mLatitude);
                start.setLongitude(mLongitude);
                Location dest = new Location(l.getTitle());
                dest.setLatitude(l.getLatitude());
                dest.setLongitude(l.getLongitude());
                double kilomet = start.distanceTo(dest)/1000;
                mMap.setInfoWindowAdapter(new CustomInfoAdapter(this, l, kilomet));
                marker.showInfoWindow();
            }
        }
        return false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMarkerClickListener(this);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        }
        else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }

    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public boolean checkLocationPermission(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    public void setGGMap(){
        Location start = new Location("Start");
        start.setLatitude(mLatitude);
        start.setLongitude(mLongitude);
        if(moveToMap==MoveToMap.FROMDETAIL){

            if(mMap!=null) {
                Location dest = new Location(gameRoom.getTitle());
                dest.setLatitude(gameRoom.getLatitude());
                dest.setLongitude(gameRoom.getLongitude());
                double kilomet = start.distanceTo(dest)/1000;
                Marker marker = mMap.addMarker(new MarkerOptions().position(new LatLng(gameRoom.getLatitude(), gameRoom.getLongitude())).title(gameRoom.getTitle()).draggable(true).visible(true));
                direction(new com.example.quanla.quannet.database.models.GameRoom(gameRoom.getLatitude(), gameRoom.getLongitude()));
                mMap.setInfoWindowAdapter(new CustomInfoAdapter(this,gameRoom,kilomet));
                marker.showInfoWindow();
            }
        }
        else if(moveToMap==MoveToMap.FROMNEARME){
            if(mMap!=null) {
                for (com.example.quanla.quannet.database.models.GameRoom l : DbContextHot.instance.getAllRooms()) {
                    Location dest = new Location(l.getTitle());
                    dest.setLatitude(l.getLatitude());
                    dest.setLongitude(l.getLongitude());
                    if (start.distanceTo(dest) <= 10000) {
                        mMap.addMarker(new MarkerOptions().position(new LatLng(l.getLatitude(), l.getLongitude())).title(l.getTitle()).draggable(true).visible(true));
                    }
                }
            }
        }
    }

    public void direction(com.example.quanla.quannet.database.models.GameRoom location){

        LatLng start = new LatLng(mLatitude, mLongitude);
        LatLng end = new LatLng(location.getLatitude(), location.getLongitude());
        Routing routing = new Routing.Builder()
                .travelMode(Routing.TravelMode.WALKING)
                .withListener(this)
                .waypoints(start, end, end)
                .build();
        routing.execute();

    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted. Do the
                    // contacts-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }

                } else {

                    // Permission denied, Disable the functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other permissions this app might request.
            // You can add here other case statements according to your requirement.
        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void replace(ActivityReplaceEvent activityReplaceEvent){
        startActivity(new Intent(MainActivity.this, DetailActivity.class));
    }

    @Subscribe
    public void replaceFragment(ReplaceFragmentEvent fragmentReplaceEvent){
        replaceFragment(fragmentReplaceEvent.getFragment(), fragmentReplaceEvent.isAddToBackStack());
    }

    public void replaceFragment(Fragment fragment, boolean addToBackStack){
        if(addToBackStack) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fl1, fragment)
                    .addToBackStack(null)
                    .commit();
        }
        else{
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fl1, fragment)
                    .commit();
        }
    }
}
