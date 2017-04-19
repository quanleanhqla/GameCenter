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
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.directions.route.Route;
import com.directions.route.RouteException;
import com.directions.route.Routing;
import com.directions.route.RoutingListener;
import com.example.quanla.quannet.R;
import com.example.quanla.quannet.adapters.CustomInfoAdapter;
import com.example.quanla.quannet.database.DbContextHot;
import com.example.quanla.quannet.database.models.GameRoom;
import com.example.quanla.quannet.events.ActivityReplaceEvent;
import com.example.quanla.quannet.events.DrawSearchEvent;
import com.example.quanla.quannet.events.MoveToMap;
import com.example.quanla.quannet.events.MoveToMapEvent;
import com.example.quanla.quannet.events.ReplaceFragmentEvent;
import com.example.quanla.quannet.fragments.HotFragment;
import com.example.quanla.quannet.fragments.MapFragment;
import com.example.quanla.quannet.fragments.NewFragment;
import com.example.quanla.quannet.fragments.SaleFragment;
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


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener  {


    private static final String TAG = "MainActivity";
    private SearchView searchView;
    static public ImageView logo;

    public static ImageView getLogo() {
        return logo;
    }

    public static void setLogo(ImageView logo) {
        MainActivity.logo = logo;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        logo = (ImageView) findViewById(R.id.logo) ;
        logo.setVisibility(View.GONE);
        searchView = (SearchView) findViewById(R.id.search) ;

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                if(getSupportFragmentManager().getBackStackEntryCount() > 0){
                    toggle.setDrawerIndicatorEnabled(false);
                    toggle.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24px);
                    toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onBackPressed();
                        }
                    });
                }
                else{
                    toggle.setDrawerIndicatorEnabled(true);
                    toggle.setToolbarNavigationClickListener(null);
                }
            }
        });

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        MapFragment mapFragment = new MapFragment();
        replaceFragment(mapFragment, false);


        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setSubtitleTextColor(Color.WHITE);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else if (searchView.getVisibility()==View.VISIBLE){
            searchView.setVisibility(View.GONE);
        }
        else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.near) {

        } else if (id == R.id.top) {
            HotFragment hotFragment = new HotFragment();
            replaceFragment(hotFragment, true);

        } else if (id == R.id.sale) {
            SaleFragment saleFragment = new SaleFragment();
            replaceFragment(saleFragment, true);
        } else if (id == R.id.newone) {
            NewFragment newFragment = new NewFragment();
            replaceFragment(newFragment, true);

        } else if (id == R.id.account) {

        } else if (id == R.id.setting) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Subscribe
    public void replaceFragment(ReplaceFragmentEvent fragmentReplaceEvent){
        Log.d(TAG, String.format("replaceFragment: %s", fragmentReplaceEvent) );
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

    @Subscribe
    public void drawSearch(DrawSearchEvent drawSearchEvent){
        searchView.setVisibility(View.VISIBLE);
        //logo.setVisibility(View.GONE);
        searchView.setFocusable(true);
        searchView.setIconified(false);

    }


}
