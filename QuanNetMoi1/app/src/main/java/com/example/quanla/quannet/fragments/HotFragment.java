package com.example.quanla.quannet.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.quanla.quannet.R;
import com.example.quanla.quannet.activities.CoreActivity;
import com.example.quanla.quannet.activities.DetailActivity;
import com.example.quanla.quannet.adapters.HotAdapter;
import com.example.quanla.quannet.database.DbContext;
import com.example.quanla.quannet.database.models.GameRoom;
import com.example.quanla.quannet.events.ActivityReplaceEvent;
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

/**
 * Created by QuanLA on 3/19/2017.
 */

public class HotFragment extends Fragment {
    ProgressDialog progressDialog;

    String[] allName = {"Ibma net", "360 Game", "X - MEN Club", "Phoenix Cyber","Game Sieu Toc","Game Online","Syndra Gaming","Victory Game","Box-Game","Tung's Net","Auto Game","Imba Esports Stadium"};

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

        progressDialog = new ProgressDialog(this.getContext());
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressDialog.dismiss();
                DbContext.instance.clear();
//                GameRoom gameRoom = dataSnapshot.child("360 Game").getValue(GameRoom.class);
//                GameRoom gameRoom1 = dataSnapshot.child("Auto Game").getValue(GameRoom.class);
//                DbContext.instance.add(gameRoom);
//                DbContext.instance.add(gameRoom1);
                for(int i=0; i<allName.length; i++){
                    DbContext.instance.add(dataSnapshot.child(allName[i]).getValue(GameRoom.class));
                }
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
        startActivity(new Intent(this.getActivity(), DetailActivity.class));
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }
}
