package com.example.quanla.quannet.fragments;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.quanla.quannet.R;
import com.example.quanla.quannet.activities.MainActivity;
import com.example.quanla.quannet.adapters.HotAdapter;
import com.example.quanla.quannet.database.DbContextHot;
import com.example.quanla.quannet.database.models.Comments;
import com.example.quanla.quannet.database.models.GameRoom;
import com.example.quanla.quannet.events.ActivityReplaceEvent;
import com.example.quanla.quannet.events.ReplaceFragmentEvent;
import com.google.android.gms.location.LocationListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by QuanLA on 3/19/2017.
 */

public class HotFragment extends Fragment{
    ProgressDialog progressDialog;
    private final String TAG = "jqk";

    String[] allName = {"Playdota Stadium", "Cybox Game Center", "Vikings Gaming", "Pegasus Club Center","GameHome","Gaming House","Imba eSports Stadium","Monaco Game","Colosseum Gaming Center","Only One Airport Gaming","Epic Gaming Center","Game Vip","G5 E-Sport Center","Moon Game", "Nhiệt Game", "Royal Gaming","Arena Gaming Center","Cyzone","H3 Cyber Gaming","Clan 105"};

    final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();
    private HotAdapter hotAdapter;

    @BindView(R.id.rv_hot)
    RecyclerView rvHot;

    public HotFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hot, container, false);
        ButterKnife.bind(this, view);


        if(getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).getSupportActionBar().setTitle("Top phòng máy");
        }

        progressDialog = new ProgressDialog(this.getContext());
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressDialog.dismiss();
                DbContextHot.instance.clear();
//                GameRoom gameRoom = dataSnapshot.child("360 Game").getValue(GameRoom.class);
//                GameRoom gameRoom1 = dataSnapshot.child("Auto Game").getValue(GameRoom.class);
//                DbContextHot.instance.add(gameRoom);
//                DbContextHot.instance.add(gameRoom1);
                for(int i=0; i<allName.length; i++){
                    GameRoom gameRoom = dataSnapshot.child("hot").child(allName[i]).getValue(GameRoom.class);
//                    Log.d(TAG, String.format("DCM: %s", gameRoom));
//                    Location dest = new Location("Dest");
//                    dest.setLatitude(gameRoom.getLatitude());
//                    dest.setLongitude(gameRoom.getLongitude());
//                    float km = mLastLocation.distanceTo(dest);
//                    gameRoom.setKm(km);
                    DbContextHot.instance.add(gameRoom);
                }
//                for (GameRoom gameRoom : DbContextHot.instance.getAllRooms())
//                    databaseReference.child("comment").child(gameRoom.getTitle()).push().setValue(new Comments("0","0"));
                Log.d(TAG, String.format("%s", dataSnapshot.child("hot").child("Playdota Stadium").getValue(GameRoom.class)));
                Log.d(TAG, (String.format("%s", DbContextHot.instance.getAllRooms().get(0).toString())));
                hotAdapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        hotAdapter = new HotAdapter();
        hotAdapter.setContext(this.getContext());
        rvHot.setAdapter(hotAdapter);
        rvHot.setLayoutManager(new LinearLayoutManager(this.getContext()));
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void replace(ActivityReplaceEvent activityReplaceEvent){
        EventBus.getDefault().post(new ReplaceFragmentEvent(new DetailFragment(), true));
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }




}
