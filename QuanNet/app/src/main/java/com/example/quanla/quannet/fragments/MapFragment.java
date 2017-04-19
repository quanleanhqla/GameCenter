package com.example.quanla.quannet.fragments;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.directions.route.Route;
import com.directions.route.RouteException;
import com.directions.route.Routing;
import com.directions.route.RoutingListener;
import com.example.quanla.quannet.R;
import com.example.quanla.quannet.activities.MainActivity;
import com.example.quanla.quannet.adapters.CustomInfoAdapter;
import com.example.quanla.quannet.database.DbContextHot;
import com.example.quanla.quannet.database.models.GameRoom;
import com.example.quanla.quannet.events.ActivityReplaceEvent;
import com.example.quanla.quannet.events.DrawSearchEvent;
import com.example.quanla.quannet.events.FromInfoEvent;
import com.example.quanla.quannet.events.MoveToMap;
import com.example.quanla.quannet.events.ReplaceFragmentEvent;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements GoogleMap.OnMarkerClickListener, RoutingListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {


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

    MapView mMapView;

    public MapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);

        mMapView = (MapView) rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume();

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }


        final Context context = this.getContext();

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;


                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

                //Initialize Google Play Services
                check(context);

            }
        });

        polylines = new ArrayList<>();
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }

        if(getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).getSupportActionBar().setTitle(" ");
        }
        return rootView;
    }

    @Override
    public void onStart() {
        EventBus.getDefault().register(this);
        super.onStart();
    }


    public void check(Context context){
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context,
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

    @Override
    public void onRoutingFailure(RouteException e) {

    }

    @Override
    public void onPause() {
        EventBus.getDefault().unregister(this);
        mMapView.onPause();
        super.onPause();
    }

    @Override
    public void onRoutingStart() {

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
        if (ContextCompat.checkSelfPermission(this.getActivity(),
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
        mMap.setOnMarkerClickListener(this);
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
        for (final com.example.quanla.quannet.database.models.GameRoom l : DbContextHot.instance.getAllRooms()) {
            if (l.getLatitude() == latLng.latitude && l.getLongitude() == latLng.longitude) {
                Location start = new Location("Start");
                start.setLatitude(mLatitude);
                start.setLongitude(mLongitude);
                Location dest = new Location(l.getTitle());
                dest.setLatitude(l.getLatitude());
                dest.setLongitude(l.getLongitude());
                double kilomet = start.distanceTo(dest)/1000;
                mMap.setInfoWindowAdapter(new CustomInfoAdapter(this.getActivity(), l, kilomet));
                mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick(Marker marker) {
                        // chuyen man hinh detail
                        EventBus.getDefault().postSticky(new FromInfoEvent(l));
                        EventBus.getDefault().post(new ReplaceFragmentEvent(new DetailFragment(), true));
                    }
                });
                marker.showInfoWindow();
            }
        }
        return false;
    }



    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public boolean checkLocationPermission(){
        if (ContextCompat.checkSelfPermission(this.getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this.getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this.getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this.getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }



    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void getGameRoom(ActivityReplaceEvent activityReplaceEvent){
        gameRoom = activityReplaceEvent.getGameRoom();
        moveToMap = activityReplaceEvent.getMoveToMap();

        Log.d(TAG, String.format("d c m %s", gameRoom.toString()));
        if(getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).getSupportActionBar().setTitle(gameRoom.getTitle());
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
                marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.imac));
                direction(new com.example.quanla.quannet.database.models.GameRoom(gameRoom.getLatitude(), gameRoom.getLongitude()));
                mMap.setInfoWindowAdapter(new CustomInfoAdapter(this.getActivity(),gameRoom,kilomet));
                marker.showInfoWindow();
            }
        }
        else {
            if(mMap!=null) {
                for (final com.example.quanla.quannet.database.models.GameRoom l : DbContextHot.instance.getAllRooms()) {
                    Location dest = new Location(l.getTitle());
                    dest.setLatitude(l.getLatitude());
                    dest.setLongitude(l.getLongitude());
                    if (start.distanceTo(dest) <= 10000) {
                        mMap.addMarker(new MarkerOptions().position(new LatLng(l.getLatitude(), l.getLongitude())).title(l.getTitle()).draggable(true).visible(true).icon(BitmapDescriptorFactory.fromResource(R.drawable.imac)));
                    }
                }
            }
        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this.getContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
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
                    if (ContextCompat.checkSelfPermission(this.getContext(),
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
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Do something that differs the Activity's menu here
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.seach_menu, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.mn_search){
            EventBus.getDefault().post(new DrawSearchEvent());
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
